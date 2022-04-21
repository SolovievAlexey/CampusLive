package ru.campus.live.core.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import ru.campus.live.core.data.repository.IUploadMediaRepository
import ru.campus.live.core.data.repository.UploadMediaRepository
import ru.campus.live.core.di.coroutines.IDispatchers
import ru.campus.live.core.di.module.viewmodel.DiscussionVModule
import ru.campus.live.discussion.data.model.DiscussionModel
import ru.campus.live.discussion.data.repository.DiscussionRepository
import ru.campus.live.discussion.data.repository.IDiscussionRepository
import ru.campus.live.discussion.domain.DiscussionInteractor
import ru.campus.live.discussion.presentation.viewmodel.DiscussionViewModel

@Module(includes = [DiscussionBindModule::class, DiscussionVModule::class])
class DiscussionModule

@Module
interface DiscussionBindModule {

    @Binds
    fun bindDiscussionRepository(discussionRepository: DiscussionRepository): IDiscussionRepository

    @Binds
    fun bindUploadMediaRepository(uploadMediaRepository: UploadMediaRepository): IUploadMediaRepository

}


