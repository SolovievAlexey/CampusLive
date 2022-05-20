package ru.campus.feature_news.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.campus.feature_news.data.model.FeedDbModel

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 20.05.2022 18:39
 */

@Dao
interface FeedDao {

    @Query("SELECT * FROM feeddbmodel")
    fun get(): List<FeedDbModel>

    @Insert
    fun insert(item: FeedDbModel)

    @Query("DELETE FROM feeddbmodel")
    fun clear()

}