package ru.campus.feature_news.data.repository

import android.content.Context
import android.content.SharedPreferences
import ru.campus.core.data.UserDataStore
import ru.campus.feature_news.data.model.StatusModel
import javax.inject.Inject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 21.05.2022 2:10
 */

class StatusDataSource @Inject constructor(
    private val context: Context,
    private val userDataStore: UserDataStore
) {

    private val sPref: SharedPreferences =
        context.getSharedPreferences("AppDB", Context.MODE_PRIVATE)

    fun read(): StatusModel {
        val views = sPref.getInt("LOCATION_VIEWS", 0)
        val karmaEmoji = sPref.getString("KARMA_EMOJI", "\uD83D\uDD25") ?: "\uD83D\uDD25"
        val notification = sPref.getInt("NOTIFICATION_COUNT", 0)
        return StatusModel(
            location = userDataStore.locationName(),
            views = views,
            karma = karmaEmoji,
            notification = notification
        )
    }

    fun save(params: StatusModel) {
        with(sPref.edit()) {
            putInt("LOCATION_VIEWS", params.views)
            putString("KARMA_EMOJI", params.karma)
            putInt("NOTIFICATION_COUNT", params.notification)
            apply()
        }
    }

    fun refresh(): StatusModel {
        val location = userDataStore.locationName()
        val karmaEmoji = "\uD83D\uDD25"
        val notification = 26
        return StatusModel(
            location = location,
            views = 4548,
            karma = karmaEmoji,
            notification = notification
        )
    }

}