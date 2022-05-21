package ru.campus.feature_discussion.data.repository

import okhttp3.ResponseBody
import ru.campus.core.data.CloudDataSource
import ru.campus.core.data.ResponseObject
import ru.campus.core.data.UserDataStore
import ru.campus.feature_discussion.data.APIService
import ru.campus.feature_discussion.data.model.DiscussionModel
import ru.campus.feature_discussion.data.model.DiscussionPostModel
import javax.inject.Inject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 21.05.2022 16:11
 */

private const val OBJECT_TYPE = 2

class BaseDiscussionRepository @Inject constructor(
    private val apiService: APIService,
    private val userDataStore: UserDataStore
) : DiscussionRepository {

    override fun get(publicationId: Int): ResponseObject<ArrayList<DiscussionModel>> {
        val call = apiService.get(token = userDataStore.token(), publicationId = publicationId)
        return CloudDataSource<ArrayList<DiscussionModel>>().execute(call)
    }

    override fun post(params: DiscussionPostModel): ResponseObject<DiscussionModel> {
        val call = apiService.post(
            token = userDataStore.token(),
            icon = params.icon,
            message = params.message,
            attachmentId = params.attachment,
            parent = params.parent,
            answered = params.answered,
            publicationId = params.publication
        )
        return CloudDataSource<DiscussionModel>().execute(call)
    }

    override fun vote(commentId: Int, vote: Int) {
        val call = apiService.vote(
            token = userDataStore.token(),
            vote = vote,
            objectId = commentId
        )
        CloudDataSource<ResponseBody>().execute(call)
    }

    override fun complaint(id: Int) {
        val call = apiService.complaint(
            objectType = OBJECT_TYPE,
            objectId = id
        )
        CloudDataSource<ResponseBody>().execute(call)
    }

}