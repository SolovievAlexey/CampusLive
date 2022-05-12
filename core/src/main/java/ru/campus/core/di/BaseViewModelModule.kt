package ru.campus.core.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import ru.campus.core.presentation.ViewModelFactory

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 12.05.2022 22:19
 */

@Module
abstract class BaseViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}