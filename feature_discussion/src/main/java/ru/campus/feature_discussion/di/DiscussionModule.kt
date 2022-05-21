package ru.campus.feature_discussion.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit
import ru.campus.core.di.ViewModelKey
import ru.campus.feature_discussion.data.APIService
import ru.campus.feature_discussion.data.repository.BaseDiscussionRepository
import ru.campus.feature_discussion.data.repository.DiscussionRepository
import ru.campus.feature_discussion.presentation.viewmodel.CreateCommentViewModel
import ru.campus.feature_discussion.presentation.viewmodel.DiscussionViewModel

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 21.05.2022 16:22
 */

@Module(includes = [DiscussionAbstractModule::class])
class DiscussionModule {

    @Provides
    fun provideAPIService(retrofit: Retrofit): APIService {
        return retrofit.create(APIService::class.java)
    }

}

@Module
interface DiscussionAbstractModule {

    @Binds
    fun bindDiscussionRepository(baseDiscussionRepository: BaseDiscussionRepository): DiscussionRepository

    @Binds
    @IntoMap
    @ViewModelKey(DiscussionViewModel::class)
    fun discussionViewModel(viewModel: DiscussionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreateCommentViewModel::class)
    fun createCommentViewModel(viewModel: CreateCommentViewModel): ViewModel

}