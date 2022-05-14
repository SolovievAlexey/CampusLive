package ru.campus.feature_location.data

import ru.campus.core.data.ResponseObject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 14.05.2022 16:45
 */

interface LocationRepository {
    fun get(name: String?): ResponseObject<ArrayList<LocationModel>>
    fun rating(id: Int)
    fun save(params: LocationModel)
}