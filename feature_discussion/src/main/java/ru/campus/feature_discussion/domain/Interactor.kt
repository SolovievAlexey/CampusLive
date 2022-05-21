package ru.campus.feature_discussion.domain

import ru.campus.core.data.DisplayMetrics
import ru.campus.core.data.DomainDataStore
import ru.campus.core.data.ResourceManager
import ru.campus.core.data.ResponseObject
import ru.campus.feature_discussion.data.model.DiscussionModel
import ru.campus.feature_discussion.data.model.DiscussionViewType
import ru.campus.feature_discussion.data.repository.DiscussionRepository
import ru.campus.feaure_discussion.R
import javax.inject.Inject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 21.05.2022 16:29
 */

class Interactor @Inject constructor(
    private val repository: DiscussionRepository,
    private val domainDataStore: DomainDataStore,
    private val displayMetrics: DisplayMetrics,
    private val resourceManager: ResourceManager
) {

    fun get(publicationId: Int): ResponseObject<ArrayList<DiscussionModel>> {
        return repository.get(publicationId = publicationId)
    }

    fun map(model: ArrayList<DiscussionModel>): ArrayList<DiscussionModel> {
        val response = ArrayList<DiscussionModel>()
        model.forEach { parent ->
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
        }
        return response
    }

    fun preparation(model: ArrayList<DiscussionModel>): ArrayList<DiscussionModel> {
        model.forEachIndexed { index, item ->
            var pathUserIcon = domainDataStore.get() + "media/icon/" + item.icon_id + ".png"
            if (item.icon_id < 10) pathUserIcon =
                domainDataStore.get() + "media/icon/" + item.icon_id + ".png"
            model[index].userAvatar = pathUserIcon

            if (item.attachment != null) {
                val params = displayMetrics.get(item.attachment.width, item.attachment.height)
                model[index].mediaWidth = params[0]
                model[index].mediaHeight = params[1]
            }

            if (item.hidden == 1)
                model[index].message = resourceManager.get(R.string.comment_hidden)
        }
        return model
    }

}