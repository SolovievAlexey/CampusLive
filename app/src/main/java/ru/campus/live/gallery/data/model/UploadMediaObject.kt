package ru.campus.live.gallery.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UploadMediaObject(
    var id: Int,
    val fullPath: String,
    var upload: Boolean,
    var error: Boolean,
    var animation: Boolean
) : Parcelable
