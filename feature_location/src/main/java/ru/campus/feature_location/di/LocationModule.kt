package ru.campus.feature_location.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.campus.feature_location.data.APIService

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 14.05.2022 17:03
 */

@Module(includes = [LocationBindModule::class, LocationViewModelModule::class])
class LocationModule {

    @Provides
    fun provideAPIService(retrofit: Retrofit): APIService {
        return retrofit.create(APIService::class.java)
    }

}