package ru.campus.feature_discussion.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 21.05.2022 10:15
 */

@Parcelize
enum class DiscussionViewType : Parcelable {
    UNKNOWN, PARENT, CHILD, PARENT_SHIMMER, CHILD_SHIMMER, PUBLICATION
}