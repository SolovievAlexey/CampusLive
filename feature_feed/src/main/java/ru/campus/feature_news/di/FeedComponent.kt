package ru.campus.feature_news.di

import dagger.Component
import ru.campus.core.di.AppDeps
import ru.campus.core.presentation.ViewModelFactory
import ru.campus.feature_news.presentation.fragment.FeedFragment

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 15.05.2022 18:54
 */

@Component(modules = [FeedModule::class], dependencies = [AppDeps::class])
interface FeedComponent {

    fun viewModelsFactory(): ViewModelFactory
    fun inject(fragment: FeedFragment)

    @Component.Builder
    interface Builder {
        fun deps(appDeps: AppDeps): Builder
        fun build(): FeedComponent
    }

}