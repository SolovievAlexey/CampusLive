package ru.campus.feature_discussion.di

import dagger.Component
import ru.campus.core.di.AppDeps
import ru.campus.core.presentation.ViewModelFactory
import ru.campus.feature_discussion.presentation.fragment.DiscussionBottomSheetFragment
import ru.campus.feature_discussion.presentation.fragment.DiscussionFragment

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 21.05.2022 16:22
 */

@Component(modules = [DiscussionModule::class], dependencies = [AppDeps::class])
@DiscussionScope
interface DiscussionComponent {

    fun viewModelsFactory(): ViewModelFactory
    fun inject(discussionFragment: DiscussionFragment)
    fun inject(discussionFragment: DiscussionBottomSheetFragment)

    @Component.Builder
    interface Builder {
        fun deps(appDeps: AppDeps): Builder
        fun build(): DiscussionComponent
    }

}

