package ru.campus.live.location.data.repository

import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.location.data.model.LocationModel

interface ILocationRepository {
    fun get(name: String?): ResponseObject<List<LocationModel>>
    fun rating(id: Int)
    fun save(data: LocationModel)
}