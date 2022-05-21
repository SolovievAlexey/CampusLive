package ru.campus.feature_discussion.data.repository

import ru.campus.core.data.ResponseObject
import ru.campus.feature_discussion.data.model.DiscussionModel
import ru.campus.feature_discussion.data.model.DiscussionPostModel

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 21.05.2022 10:13
 */

interface DiscussionRepository {
    fun shimmer(): ArrayList<DiscussionModel>
    fun get(publicationId: Int): ResponseObject<ArrayList<DiscussionModel>>
    fun post(params: DiscussionPostModel): ResponseObject<DiscussionModel>
    fun vote(commentId: Int, vote: Int)
    fun complaint(id: Int)
}