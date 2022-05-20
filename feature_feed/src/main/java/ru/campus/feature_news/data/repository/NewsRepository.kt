package ru.campus.feature_news.data.repository

import ru.campus.core.data.ResponseObject
import ru.campus.feature_news.data.model.FeedModel
import ru.campus.feature_news.data.model.FeedPostModel
import ru.campus.feature_news.data.model.VoteModel

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 14.05.2022 20:34
 */

interface NewsRepository {
    fun cache(): ArrayList<FeedModel>
    fun get(offset: Int): ResponseObject<ArrayList<FeedModel>>
    fun save(model: ArrayList<FeedModel>)
    fun post(params: FeedPostModel): ResponseObject<FeedModel>
    fun vote(id: Int, vote: Int)
    fun complaint(id: Int)
}