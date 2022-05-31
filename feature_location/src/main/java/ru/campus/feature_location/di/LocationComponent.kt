package ru.campus.feature_location.di

import dagger.Component
import ru.campus.core.di.AppDeps
import ru.campus.core.presentation.ViewModelFactory
import javax.inject.Scope

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 14.05.2022 17:03
 */

@Component(modules = [LocationModule::class], dependencies = [AppDeps::class])
@LocationScope
interface LocationComponent {

    fun viewModelsFactory(): ViewModelFactory

    @Component.Builder
    interface Builder {
        fun deps(appDeps: AppDeps): Builder
        fun build(): LocationComponent
    }

}