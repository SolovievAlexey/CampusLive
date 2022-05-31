package ru.campus.feature_news.data.model

import com.google.gson.annotations.SerializedName

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 21.05.2022 1:48
 */

data class StatusModel(
    val location: String? = null,
    val views: Int,
    val karma: String
)