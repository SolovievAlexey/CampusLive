package ru.campus.live.location.data.repository

import okhttp3.ResponseBody
import ru.campus.live.core.data.APIService
import ru.campus.live.core.data.source.CloudDataSource
import ru.campus.live.core.data.source.ErrorDataSource
import ru.campus.live.core.data.source.UserDataSource
import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.location.data.model.LocationModel
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val apiService: APIService,
    private val errorDataSource: ErrorDataSource,
    private val userDataSource: UserDataSource
) : ILocationRepository {

    override fun get(name: String?): ResponseObject<List<LocationModel>> {
        val call = apiService.location(userDataSource.token(), name)
        return CloudDataSource<List<LocationModel>>(errorDataSource = errorDataSource)
            .execute(call)
    }

    override fun rating(id: Int) {
        val call = apiService.locationRating(id, userDataSource.token())
        CloudDataSource<ResponseBody>(errorDataSource = errorDataSource).execute(call)
    }

    override fun save(data: LocationModel) {
        userDataSource.locationSave(data)
    }

}