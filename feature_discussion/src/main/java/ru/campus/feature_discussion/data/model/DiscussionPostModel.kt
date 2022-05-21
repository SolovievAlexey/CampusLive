package ru.campus.feature_discussion.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 21.05.2022 10:18
 */

@Parcelize
data class DiscussionPostModel(
    var icon: Int = 0,
    val message: String,
    var attachment: Int = 0,
    val parent: Int,
    val answered: Int,
    val publication: Int
) : Parcelable
