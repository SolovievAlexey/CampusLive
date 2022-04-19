package ru.campus.live.start.data.repository

import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.start.data.model.LoginModel

interface IUserRepository {
    fun registration(): ResponseObject<LoginModel>
    fun login(userData: LoginModel): Boolean
}