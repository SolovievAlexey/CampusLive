package ru.campus.live.ribbon.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PublicationDao {
    @Query("SELECT * FROM ribbondbmodel")
    fun get(): List<RibbonDBModel>

    @Insert
    fun insert(item: RibbonDBModel)

    @Query("DELETE FROM ribbondbmodel")
    fun clear()
}