package ru.campus.live.ribbon.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Publication::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun publicationDao(): PublicationDao
}