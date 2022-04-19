package ru.campus.live.ribbon.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PublicationDao {
    @Query("SELECT * FROM publication")
    fun get(): List<Publication>

    @Insert
    fun insert(item: Publication)

    @Query("DELETE FROM publication")
    fun clear()
}