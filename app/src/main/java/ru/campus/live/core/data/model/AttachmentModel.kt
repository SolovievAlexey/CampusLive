package ru.campus.live.core.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AttachmentModel(
    @SerializedName(value = "id")
    val attachmentId: Int,
    @SerializedName(value = "path")
    val attachmentPath: String,
    @SerializedName(value = "width")
    val attachmentWidth: Int,
    @SerializedName(value = "height")
    val attachmentHeight: Int,
    @SerializedName(value = "orientation")
    val attachmentOrientation: Int
) : Parcelable
