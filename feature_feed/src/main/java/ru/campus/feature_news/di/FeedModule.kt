package ru.campus.feature_news.di

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit
import ru.campus.core.di.ViewModelKey
import ru.campus.feature_news.data.APIService
import ru.campus.feature_news.data.BaseNewsRepository
import ru.campus.feature_news.data.NewsRepository
import ru.campus.feature_news.presentation.viewmodel.CreateMessageViewModel
import ru.campus.feature_news.presentation.viewmodel.FeedViewModel
import ru.campus.file_upload.data.BaseUploadMediaRepository
import ru.campus.file_upload.data.UploadMediaRepository
import ru.campus.file_upload.data.UploadMediaService
import ru.campus.file_upload.domain.PreparationMediaUseCase

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 15.05.2022 18:55
 */

@Module(includes = [FeedAbstractModule::class])
class FeedModule {

    @Provides
    fun provideAPIService(retrofit: Retrofit): APIService {
        return retrofit.create(APIService::class.java)
    }

    @Provides
    fun provideUploadMediaServices(retrofit: Retrofit): UploadMediaService {
        return retrofit.create(UploadMediaService::class.java)
    }

    @Provides
    fun provideBaseUploadMediaRepository(
        uploadMediaService: UploadMediaService,
        preparationMediaUseCase: PreparationMediaUseCase,
    ): BaseUploadMediaRepository {
        return BaseUploadMediaRepository(uploadMediaService, preparationMediaUseCase)
    }

    @Provides
    fun providePreparationMediaUseCase(context: Context): PreparationMediaUseCase {
        return PreparationMediaUseCase(context)
    }

}

@Module
interface FeedAbstractModule {

    @Binds
    fun bindNewsRepository(baseNewsRepository: BaseNewsRepository): NewsRepository

    @Binds
    fun bindUploadMediaRepository(baseUploadMediaRepository: BaseUploadMediaRepository): UploadMediaRepository

    @Binds
    @IntoMap
    @ViewModelKey(FeedViewModel::class)
    fun onBoardViewModel(viewModel: FeedViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreateMessageViewModel::class)
    fun createMessageViewModel(viewModel: CreateMessageViewModel): ViewModel
}