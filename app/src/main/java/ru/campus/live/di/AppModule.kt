package ru.campus.live.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.campus.core.di.BaseCoroutineDispatchers
import ru.campus.core.di.CoroutineDispatchers
import javax.inject.Singleton

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 12.05.2022 21:58
 */

@Module(includes = [AppBindModule::class])
class AppModule {

    @Provides
    fun provideAPIService(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://apiburg.beget.tech")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideCoroutineDispatchers(): BaseCoroutineDispatchers {
        return BaseCoroutineDispatchers()
    }

}

@Module
interface AppBindModule {

    @Binds
    fun bindDispatchers(dispatchers: BaseCoroutineDispatchers): CoroutineDispatchers

}