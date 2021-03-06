package ru.campus.core.data

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import javax.inject.Inject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 12.05.2022 20:26
 */

class BaseUserDataStore @Inject constructor(context: Context) : UserDataStore {

    private val sPref: SharedPreferences =
        context.getSharedPreferences("AppDB", Context.MODE_PRIVATE)

    override fun uid(): Int {
        return sPref.getInt("UID", 0)
    }

    override fun login(uid: Int, token: String) {
        val dateReg = System.currentTimeMillis() / 1000
        with(sPref.edit()) {
            putInt("UID", uid)
            putString("TOKEN", token)
            putLong("DATE_REG", dateReg)
            apply()
        }
    }

    override fun token(): String {
        return sPref.getString("TOKEN", "") ?: ""
    }

    override fun locationSave(id: Int, name: String) {
        with(sPref.edit()) {
            putInt("LOCATION_ID", id)
            putString("LOCATION_NAME", name)
            apply()
        }
    }

    override fun location(): Int {
        return sPref.getInt("LOCATION_ID", 0)
    }

    override fun locationName(): String {
        return sPref.getString("LOCATION_NAME","") ?: ""
    }

    override fun avatarSave(id: Int) {
        Log.d("MyLog", "Сохраняем аватар = "+id)
        sPref.edit().putInt("AVATAR", id).apply()
    }

    override fun avatar(): Int {
        val avatar = sPref.getInt("AVATAR", 0)
        Log.d("MyLog", "Отдаем аватар = "+avatar)
        return avatar
    }

}