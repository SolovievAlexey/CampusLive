package ru.campus.live.ribbon.data.repository

import android.util.Log
import ru.campus.live.ribbon.data.db.AppDatabase
import ru.campus.live.ribbon.data.db.RibbonDBModel
import ru.campus.live.ribbon.data.model.RibbonModel
import ru.campus.live.ribbon.data.model.RibbonViewType
import javax.inject.Inject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 23.04.2022 14:26
 */

class CashDataSource @Inject constructor(private val appDatabase: AppDatabase): ICashDataSource {

    override fun get(): ArrayList<RibbonModel> {
        val result = appDatabase.publicationDao().get()
        return mapRibbon(result)
    }

    override fun post(model: ArrayList<RibbonModel>) {
        val result = map(model)
        val dao = appDatabase.publicationDao()
        dao.clear()
        result.forEach { item -> dao.insert(item = item) }
    }

    private fun map(model: ArrayList<RibbonModel>): ArrayList<RibbonDBModel> {
        val response = ArrayList<RibbonDBModel>()
        model.forEach { item ->
            response.add(RibbonDBModel(
                viewType = item.viewType,
                location = item.location,
                id = item.id,
                message = item.message,
                attachment = item.attachment,
                mediaWidth = item.mediaWidth,
                mediaHeight = item.mediaHeight,
                rating = item.rating,
                comments = item.comments,
                commentsString = item.commentsString,
                vote = item.vote,
                relativeTime = item.relativeTime
            ))
        }
        return response
    }

    private fun mapRibbon(model: List<RibbonDBModel>): ArrayList<RibbonModel> {
        val response = ArrayList<RibbonModel>()
        model.forEach { item ->
            response.add(RibbonModel(
                viewType = RibbonViewType.UNKNOWN,
                location = item.location,
                id = item.id,
                message = item.message,
                attachment = item.attachment,
                mediaWidth = item.mediaWidth,
                mediaHeight = item.mediaHeight,
                rating = item.rating,
                comments = item.comments,
                commentsString = item.commentsString,
                vote = item.vote,
                relativeTime = item.relativeTime
            ))
        }
        return response
    }

}