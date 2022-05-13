package ru.campus.live.core.data.source

import ru.campus.live.location.data.model.LocationModel

interface IUserDataStore {
    fun isAuth(): Boolean
    fun token(): String
    fun uid(): Int
    fun locationSave(model: LocationModel)
    fun location(): LocationModel
    fun saveUserAvatarIcon(userAvatarIcon: Int)
    fun getUserAvatarIcon(): Int
    fun rating(): Int
}