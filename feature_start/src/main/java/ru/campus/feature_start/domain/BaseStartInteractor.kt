package ru.campus.feature_start.domain

import ru.campus.core.data.ResponseObject
import ru.campus.feature_start.data.model.LoginModel
import ru.campus.feature_start.data.model.StartModel
import ru.campus.feature_start.data.repository.ErrorDataSource
import ru.campus.feature_start.data.repository.StartRepository
import ru.campus.feature_start.data.repository.UserRepository
import javax.inject.Inject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 12.05.2022 20:40
 */

class BaseStartInteractor @Inject constructor(
    private val startRepository: StartRepository,
    private val userRepository: UserRepository,
    private val errorDataSource: ErrorDataSource
) : StartInteractor {

    override fun start(): List<StartModel> {
        return startRepository.start()
    }

    override fun login(): ResponseObject<LoginModel> {
        val result = userRepository.registration()
        if (result is ResponseObject.Success)
            userRepository.login(result.data)
        return result
    }

    override fun error(statusCode: Int): String {
        return errorDataSource.get(statusCode = statusCode)
    }

}