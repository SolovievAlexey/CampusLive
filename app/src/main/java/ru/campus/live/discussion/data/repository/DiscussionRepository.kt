package ru.campus.live.discussion.data.repository

import okhttp3.ResponseBody
import ru.campus.live.core.data.APIService
import ru.campus.live.core.data.source.CloudDataSource
import ru.campus.live.core.data.source.ErrorDataSource
import ru.campus.live.core.data.source.UserDataSource
import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.discussion.data.model.CommentCreateObject
import ru.campus.live.discussion.data.model.DiscussionObject
import javax.inject.Inject

class DiscussionRepository @Inject constructor(
    private val apiService: APIService,
    private val errorDataSource: ErrorDataSource,
    private val user: UserDataSource
) : IDiscussionRepository {

    override fun get(publication: Int): ResponseObject<ArrayList<DiscussionObject>> {
        val call = apiService.discussion(token = user.token(), publicationId = publication)
        return CloudDataSource<ArrayList<DiscussionObject>>(errorDataSource = errorDataSource)
            .execute(call)
    }

    override fun post(params: CommentCreateObject): ResponseObject<DiscussionObject> {
        val call = apiService.commentCreate(
            token = user.token(),
            icon = params.icon,
            message = params.message,
            attachmentId = params.attachment,
            parent = params.parent,
            answered = params.answered,
            publicationId = params.publication
        )
        return CloudDataSource<DiscussionObject>(errorDataSource = errorDataSource).execute(call)
    }

    override fun vote(id: Int, vote: Int) {
        val call = apiService.voteComment(token = user.token(), vote = vote, objectId = id)
        CloudDataSource<ResponseBody>(errorDataSource = errorDataSource).execute(call)
    }

    override fun complaint(id: Int) {
        val call = apiService.complaint(2, id)
        CloudDataSource<ResponseBody>(errorDataSource = errorDataSource).execute(call)
    }

}