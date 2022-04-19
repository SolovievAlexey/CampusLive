package ru.campus.live.discussion.data.repository

import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.discussion.data.model.CommentCreateObject
import ru.campus.live.discussion.data.model.DiscussionModel

interface IDiscussionRepository {
    fun get(publication: Int): ResponseObject<ArrayList<DiscussionModel>>
    fun post(params: CommentCreateObject): ResponseObject<DiscussionModel>
    fun vote(id: Int, vote: Int)
    fun complaint(id: Int)
}