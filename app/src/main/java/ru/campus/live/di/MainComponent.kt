package ru.campus.live.di

import dagger.Component
import ru.campus.core.data.UserDataStore
import ru.campus.core.di.AppDeps
import ru.campus.core.presentation.ViewModelFactory
import ru.campus.live.presentation.MainActivity
import javax.inject.Singleton

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 13.05.2022 22:30
 */

@Component(modules = [MainModule::class], dependencies = [AppDeps::class])
@Singleton
interface MainComponent {

    fun inject(activity: MainActivity)

    @Component.Builder
    interface Builder {
        fun deps(appDeps: AppDeps): Builder
        fun build(): MainComponent
    }

}