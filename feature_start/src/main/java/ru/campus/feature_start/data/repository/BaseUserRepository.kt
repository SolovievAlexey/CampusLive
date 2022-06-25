package ru.campus.feature_start.data.repository

import kotlinx.coroutines.withContext
import ru.campus.core.data.CloudDataSource
import ru.campus.core.data.ResponseObject
import ru.campus.core.data.UserDataStore
import ru.campus.core.di.CoroutineDispatchers
import ru.campus.feature_start.data.interfaces.APIService
import ru.campus.feature_start.data.model.LoginModel
import ru.campus.feature_start.data.model.OSType
import javax.inject.Inject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 12.05.2022 20:16
 */

class BaseUserRepository @Inject constructor(
    private val apiService: APIService,
    private val dispatchers: CoroutineDispatchers,
    private val userDataStore: UserDataStore
) : UserRepository {

    override suspend fun registration(): ResponseObject<LoginModel> {
        val response: ResponseObject<LoginModel>
        withContext(dispatchers.io) {
            val call = apiService.registration(os = OSType.ANDROID.ordinal)
            response = CloudDataSource<LoginModel>().execute(call = call)
        }
        return response
    }

    override suspend fun login(params: LoginModel) {
        withContext(dispatchers.io) {
            userDataStore.login(uid = params.uid, token = params.token)
        }
    }

}