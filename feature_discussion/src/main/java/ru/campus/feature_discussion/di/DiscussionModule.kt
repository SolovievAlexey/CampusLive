package ru.campus.feature_discussion.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit
import ru.campus.core.data.ErrorMessageHandler
import ru.campus.core.di.ViewModelKey
import ru.campus.feature_discussion.data.APIService
import ru.campus.feature_discussion.data.repository.*
import ru.campus.feature_discussion.presentation.viewmodel.CreateCommentViewModel
import ru.campus.feature_discussion.presentation.viewmodel.DiscussionViewModel
import ru.campus.file_upload.data.BaseUploadMediaRepository
import ru.campus.file_upload.data.UploadMediaRepository
import ru.campus.file_upload.data.UploadMediaService
import ru.campus.file_upload.domain.PreparationMediaUseCase

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 21.05.2022 16:22
 */

@Module(includes = [DiscussionAbstractModule::class])
class DiscussionModule {

    @Provides
    @DiscussionScope
    fun provideAPIService(retrofit: Retrofit): APIService {
        return retrofit.create(APIService::class.java)
    }

    @Provides
    @DiscussionScope
    fun provideUploadMediaServices(retrofit: Retrofit): UploadMediaService {
        return retrofit.create(UploadMediaService::class.java)
    }

    @Provides
    @DiscussionScope
    fun provideUploadMediaRepository(
        uploadMediaService: UploadMediaService,
        preparationMediaUseCase: PreparationMediaUseCase
    ): BaseUploadMediaRepository {
        return BaseUploadMediaRepository(uploadMediaService, preparationMediaUseCase)
    }

}

@Module
interface DiscussionAbstractModule {

    @Binds
    @DiscussionScope
    fun bindDiscussionRepository(baseDiscussionRepository: BaseDiscussionRepository): DiscussionRepository

    @Binds
    @DiscussionScope
    fun bindUploadMediaRepository(baseUploadMediaRepository: BaseUploadMediaRepository): UploadMediaRepository

    @Binds
    @DiscussionScope
    fun bindUserAvatarStore(baseDataAvatarStore: BaseDataAvatarStore): UserAvatarStore

    @Binds
    @DiscussionScope
    fun bindErrorMessageHandler(errorDataSource: ErrorDataSource): ErrorMessageHandler

    @Binds
    @IntoMap
    @ViewModelKey(CreateCommentViewModel::class)
    fun createCommentViewModel(viewModel: CreateCommentViewModel): ViewModel

}