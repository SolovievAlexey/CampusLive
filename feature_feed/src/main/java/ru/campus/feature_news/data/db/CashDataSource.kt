package ru.campus.feature_news.data.db

import ru.campus.feature_news.data.model.FeedModel

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 20.05.2022 18:43
 */

interface CashDataSource {
    fun get(): ArrayList<FeedModel>
    fun post(model: ArrayList<FeedModel>)
}