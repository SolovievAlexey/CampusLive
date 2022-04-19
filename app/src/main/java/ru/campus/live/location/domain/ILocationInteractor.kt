package ru.campus.live.location.domain

import ru.campus.live.location.data.model.LocationModel

interface ILocationInteractor {
    fun search(name: String?): List<LocationModel>
    fun save(params: LocationModel)
}