package ru.campus.feature_start.domain

import ru.campus.core.data.ResponseObject
import ru.campus.feature_start.data.model.LoginModel
import ru.campus.feature_start.data.model.StartModel

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 12.05.2022 20:30
 */

interface StartInteractor {
    fun start(): List<StartModel>
    fun login(): ResponseObject<LoginModel>
}