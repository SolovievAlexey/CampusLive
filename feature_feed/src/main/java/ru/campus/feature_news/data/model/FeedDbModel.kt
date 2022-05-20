package ru.campus.feature_news.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 20.05.2022 18:36
 */

@Entity
data class FeedDbModel(
    @PrimaryKey val primaryKey: Int? = null,
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "message") val message: String = "",
    @ColumnInfo(name = "mediaWidth") var mediaWidth: Int = 0,
    @ColumnInfo(name = "mediaHeight") var mediaHeight: Int = 0,
    @Embedded
    val attachment: AttachmentModel? = null,
    @ColumnInfo(name = "rating") var rating: Int = 0,
    @ColumnInfo(name = "comments") val comments: Int = 0,
    @ColumnInfo(name = "commentsString") var commentsString: String = "",
    @ColumnInfo(name = "vote") var vote: Int = 0,
    @ColumnInfo(name = "relativeTime") val relativeTime: String = "",
)