package ru.campus.live.core.di.module

import dagger.Binds
import dagger.Module
import ru.campus.live.core.data.repository.IUploadMediaRepository
import ru.campus.live.core.data.repository.UploadMediaRepository
import ru.campus.live.core.di.module.viewmodel.DiscussionVModule
import ru.campus.live.discussion.data.repository.DiscussionRepository
import ru.campus.live.discussion.data.repository.IDiscussionRepository

@Module(includes = [DiscussionBindModule::class, DiscussionVModule::class])
class DiscussionModule

@Module
interface DiscussionBindModule {

    @Binds
    fun bindDiscussionRepository(discussionRepository: DiscussionRepository): IDiscussionRepository

    @Binds
    fun bindUploadMediaRepository(uploadMediaRepository: UploadMediaRepository): IUploadMediaRepository



}