package ru.campus.live.core.di.module.viewmodel

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.campus.live.core.di.module.viewmodel.base.BaseViewModelModule
import ru.campus.live.core.di.module.viewmodel.base.ViewModelKey
import ru.campus.live.discussion.presentation.viewmodel.DiscussionCreateViewModel
import ru.campus.live.discussion.presentation.viewmodel.DiscussionViewModel

@Module
abstract class DiscussionVModule : BaseViewModelModule() {

    @Binds
    @IntoMap
    @ViewModelKey(DiscussionViewModel::class)
    abstract fun discussionViewModel(viewModel: DiscussionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DiscussionCreateViewModel::class)
    abstract fun discussionCreateViewModel(viewModel: DiscussionCreateViewModel): ViewModel

}