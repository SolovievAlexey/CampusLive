package ru.campus.live.core.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AttachmentModel(
    val id: Int,
    val path: String,
    val width: Int,
    val height: Int,
    val orientation: Int
) : Parcelable
