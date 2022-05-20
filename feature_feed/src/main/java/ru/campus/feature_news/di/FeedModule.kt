package ru.campus.feature_news.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit
import ru.campus.core.data.ErrorMessageHandler
import ru.campus.core.di.ViewModelKey
import ru.campus.feature_news.data.APIService
import ru.campus.feature_news.data.db.AppDatabase
import ru.campus.feature_news.data.db.BaseCashDataSource
import ru.campus.feature_news.data.db.CashDataSource
import ru.campus.feature_news.data.repository.BaseNewsRepository
import ru.campus.feature_news.data.repository.NewsRepository
import ru.campus.feature_news.domain.BaseErrorMessageHandler
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

    @Provides
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "publication_db"
        ).build()
    }

}

@Module
interface FeedAbstractModule {

    @Binds
    fun bindNewsRepository(baseNewsRepository: BaseNewsRepository): NewsRepository

    @Binds
    fun bindUploadMediaRepository(baseUploadMediaRepository: BaseUploadMediaRepository): UploadMediaRepository

    @Binds
    fun bindErrorHandler(baseErrorMessageHandler: BaseErrorMessageHandler): ErrorMessageHandler

    @Binds
    fun bindCashDataSource(baseCashDataSource: BaseCashDataSource): CashDataSource

    @Binds
    @IntoMap
    @ViewModelKey(FeedViewModel::class)
    fun onBoardViewModel(viewModel: FeedViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreateMessageViewModel::class)
    fun createMessageViewModel(viewModel: CreateMessageViewModel): ViewModel
}