package ru.campus.feature_news

import ru.campus.core.data.ResponseObject
import ru.campus.feature_news.data.FeedModel
import ru.campus.feature_news.data.FeedPostModel
import ru.campus.feature_news.data.VoteModel

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