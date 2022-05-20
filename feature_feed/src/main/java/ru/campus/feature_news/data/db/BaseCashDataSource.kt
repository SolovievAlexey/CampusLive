package ru.campus.feature_news.data.db

import ru.campus.feature_news.data.model.FeedDbModel
import ru.campus.feature_news.data.model.FeedModel
import ru.campus.feature_news.data.model.FeedViewType
import javax.inject.Inject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 20.05.2022 18:43
 */

class BaseCashDataSource @Inject constructor(
    private val appDatabase: AppDatabase,
) : CashDataSource {

    override fun get(): ArrayList<FeedModel> {
        val db = appDatabase.publicationDao().get()
        val model = ArrayList<FeedModel>()
        db.forEach { item ->
            model.add(FeedModel(
                viewType = FeedViewType.PUBLICATION,
                location = null,
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
        return model
    }

    override fun post(model: ArrayList<FeedModel>) {
        appDatabase.publicationDao().clear()
        model.forEach { item ->
            val insert = FeedDbModel(
                id = item.id,
                message = item.message,
                mediaWidth = item.mediaWidth,
                mediaHeight = item.mediaHeight,
                attachment = item.attachment,
                rating = item.rating,
                comments = item.comments,
                commentsString = item.commentsString,
                vote = item.vote,
                relativeTime = item.relativeTime)
            appDatabase.publicationDao().insert(insert)
        }
    }

}