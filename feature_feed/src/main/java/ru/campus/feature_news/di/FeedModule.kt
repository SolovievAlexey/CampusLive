package ru.campus.feature_news.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.campus.core.di.ViewModelKey
import ru.campus.feature_news.data.BaseNewsRepository
import ru.campus.feature_news.data.NewsRepository
import ru.campus.feature_news.presentation.FeedViewModel

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 15.05.2022 18:55
 */

@Module
interface FeedModule {

    @Binds
    fun bindNewsRepository(baseNewsRepository: BaseNewsRepository): NewsRepository

    @Binds
    @IntoMap
    @ViewModelKey(FeedViewModel::class)
    abstract fun onBoardViewModel(viewModel: FeedViewModel): ViewModel

}