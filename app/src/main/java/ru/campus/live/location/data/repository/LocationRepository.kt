package ru.campus.live.location.data.repository

import okhttp3.ResponseBody
import ru.campus.live.core.data.APIService
import ru.campus.live.core.data.source.CloudDataSource
import ru.campus.live.core.data.source.ErrorDataSource
import ru.campus.live.core.data.source.UserDataStore
import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.location.data.model.LocationModel
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val apiService: APIService,
    private val errorDataSource: ErrorDataSource,
    private val userDataStore: UserDataStore
) : ILocationRepository {

    override fun get(name: String?): ResponseObject<List<LocationModel>> {
        val call = apiService.location(userDataStore.token(), name)
        return CloudDataSource<List<LocationModel>>(errorDataSource = errorDataSource)
            .execute(call)
    }

    override fun rating(id: Int) {
        val call = apiService.locationRating(id, userDataStore.token())
        CloudDataSource<ResponseBody>(errorDataSource = errorDataSource).execute(call)
    }

    override fun save(data: LocationModel) {
        userDataStore.locationSave(data)
    }

}