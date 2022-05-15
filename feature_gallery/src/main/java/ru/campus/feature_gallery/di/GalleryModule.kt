package ru.campus.feature_gallery.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.campus.core.di.ViewModelKey
import ru.campus.feature_gallery.data.BaseGalleryDataSource
import ru.campus.feature_gallery.data.GalleryRepository
import ru.campus.feature_gallery.presentation.GalleryViewModel

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 15.05.2022 23:07
 */

@Module(includes = [GalleryAbstractModule::class])
class GalleryModule

@Module
interface GalleryAbstractModule {

    @Binds
    fun bindNewsRepository(baseGalleryRepository: BaseGalleryDataSource): GalleryRepository

    @Binds
    @IntoMap
    @ViewModelKey(GalleryViewModel::class)
    abstract fun onBoardViewModel(viewModel: GalleryViewModel): ViewModel

}