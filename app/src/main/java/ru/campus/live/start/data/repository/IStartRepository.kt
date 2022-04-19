package ru.campus.live.start.data.repository

import ru.campus.live.start.data.model.StartModel

interface IStartRepository {
    fun start(): ArrayList<StartModel>
}