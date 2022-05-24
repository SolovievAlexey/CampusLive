package ru.campus.feature_start.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.campus.core.data.BaseUserDataStore
import ru.campus.feature_start.data.interfaces.APIService

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

}


