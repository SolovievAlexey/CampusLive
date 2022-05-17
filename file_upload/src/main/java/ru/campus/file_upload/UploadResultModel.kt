package ru.campus.file_upload

import com.google.gson.annotations.SerializedName

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 17.05.2022 22:30
 */

data class UploadResultModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("path")
    val path: String,
    @SerializedName("width")
    val width: Int,
    @SerializedName("height")
    val height: Int
)

