package ru.campus.feature_news.data.model

import com.google.gson.annotations.SerializedName

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 14.05.2022 20:48
 */

data class UploadResultModel(
    @SerializedName(value = "id")
    val id: Int,
    @SerializedName(value = "path")
    val path: String,
    @SerializedName(value = "width")
    val width: Int,
    @SerializedName(value = "height")
    val height: Int
)
