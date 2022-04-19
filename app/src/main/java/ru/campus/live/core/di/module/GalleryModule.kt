package ru.campus.live.core.di.module

import dagger.Binds
import dagger.Module
import ru.campus.live.core.di.module.viewmodel.GalleryVModule
import ru.campus.live.gallery.data.repository.GalleryDataSource
import ru.campus.live.gallery.data.repository.GalleryRepository
import ru.campus.live.gallery.data.repository.IGalleryDataSource
import ru.campus.live.gallery.data.repository.IGalleryRepository

@Module(includes = [GalleryBindModule::class, GalleryVModule::class])
class GalleryModule

@Module
interface GalleryBindModule {
    @Binds
    fun bindGalleryRepository(galleryRepository: GalleryRepository): IGalleryRepository

    @Binds
    fun bindGalleryDataSource(galleryDataSource: GalleryDataSource): IGalleryDataSource

}