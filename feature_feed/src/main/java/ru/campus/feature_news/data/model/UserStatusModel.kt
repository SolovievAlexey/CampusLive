package ru.campus.feature_news.data.model

import com.google.gson.annotations.SerializedName

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 25.05.2022 20:02
 */

data class UserStatusModel(
    @SerializedName("views") val views: Int,
    @SerializedName("karma") val karma: Float
)