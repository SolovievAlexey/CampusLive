package ru.campus.file_upload.data

import com.google.gson.annotations.SerializedName

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 18.05.2022 21:28
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