package ru.campus.feature_news.data

import ru.campus.core.data.ResponseObject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 14.05.2022 20:34
 */

interface NewsRepository {
    fun get(offset: Int): ResponseObject<ArrayList<FeedModel>>
    fun post(params: FeedPostModel): ResponseObject<FeedModel>
    fun vote(params: VoteModel)
    fun complaint(id: Int)
}