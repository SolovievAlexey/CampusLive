package ru.campus.live.discussion.domain

import ru.campus.live.R
import ru.campus.live.core.data.source.DisplayMetrics
import ru.campus.live.core.data.source.HostDataSource
import ru.campus.live.core.data.source.IUserDataSource
import ru.campus.live.core.data.source.ResourceManager
import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.core.data.model.VoteModel
import ru.campus.live.discussion.data.model.DiscussionObject
import ru.campus.live.discussion.data.model.DiscussionViewType
import ru.campus.live.discussion.data.repository.IDiscussionRepository
import ru.campus.live.discussion.domain.usecase.DiscussionTitleUseCase
import ru.campus.live.discussion.domain.usecase.DiscussionVoteUseCase
import ru.campus.live.discussion.domain.usecase.UserAvatarUseCase
import ru.campus.live.ribbon.data.model.RibbonModel
import javax.inject.Inject

class DiscussionInteractor @Inject constructor(
    private val repository: IDiscussionRepository,
    private val userDataSource: IUserDataSource,
    private val hostDataSource: HostDataSource,
    private val displayMetrics: DisplayMetrics,
    private val resourceManager: ResourceManager,
) {

    fun get(publication: Int): ResponseObject<ArrayList<DiscussionObject>> {
        return repository.get(publication)
    }

    fun header(
        item: DiscussionObject,
        model: ArrayList<DiscussionObject>,
    ): ArrayList<DiscussionObject> {
        model.add(0, item)
        return model
    }

    fun preparation(model: ArrayList<DiscussionObject>): ArrayList<DiscussionObject> {
        val response = ArrayList<DiscussionObject>()
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

    fun error(): ArrayList<DiscussionObject> {
        val model = ArrayList<DiscussionObject>()
        model.add(DiscussionObject(DiscussionViewType.DISCUSSION_NONE))
        return model
    }

    fun count(model: ArrayList<DiscussionObject>): Int {
        var count = 0
        model.forEach { item ->
            if (item.type == DiscussionViewType.PARENT
                || item.type == DiscussionViewType.CHILD
            ) count++
        }
        return count
    }

    fun title(count: Int): String {
        return DiscussionTitleUseCase(resourceManager).execute(count)
    }

    fun insert(
        item: DiscussionObject,
        model: ArrayList<DiscussionObject>,
    ): ArrayList<DiscussionObject> {
        model.add(item)
        return model
    }

    fun vote(params: VoteModel) {
        repository.vote(params.id, params.vote)
    }

    fun renderVoteView(
        model: ArrayList<DiscussionObject>,
        voteModel: VoteModel,
    ): ArrayList<DiscussionObject> {
        return DiscussionVoteUseCase().execute(model, voteModel)
    }

    fun complaint(id: Int) {
        repository.complaint(id)
    }

    fun refreshUserAvatar(model: ArrayList<DiscussionObject>) {
        userDataSource.saveUserAvatarIcon(UserAvatarUseCase().execute(model, userDataSource.uid()))
    }

    fun shimmer(): ArrayList<DiscussionObject> {
        val model = ArrayList<DiscussionObject>()
        model.add(DiscussionObject(DiscussionViewType.PARENT_SHIMMER))
        model.add(DiscussionObject(DiscussionViewType.CHILD_SHIMMER))
        model.add(DiscussionObject(DiscussionViewType.CHILD_SHIMMER))
        model.add(DiscussionObject(DiscussionViewType.CHILD_SHIMMER))
        model.add(DiscussionObject(DiscussionViewType.PARENT_SHIMMER))
        model.add(DiscussionObject(DiscussionViewType.CHILD_SHIMMER))
        model.add(DiscussionObject(DiscussionViewType.PARENT_SHIMMER))
        model.add(DiscussionObject(DiscussionViewType.CHILD_SHIMMER))
        model.add(DiscussionObject(DiscussionViewType.CHILD_SHIMMER))
        return model
    }

    fun map(publication: RibbonModel): DiscussionObject {
        return DiscussionObject(
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

    private fun preparationDataViewHolder(model: ArrayList<DiscussionObject>): ArrayList<DiscussionObject> {
        model.forEachIndexed { index, item ->
            if (item.type == DiscussionViewType.PARENT || item.type == DiscussionViewType.CHILD) {
                var pathUserIcon = hostDataSource.domain() + "media/icon/" + item.icon_id + ".png"
                if (item.icon_id < 10) pathUserIcon =
                    hostDataSource.domain() + "media/icon/" + item.icon_id + ".png"
                model[index].userAvatar = pathUserIcon

                if (item.attachment != null) {
                    val params = displayMetrics.get(item.attachment.width, item.attachment.height)
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