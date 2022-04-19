package ru.campus.live.start.domain

import ru.campus.live.core.data.model.ResponseObject
import ru.campus.live.start.data.model.LoginModel
import ru.campus.live.start.data.model.StartModel
import ru.campus.live.start.data.repository.IStartRepository
import ru.campus.live.start.data.repository.IUserRepository
import javax.inject.Inject

class StartInteractor @Inject constructor(
    private val startRepository: IStartRepository,
    private val userRepository: IUserRepository
) : IStartInteractor {

    override fun start(): ArrayList<StartModel> {
        return startRepository.start()
    }

    override fun login(): ResponseObject<LoginModel> {
        val result = userRepository.registration()
        if (result is ResponseObject.Success<LoginModel>)
            userRepository.login(result.data)
        return result
    }

}
