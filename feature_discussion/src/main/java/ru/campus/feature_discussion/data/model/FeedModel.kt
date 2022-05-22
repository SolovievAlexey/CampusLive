package ru.campus.feature_discussion.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 14.05.2022 20:35
 */

@Parcelize
data class FeedModel(
    var viewType: FeedViewType = FeedViewType.PUBLICATION,
    val id: Int = 0,
    val message: String = "",
    val attachment: AttachmentModel? = null,
    var mediaWidth: Int = 0,
    var mediaHeight: Int = 0,
    var rating: Int = 0,
    val comments: Int = 0,
    var commentsString: String = "",
    var vote: Int = 0,
    val relativeTime: String = "",
) : Parcelable

