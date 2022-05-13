package ru.campus.live.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.campus.core.di.BaseViewModelModule
import ru.campus.core.di.ViewModelKey
import ru.campus.live.presentation.MainViewModel

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 13.05.2022 22:36
 */

@Module
abstract class MainVModule : BaseViewModelModule() {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun mainViewModel(viewModel: MainViewModel): ViewModel

}