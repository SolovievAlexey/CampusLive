package ru.campus.feature_discussion.domain

import ru.campus.core.data.*
import ru.campus.feature_discussion.data.model.DiscussionModel
import ru.campus.feature_discussion.data.model.DiscussionViewType
import ru.campus.feature_discussion.data.repository.DiscussionRepository
import ru.campus.feature_discussion.data.repository.UserAvatarStore
import ru.campus.feaure_discussion.R
import javax.inject.Inject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 21.05.2022 16:29
 */

class DiscussionInteractor @Inject constructor(
    private val repository: DiscussionRepository,
    private val domainDataStore: DomainDataStore,
    private val displayMetrics: DisplayMetrics,
    private val resourceManager: ResourceManager,
    private val userAvatarStore: UserAvatarStore,
    private val errorMessageHandler: ErrorMessageHandler,
) {

    fun shimmer(): ArrayList<DiscussionModel> {
        return repository.shimmer()
    }

    fun get(publicationId: Int): ResponseObject<ArrayList<DiscussionModel>> {
        return repository.get(publicationId = publicationId)
    }

    fun insertPublication(
        model: ArrayList<DiscussionModel>,
        publication: DiscussionModel,
    ): ArrayList<DiscussionModel> {
        try {
            if (model[0].type != DiscussionViewType.PUBLICATION)
                model.add(0, publication)
            return model
        } catch (e: Exception) {
            val response = ArrayList<DiscussionModel>()
            response.add(publication)
            return response
        }
    }

    fun map(model: ArrayList<DiscussionModel>): ArrayList<DiscussionModel> {
        val response = ArrayList<DiscussionModel>()
        model.forEach { parent ->
            if(parent.type != DiscussionViewType.PUBLICATION) {
                if (parent.parent == 0) {
                    parent.type = DiscussionViewType.PARENT
                    response.add(parent)
                    model.forEach { child ->
                        if (child.parent == parent.id) {
                            child.type = DiscussionViewType.CHILD
                            response.add(child)
                        }
                    }
                }
            } else {
                response.add(parent)
            }
        }
        return response
    }

    fun preparation(model: ArrayList<DiscussionModel>): ArrayList<DiscussionModel> {
        model.forEachIndexed { index, item ->
            if(item.type != DiscussionViewType.PUBLICATION) {
                var pathUserIcon = domainDataStore.get() + "/media/icon/" + item.icon_id + ".png"
                if (item.icon_id < 10) pathUserIcon =
                    domainDataStore.get() + "/media/icon/" + item.icon_id + ".png"
                model[index].userAvatar = pathUserIcon

                if (item.attachment != null) {
                    val params = displayMetrics.get(item.attachment.width, item.attachment.height)
                    model[index].mediaWidth = params[0]
                    model[index].mediaHeight = params[1]
                }

                if (item.hidden == 1)
                    model[index].message = resourceManager.get(R.string.comment_hidden)
            }
        }
        return model
    }

    fun avatar(model: ArrayList<DiscussionModel>?) {
        userAvatarStore.execute(model = model)
    }

    fun title(count: Int): String {
        return repository.title(count = count)
    }

    fun complaint(id: Int) {
        repository.complaint(id = id)
    }

    fun vote(id: Int, vote: Int) {
        repository.vote(commentId = id, vote = vote)
    }

    fun renderVoteView(model: ArrayList<DiscussionModel>, item: DiscussionModel, vote: Int,
    ): ArrayList<DiscussionModel> {
        return DiscussionVoteUseCase().execute(model, item, vote)
    }

}