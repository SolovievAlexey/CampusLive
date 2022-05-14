package ru.campus.feature_location.data

import okhttp3.ResponseBody
import ru.campus.core.data.CloudDataSource
import ru.campus.core.data.ResponseObject
import ru.campus.core.data.UserDataStore
import javax.inject.Inject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 14.05.2022 16:46
 */

class BaseLocationRepository @Inject constructor(
    private val apiService: APIService,
    private val userDataStore: UserDataStore
) : LocationRepository {

    override fun get(name: String?): ResponseObject<List<LocationModel>> {
        val call = apiService.location(token = userDataStore.token(), name = name)
        return CloudDataSource<List<LocationModel>>().execute(call = call)
    }

    override fun rating(id: Int) {
        val call = apiService.locationRating(token = userDataStore.token(), id = id)
        CloudDataSource<ResponseBody>().execute(call = call)
    }

    override fun save(params: LocationModel) {
        userDataStore.locationSave(id = params.id, name = params.name)
    }

}