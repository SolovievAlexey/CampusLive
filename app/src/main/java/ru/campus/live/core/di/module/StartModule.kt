package ru.campus.live.core.di.module

import dagger.Binds
import dagger.Module
import ru.campus.live.core.di.module.viewmodel.StartVModule
import ru.campus.live.start.data.repository.IStartRepository
import ru.campus.live.start.data.repository.IUserRepository
import ru.campus.live.start.data.repository.StartRepository
import ru.campus.live.start.data.repository.UserRepository
import ru.campus.live.start.domain.IStartInteractor
import ru.campus.live.start.domain.StartInteractor

@Module(includes = [StartBindModule::class, StartVModule::class])
class StartModule

@Module
interface StartBindModule {
    @Binds
    fun bindStartRepository(startRepository: StartRepository): IStartRepository

    @Binds
    fun bindStartInteractor(startInteractor: StartInteractor): IStartInteractor

    @Binds
    fun bindUserRepository(userRepository: UserRepository): IUserRepository
}

