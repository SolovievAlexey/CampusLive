package ru.campus.live.core.di.module.viewmodel

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.campus.live.core.di.module.viewmodel.base.BaseViewModelModule
import ru.campus.live.core.di.module.viewmodel.base.ViewModelKey
import ru.campus.live.ribbon.presentation.viewmodel.RibbonEditorViewModel
import ru.campus.live.ribbon.presentation.viewmodel.RibbonViewModel

@Module
abstract class FeedVModule : BaseViewModelModule() {

    @Binds
    @IntoMap
    @ViewModelKey(RibbonViewModel::class)
    abstract fun feedVM(viewModel: RibbonViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RibbonEditorViewModel::class)
    abstract fun createPublicationViewModel(viewModel: RibbonEditorViewModel): ViewModel

}