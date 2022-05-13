package ru.campus.live.core.data.source

import android.content.Context
import android.content.SharedPreferences
import ru.campus.live.location.data.model.LocationModel
import javax.inject.Inject

class UserDataStore @Inject constructor(context: Context) : IUserDataStore {

    private val sPref: SharedPreferences =
        context.getSharedPreferences("AppDB", Context.MODE_PRIVATE)

    override fun isAuth(): Boolean {
        val token = sPref.getString("TOKEN", "").toString()
        val locationId = sPref.getInt("LOCATION_ID", 0)
        if (token.isEmpty()) return false
        if (locationId == 0) return false
        return true
    }

    override fun token(): String {
        return sPref.getString("TOKEN", "").toString()
    }

    override fun uid(): Int {
        return sPref.getInt("UID", 0)
    }

    override fun locationSave(model: LocationModel) {
        with(sPref.edit()) {
            putInt("LOCATION_ID", model.locationId)
            putString("LOCATION_NAME", model.locationName)
            putString("LOCATION_ADDRESS", model.locationAddress)
            putInt("LOCATION_TYPE", model.locationType)
            apply()
        }
    }

    override fun location(): LocationModel {
        val id = sPref.getInt("LOCATION_ID", 0)
        val name = sPref.getString("LOCATION_NAME", "")
        val address = sPref.getString("LOCATION_ADDRESS", "")
        val type = sPref.getInt("LOCATION_TYPE", 1)
        return LocationModel(locationId = id, locationName = name!!, locationAddress = address!!, locationType = type)
    }

    override fun saveUserAvatarIcon(userAvatarIcon: Int) {
        sPref.edit().putInt("USER_AVATAR", userAvatarIcon).apply()
    }

    override fun getUserAvatarIcon(): Int {
        return sPref.getInt("USER_AVATAR", 0)
    }

    override fun rating(): Int {
        return sPref.getInt("RATING", 0)
    }

}