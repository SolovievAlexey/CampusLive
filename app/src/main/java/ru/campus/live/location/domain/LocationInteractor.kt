package ru.campus.live.location.domain

import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.location.data.model.LocationModel
import ru.campus.live.location.data.repository.ILocationRepository
import javax.inject.Inject

class LocationInteractor @Inject constructor(
    private val repository: ILocationRepository,
) : ILocationInteractor {

    override fun search(name: String?): List<LocationModel> {
        val result = repository.get(name)
        if (result is ResponseObject.Success)
            return result.data
        else
            return emptyList()
    }

    override fun save(params: LocationModel) {
        repository.rating(id = params.id)
        repository.save(params)
    }

}