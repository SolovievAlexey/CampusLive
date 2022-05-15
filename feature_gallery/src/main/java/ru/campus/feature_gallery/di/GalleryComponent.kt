package ru.campus.feature_gallery.di

import dagger.Component
import ru.campus.core.di.AppDeps
import ru.campus.core.presentation.ViewModelFactory

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 15.05.2022 23:07
 */

@Component(modules = [GalleryModule::class], dependencies = [AppDeps::class])
interface GalleryComponent {

    fun viewModelsFactory(): ViewModelFactory

    @Component.Builder
    interface Builder {
        fun deps(appDeps: AppDeps): Builder
        fun build(): GalleryComponent
    }

}