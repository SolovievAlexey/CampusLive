package ru.campus.live.discussion.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CommentCreateObject(
    var icon: Int = 0,
    val message: String,
    var attachment: Int,
    val parent: Int,
    val answered: Int,
    val publication: Int
) : Parcelable