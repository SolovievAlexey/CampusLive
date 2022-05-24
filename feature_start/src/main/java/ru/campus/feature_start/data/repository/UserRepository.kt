package ru.campus.feature_start.data.repository

import ru.campus.core.data.ResponseObject
import ru.campus.feature_start.data.model.LoginModel

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 12.05.2022 20:13
 */

interface UserRepository {
    fun registration(): ResponseObject<LoginModel>
    fun login(params: LoginModel)
}