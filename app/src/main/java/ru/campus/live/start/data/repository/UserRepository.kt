package ru.campus.live.start.data.repository

import ru.campus.live.core.data.APIService
import ru.campus.live.core.data.source.CloudDataSource
import ru.campus.live.core.data.source.ErrorDataSource
import ru.campus.live.core.data.source.UserDataSource
import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.start.data.model.LoginModel
import ru.campus.live.start.data.model.OSType
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: APIService,
    private val errorDataSource: ErrorDataSource,
    private val userDataSource: UserDataSource
) : IUserRepository {

    override fun registration(): ResponseObject<LoginModel> {
        val call = apiService.registration(os = OSType.ANDROID.ordinal)
        return CloudDataSource<LoginModel>(errorDataSource = errorDataSource)
            .execute(call)
    }

    override fun login(userData: LoginModel): Boolean {
        return userDataSource.login(userData)
    }

}