package ru.campus.live.ribbon.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Publication(
    @PrimaryKey
    val id: Int? = null,
    @ColumnInfo(name = "publication_id")
    val publicationId: Int,
    @ColumnInfo(name = "type")
    val type: Int = 0,
    @ColumnInfo(name = "location_id")
    val locationId: Int?,
    @ColumnInfo(name = "location_name")
    val locationName: String?,
    @ColumnInfo(name = "location_address")
    val locationAddress: String?,
    @ColumnInfo(name = "location_type")
    val locationType: Int?,
    @ColumnInfo(name = "message")
    val message: String,
    @ColumnInfo(name = "media_id")
    val mediaId: Int?,
    @ColumnInfo(name = "media_path")
    val mediaPath: String?,
    @ColumnInfo(name = "media_width")
    val mediaWidth: Int?,
    @ColumnInfo(name = "media_height")
    val mediaHeight: Int?,
    @ColumnInfo(name = "media_orientation")
    val mediaOrientation: Int?,
    @ColumnInfo(name = "rating")
    val rating: Int,
    @ColumnInfo(name = "comments")
    val comments: Int,
    @ColumnInfo(name = "vote")
    val vote: Int,
    @ColumnInfo(name = "relativeTime")
    val relativeTime: String
)



