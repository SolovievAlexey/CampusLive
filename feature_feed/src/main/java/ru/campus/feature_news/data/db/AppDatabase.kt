package ru.campus.feature_news.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.campus.feature_news.data.model.FeedDbModel

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 20.05.2022 18:41
 */

@Database(entities = [FeedDbModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun publicationDao(): FeedDao
}