package ru.campus.core.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 12.05.2022 21:58
 */

@Module(includes = [AppBindModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideCoroutineDispatchers(): CoroutineDispatchers {
        return BaseCoroutineDispatchers()
    }

}

@Module
interface AppBindModule {

    @Binds
    fun bindDispatchers(dispatchers: BaseCoroutineDispatchers): CoroutineDispatchers

}