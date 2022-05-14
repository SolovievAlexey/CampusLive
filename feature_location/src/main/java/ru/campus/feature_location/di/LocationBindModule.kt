package ru.campus.feature_location.di

import dagger.Binds
import dagger.Module
import ru.campus.feature_location.data.BaseLocationRepository
import ru.campus.feature_location.data.LocationRepository

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 14.05.2022 17:51
 */

@Module
interface LocationBindModule {

    @Binds
    fun bindLocationRepository(baseLocationRepository: BaseLocationRepository): LocationRepository
}