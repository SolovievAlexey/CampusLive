package ru.campus.feature_news.data.repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import ru.campus.core.data.CloudDataSource
import ru.campus.core.data.ResponseObject
import ru.campus.core.data.UserDataStore
import ru.campus.feature_news.data.APIService
import ru.campus.feature_news.data.model.StatusModel
import ru.campus.feature_news.data.model.UserStatusModel
import javax.inject.Inject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 21.05.2022 2:10
 */

class StatusDataSource @Inject constructor(
    context: Context,
    private val userDataStore: UserDataStore,
    private val apiService: APIService
) {

    private val sPref: SharedPreferences =
        context.getSharedPreferences("AppDB", Context.MODE_PRIVATE)

    fun read(): StatusModel {
        val views = sPref.getInt("LOCATION_VIEWS", 0)
        val karmaEmoji = sPref.getString("KARMA_EMOJI", "\uD83D\uDD25") ?: "\uD83D\uDD25"
        return StatusModel(
            location = userDataStore.locationName(),
            views = views,
            karma = karmaEmoji
        )
    }

    fun save(params: StatusModel) {
        with(sPref.edit()) {
            putInt("LOCATION_VIEWS", params.views)
            putString("KARMA_EMOJI", params.karma)
            apply()
        }
    }

    fun refresh(): StatusModel {
        val call =
            apiService.status(token = userDataStore.token(), location = userDataStore.location())
        val result = CloudDataSource<UserStatusModel>().execute(call = call)
        if (result is ResponseObject.Success) {
            Log.d("MyLog", "Карма пользователя = "+result.data.karma)
            val karmaEmoji = if (result.data.karma > 0.7) {
                "\uD83D\uDD25"
            } else if (result.data.karma > 0.5) {
                "\uD83D\uDC4D"
            } else if (result.data.karma > 0.2) {
                "\uD83D\uDC4E"
            } else {
                "\uD83D\uDCA9"
            }

            val location = userDataStore.locationName()
            return StatusModel(
                location = location,
                views = result.data.views,
                karma = karmaEmoji
            )
        }

        return read()
    }

}