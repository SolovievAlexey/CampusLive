package ru.campus.live.core.di.module.viewmodel

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.campus.live.core.di.module.viewmodel.base.BaseViewModelModule
import ru.campus.live.core.di.module.viewmodel.base.ViewModelKey
import ru.campus.live.location.presentation.LocationViewModel

@Module
abstract class LocationVModule: BaseViewModelModule() {

    @Binds
    @IntoMap
    @ViewModelKey(LocationViewModel::class)
    abstract fun locationViewModel(viewModel: LocationViewModel): ViewModel

}