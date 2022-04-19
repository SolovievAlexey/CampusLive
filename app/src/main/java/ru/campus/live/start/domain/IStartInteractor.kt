package ru.campus.live.start.domain

import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.start.data.model.LoginModel
import ru.campus.live.start.data.model.StartModel

interface IStartInteractor {
    fun start(): ArrayList<StartModel>
    fun login(): ResponseObject<LoginModel>
}