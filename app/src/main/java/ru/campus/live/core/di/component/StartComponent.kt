package ru.campus.live.core.di.component

import dagger.Component
import ru.campus.live.core.di.deps.AppDeps
import ru.campus.live.core.di.module.StartModule
import ru.campus.live.core.di.module.viewmodel.base.ViewModelFactory

@Component(modules = [StartModule::class], dependencies = [AppDeps::class])
interface StartComponent {

    fun viewModelsFactory(): ViewModelFactory

    @Component.Builder
    interface Builder {
        fun deps(appDeps: AppDeps): Builder
        fun build(): StartComponent
    }

}