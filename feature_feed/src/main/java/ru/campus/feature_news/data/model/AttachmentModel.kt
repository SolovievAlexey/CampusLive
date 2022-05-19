package ru.campus.feature_news.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 14.05.2022 20:39
 */

@Parcelize
data class AttachmentModel(
    @SerializedName(value = "id")
    val id: Int,
    @SerializedName(value = "path")
    val path: String,
    @SerializedName(value = "width")
    val width: Int,
    @SerializedName(value = "height")
    val height: Int,
    @SerializedName(value = "orientation")
    val orientation: Int
) : Parcelable
