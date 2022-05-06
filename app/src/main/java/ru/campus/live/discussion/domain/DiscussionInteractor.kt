package ru.campus.live.discussion.domain

import ru.campus.live.R
import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.core.data.model.VoteModel
import ru.campus.live.core.data.source.DisplayMetrics
import ru.campus.live.core.data.source.HostDataSource
import ru.campus.live.core.data.source.IUserDataStore
import ru.campus.live.core.data.source.ResourceManager
import ru.campus.live.discussion.data.model.DiscussionModel
import ru.campus.live.discussion.data.model.DiscussionViewType
import ru.campus.live.discussion.data.repository.IDiscussionRepository
import ru.campus.live.discussion.domain.usecase.DiscussionTitleUseCase
import ru.campus.live.discussion.domain.usecase.DiscussionVoteUseCase
import ru.campus.live.discussion.domain.usecase.UserAvatarUseCase
import ru.campus.live.ribbon.data.model.RibbonModel
import javax.inject.Inject

class DiscussionInteractor @Inject constructor(
    private val repository: IDiscussionRepository,
    private val userDataStore: IUserDataStore,
    private val hostDataSource: HostDataSource,
    private val displayMetrics: DisplayMetrics,
    private val resourceManager: ResourceManager,
) {

    fun get(publication: Int): ResponseObject<ArrayList<DiscussionModel>> {
        return repository.get(publication)
    }

    fun adPublicationView(
        publication: DiscussionModel,
        model: ArrayList<DiscussionModel>
    ): ArrayList<DiscussionModel> {
        model.add(0, publication)
        return model
    }

    fun preparation(model: ArrayList<DiscussionModel>): ArrayList<DiscussionModel> {
        val response = ArrayList<DiscussionModel>()
        model.forEach { item ->
            if (item.type == DiscussionViewType.UNKNOWN ||
                item.type == DiscussionViewType.PARENT || item.type == DiscussionViewType.CHILD
            ) {
                if (item.parent == 0) {
                    item.type = DiscussionViewType.PARENT
                    response.add(item)
                }

                model.forEach { child ->
                    if (child.parent == item.id) {
                        child.type = DiscussionViewType.CHILD
                        response.add(child)
                    }
                }
            }
        }
        return preparationDataViewHolder(response)
    }

    fun getErrorView(): ArrayList<DiscussionModel> {
        val model = ArrayList<DiscussionModel>()
        model.add(DiscussionModel(DiscussionViewType.DISCUSSION_NONE))
        return model
    }

    fun count(model: ArrayList<DiscussionModel>): Int {
        return model.count {
            it.type == DiscussionViewType.PARENT
                    || it.type == DiscussionViewType.CHILD
        }
    }

    fun title(count: Int): String {
        return DiscussionTitleUseCase(resourceManager).execute(count)
    }

    fun insert(
        item: DiscussionModel,
        model: ArrayList<DiscussionModel>,
    ): ArrayList<DiscussionModel> {
        model.add(item)
        return model
    }

    fun vote(params: VoteModel) {
        repository.vote(params.id, params.vote)
    }

    fun renderVoteView(
        model: ArrayList<DiscussionModel>,
        voteModel: VoteModel,
    ): ArrayList<DiscussionModel> {
        return DiscussionVoteUseCase().execute(model, voteModel)
    }

    fun complaint(id: Int) {
        repository.complaint(id)
    }

    fun refreshUserAvatar(model: ArrayList<DiscussionModel>) {
        userDataStore.saveUserAvatarIcon(UserAvatarUseCase().execute(model, userDataStore.uid()))
    }

    fun shimmer(): ArrayList<DiscussionModel> {
        val model = ArrayList<DiscussionModel>()
        model.add(DiscussionModel(DiscussionViewType.PARENT_SHIMMER))
        model.add(DiscussionModel(DiscussionViewType.CHILD_SHIMMER))
        model.add(DiscussionModel(DiscussionViewType.CHILD_SHIMMER))
        model.add(DiscussionModel(DiscussionViewType.CHILD_SHIMMER))
        model.add(DiscussionModel(DiscussionViewType.PARENT_SHIMMER))
        model.add(DiscussionModel(DiscussionViewType.CHILD_SHIMMER))
        model.add(DiscussionModel(DiscussionViewType.PARENT_SHIMMER))
        model.add(DiscussionModel(DiscussionViewType.CHILD_SHIMMER))
        model.add(DiscussionModel(DiscussionViewType.CHILD_SHIMMER))
        return model
    }

    fun map(publication: RibbonModel): DiscussionModel {
        return DiscussionModel(
            type = DiscussionViewType.PUBLICATION,
            id = publication.id,
            hidden = 1,
            author = 0,
            icon_id = 0,
            message = publication.message,
            attachment = publication.attachment,
            rating = publication.rating,
            vote = publication.vote,
            parent = 0,
            answered = 0,
            relativeTime = publication.relativeTime
        )
    }

    private fun preparationDataViewHolder(model: ArrayList<DiscussionModel>): ArrayList<DiscussionModel> {
        model.forEachIndexed { index, item ->
            if (item.type == DiscussionViewType.PARENT || item.type == DiscussionViewType.CHILD) {
                var pathUserIcon = hostDataSource.domain() + "media/icon/" + item.icon_id + ".png"
                if (item.icon_id < 10) pathUserIcon =
                    hostDataSource.domain() + "media/icon/" + item.icon_id + ".png"
                model[index].userAvatar = pathUserIcon

                if (item.attachment != null) {
                    val params = displayMetrics.get(item.attachment.attachmentWidth, item.attachment.attachmentHeight)
                    model[index].mediaWidth = params[0]
                    model[index].mediaHeight = params[1]
                } else {
                    model[index].mediaWidth = 0
                    model[index].mediaHeight = 1
                }

                if (item.hidden == 1)
                    model[index].message = resourceManager.get(R.string.comment_hidden)
            }
        }
        return model
    }

}