package ru.campus.feature_location.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.campus.core.di.BaseViewModelModule
import ru.campus.core.di.ViewModelKey
import ru.campus.feature_location.presentation.LocationViewModel

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 14.05.2022 17:59
 */

@Module
abstract class LocationViewModelModule : BaseViewModelModule() {

    @Binds
    @IntoMap
    @ViewModelKey(LocationViewModel::class)
    abstract fun onBoardViewModel(viewModel: LocationViewModel): ViewModel

}