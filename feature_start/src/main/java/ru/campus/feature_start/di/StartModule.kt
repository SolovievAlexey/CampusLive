package ru.campus.feature_start.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.campus.core.data.BaseResourceManager
import ru.campus.core.data.BaseUserDataStore
import ru.campus.core.data.ResourceManager
import ru.campus.core.data.UserDataStore
import ru.campus.feature_start.data.interfaces.APIService
import ru.campus.feature_start.data.repository.BaseStartRepository
import ru.campus.feature_start.data.repository.BaseUserRepository
import ru.campus.feature_start.data.repository.StartRepository
import ru.campus.feature_start.data.repository.UserRepository
import ru.campus.feature_start.domain.BaseStartInteractor
import ru.campus.feature_start.domain.StartInteractor

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 12.05.2022 22:11
 */

@Module(includes = [StartBindModule::class, StartViewModelModule::class])
class StartModule {

    @Provides
    fun provideAPIService(retrofit: Retrofit): APIService {
        return retrofit.create(APIService::class.java)
    }

    @Provides
    fun provideUserDataStore(): BaseUserDataStore {
        return BaseUserDataStore()
    }

}

@Module
interface StartBindModule {

    @Binds
    fun bindResourceManager(resourceManager: BaseResourceManager): ResourceManager

    @Binds
    fun bindStartRepository(startRepository: BaseStartRepository): StartRepository

    @Binds
    fun bindStartInteractor(startInteractor: BaseStartInteractor): StartInteractor

    @Binds
    fun bindUserRepository(baseUserRepository: BaseUserRepository): UserRepository

    @Binds
    fun bindUserDataStore(baseUserDataStore: BaseUserDataStore): UserDataStore

}

