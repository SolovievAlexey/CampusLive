package ru.campus.feature_start.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.campus.core.di.BaseViewModelModule
import ru.campus.core.di.ViewModelKey
import ru.campus.feature_start.presentation.StartViewModel

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 12.05.2022 22:17
 */

@Module
abstract class StartViewModelModule : BaseViewModelModule() {

    @Binds
    @IntoMap
    @ViewModelKey(StartViewModel::class)
    abstract fun bindStartViewModel(viewModel: StartViewModel): ViewModel

}