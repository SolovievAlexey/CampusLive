package ru.campus.feature_news.data.model

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 14.05.2022 20:45
 */

data class FeedPostModel(
    val message: String,
    var attachment: Int = 0
)
