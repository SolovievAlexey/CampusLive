package ru.campus.feature_location.domain

import ru.campus.core.data.ResponseObject
import ru.campus.feature_location.data.LocationModel
import ru.campus.feature_location.data.LocationRepository
import javax.inject.Inject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 14.05.2022 17:04
 */

class LocationInteractor @Inject constructor(
    private val repository: LocationRepository,
) {

    fun get(name: String?): ResponseObject<ArrayList<LocationModel>> {
        return repository.get(name)
    }

    fun save(locationModel: LocationModel) {
        repository.save(params = locationModel)
    }

}