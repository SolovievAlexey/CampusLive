package ru.campus.live.core.data.source

import ru.campus.live.location.data.model.LocationModel
import ru.campus.live.start.data.model.LoginModel

interface IUserDataSource {
    fun login(data: LoginModel): Boolean
    fun isAuth(): Boolean
    fun token(): String
    fun uid(): Int
    fun locationSave(model: LocationModel)
    fun location(): LocationModel
    fun saveUserAvatarIcon(userAvatarIcon: Int)
    fun getUserAvatarIcon(): Int
}