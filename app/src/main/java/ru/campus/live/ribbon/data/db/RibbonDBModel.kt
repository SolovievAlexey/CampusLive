package ru.campus.live.ribbon.data.db

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.campus.live.core.data.model.AttachmentModel
import ru.campus.live.location.data.model.LocationModel
import ru.campus.live.ribbon.data.model.RibbonViewType

@Entity
data class RibbonDBModel(
    @PrimaryKey
    val key: Int? = null,
    @ColumnInfo(name = "viewType")
    val viewType: RibbonViewType = RibbonViewType.UNKNOWN,
    @Embedded
    val location: LocationModel? = null,
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "message")
    val message: String = "",
    @Embedded
    val attachment: AttachmentModel? = null,
    @ColumnInfo(name = "mediaWidth")
    val mediaWidth: Int = 0,
    @ColumnInfo(name = "mediaHeight")
    val mediaHeight: Int = 0,
    @ColumnInfo(name = "rating")
    val rating: Int = 0,
    @ColumnInfo(name = "comments")
    val comments: Int = 0,
    @ColumnInfo(name = "commentsString")
    val commentsString: String = "",
    @ColumnInfo(name = "vote")
    val vote: Int = 0,
    @ColumnInfo(name = "relativeTime")
    val relativeTime: String = ""
)
