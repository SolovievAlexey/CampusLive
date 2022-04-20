package ru.campus.live.gallery.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GalleryDataModel(
    val id: Int,
    val fullPath: String,
    val realOrientation: Int
) : Parcelable
