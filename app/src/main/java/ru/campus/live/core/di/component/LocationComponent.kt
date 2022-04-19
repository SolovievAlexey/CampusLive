package ru.campus.live.core.di.component

import dagger.Component
import ru.campus.live.core.di.deps.AppDeps
import ru.campus.live.core.di.module.LocationModule
import ru.campus.live.core.di.module.viewmodel.LocationVModule
import ru.campus.live.core.di.module.viewmodel.base.ViewModelFactory

@Component(modules = [LocationModule::class], dependencies = [AppDeps::class])
interface LocationComponent {

    fun viewModelsFactory(): ViewModelFactory

    @Component.Builder
    interface Builder {
        fun deps(appDeps: AppDeps): Builder
        fun build(): LocationComponent
    }

}