package ru.campus.live.core.di.module.viewmodel

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.campus.live.core.di.module.viewmodel.base.BaseViewModelModule
import ru.campus.live.core.di.module.viewmodel.base.ViewModelKey
import ru.campus.live.gallery.presentation.GalleryViewModel

@Module
abstract class GalleryVModule : BaseViewModelModule() {

    @Binds
    @IntoMap
    @ViewModelKey(GalleryViewModel::class)
    abstract fun galleryViewModel(viewModel: GalleryViewModel): ViewModel
}