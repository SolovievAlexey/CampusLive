package ru.campus.feature_discussion.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 21.05.2022 10:15
 */

@Parcelize
data class DiscussionModel(
    var type: DiscussionViewType = DiscussionViewType.UNKNOWN,
    val id: Int = 0,
    val hidden: Int = 0,
    val author: Int = 0,
    var icon_id: Int = 0,
    var userAvatar: String = "",
    var mediaWidth: Int = 0,
    var mediaHeight: Int = 0,
    var message: String = "",
    val attachment: AttachmentModel? = null,
    var rating: Int = 0,
    var vote: Int = 0,
    val parent: Int = 0,
    val answered: Int = 0,
    val relativeTime: String = ""
) : Parcelable