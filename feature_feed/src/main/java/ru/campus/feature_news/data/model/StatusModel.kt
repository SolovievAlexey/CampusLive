package ru.campus.feature_news.data.model

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 21.05.2022 1:48
 */

data class StatusModel(
    val location: String,
    val views: Int,
    val karma: String,
    val notification: Int
)