package ru.campus.feature_start.data.repository

import ru.campus.core.data.CloudDataSource
import ru.campus.core.data.ResponseObject
import ru.campus.core.data.UserDataStore
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
    private val userDataStore: UserDataStore
) : UserRepository {

    override fun registration(): ResponseObject<LoginModel> {
        val call = apiService.registration(os = OSType.ANDROID.ordinal)
        return CloudDataSource<LoginModel>().execute(call = call)
    }

    override fun login(params: LoginModel) {
        return userDataStore.login(uid = params.uid, token = params.token)
    }

}