package ru.campus.live.core.di.module

import dagger.Binds
import dagger.Module
import ru.campus.live.core.di.module.viewmodel.LocationVModule
import ru.campus.live.location.data.repository.ILocationRepository
import ru.campus.live.location.data.repository.LocationRepository

@Module(includes = [LocationBindModule::class, LocationVModule::class])
class LocationModule

@Module
interface LocationBindModule {

    @Binds
    fun bindLocationRepository(locationRepository: LocationRepository): ILocationRepository

}