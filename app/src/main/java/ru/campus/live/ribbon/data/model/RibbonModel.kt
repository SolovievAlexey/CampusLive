package ru.campus.live.ribbon.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

import ru.campus.live.core.data.model.AttachmentModel
import ru.campus.live.location.data.model.LocationModel

@Parcelize
data class RibbonModel(
    var viewType: RibbonViewType = RibbonViewType.UNKNOWN,
    val location: LocationModel? = null,
    val id: Int = 0,
    val message: String = "",
    val attachment: AttachmentModel? = null,
    var mediaWidth: Int = 0,
    var mediaHeight: Int = 0,
    var rating: Int = 0,
    val comments: Int = 0,
    var commentsString: String = "",
    var vote: Int = 0,
    val relativeTime: String = ""
) : Parcelable
