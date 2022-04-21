package ru.campus.live.core.di.component

import dagger.Component
import ru.campus.live.core.di.deps.AppDeps
import ru.campus.live.core.di.module.DiscussionModule
import ru.campus.live.core.di.module.viewmodel.base.ViewModelFactory
import ru.campus.live.discussion.presentation.fragments.DiscussionFragment

@Component(modules = [DiscussionModule::class], dependencies = [AppDeps::class])
interface DiscussionComponent {

    fun viewModelsFactory(): ViewModelFactory
    fun inject(discussionFragment: DiscussionFragment)

    @Component.Builder
    interface Builder {
        fun deps(appDeps: AppDeps): Builder
        fun build(): DiscussionComponent
    }

}