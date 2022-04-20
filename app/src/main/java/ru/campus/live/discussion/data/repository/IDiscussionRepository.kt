package ru.campus.live.discussion.data.repository

import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.discussion.data.model.CommentCreateModel
import ru.campus.live.discussion.data.model.DiscussionModel

interface IDiscussionRepository {
    fun get(publication: Int): ResponseObject<ArrayList<DiscussionModel>>
    fun post(params: CommentCreateModel): ResponseObject<DiscussionModel>
    fun vote(id: Int, vote: Int)
    fun complaint(id: Int)
}