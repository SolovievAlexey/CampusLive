package ru.campus.feature_news.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
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
    @ColumnInfo(name = "attachment_id")
    val id: Int,
    @SerializedName(value = "path")
    @ColumnInfo(name = "attachment_path")
    val path: String,
    @SerializedName(value = "width")
    @ColumnInfo(name = "attachment_width")
    val width: Int,
    @SerializedName(value = "height")
    @ColumnInfo(name = "attachment_height")
    val height: Int,
    @SerializedName(value = "orientation")
    @ColumnInfo(name = "attachment_orientation")
    val orientation: Int
) : Parcelable
