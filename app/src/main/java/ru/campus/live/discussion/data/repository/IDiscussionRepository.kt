package ru.campus.live.discussion.data.repository

import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.discussion.data.model.CommentCreateObject
import ru.campus.live.discussion.data.model.DiscussionObject

interface IDiscussionRepository {
    fun get(publication: Int): ResponseObject<ArrayList<DiscussionObject>>
    fun post(params: CommentCreateObject): ResponseObject<DiscussionObject>
    fun vote(id: Int, vote: Int)
    fun complaint(id: Int)
}