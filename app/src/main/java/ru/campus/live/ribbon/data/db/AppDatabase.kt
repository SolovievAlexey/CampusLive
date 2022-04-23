package ru.campus.live.ribbon.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RibbonDBModel::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun publicationDao(): PublicationDao
}