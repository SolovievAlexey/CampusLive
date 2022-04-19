package ru.campus.live.core.di.module.viewmodel

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.campus.live.core.di.module.viewmodel.base.BaseViewModelModule
import ru.campus.live.core.di.module.viewmodel.base.ViewModelKey
import ru.campus.live.core.presentation.main.MainViewModel

@Module
abstract class MainVModule : BaseViewModelModule() {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun mainViewModel(viewModel: MainViewModel): ViewModel

}