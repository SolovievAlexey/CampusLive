package ru.campus.live.core.di.component

import dagger.Component
import ru.campus.live.core.di.deps.AppDeps
import ru.campus.live.core.di.module.FeedModule
import ru.campus.live.core.di.module.viewmodel.base.ViewModelFactory

@Component(modules = [FeedModule::class], dependencies = [AppDeps::class])
interface FeedComponent {

    fun viewModelsFactory(): ViewModelFactory

    @Component.Builder
    interface Builder {
        fun deps(appDeps: AppDeps): Builder
        fun build(): FeedComponent
    }

}