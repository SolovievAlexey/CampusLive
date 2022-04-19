package ru.campus.live.core.data.source

import android.content.Context
import android.content.SharedPreferences
import ru.campus.live.R
import javax.inject.Inject

class HostDataSource @Inject constructor(private val context: Context) {

    fun domain(): String {
        val sPref: SharedPreferences =
            context.getSharedPreferences("AppDB", Context.MODE_PRIVATE)
        return sPref.getString("HOST", context.getString(R.string.host) + "/").toString()
    }

}