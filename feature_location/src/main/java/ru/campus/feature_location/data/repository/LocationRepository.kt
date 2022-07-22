package ru.campus.feature_location.data.repository

import ru.campus.core.data.ResponseObject
import ru.campus.feature_location.data.model.LocationModel

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 14.05.2022 16:45
 */

interface LocationRepository {
    fun get(name: String?): ResponseObject<ArrayList<LocationModel>>
    fun beside(latitude: Double, longitude: Double): ResponseObject<ArrayList<LocationModel>>
    fun rating(id: Int)
    fun save(params: LocationModel)
}