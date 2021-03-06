package ru.campus.live.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.campus.core.data.*
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
    @Singleton
    fun provideAPIService(domainDataStore: DomainDataStore): Retrofit {
        return Retrofit.Builder()
            .baseUrl(domainDataStore.get()+"/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideCoroutineDispatchers(): BaseCoroutineDispatchers {
        return BaseCoroutineDispatchers()
    }

}

@Module
interface AppBindModule {

    @Binds
    @Singleton
    fun bindDispatchers(dispatchers: BaseCoroutineDispatchers): CoroutineDispatchers

    @Binds
    @Singleton
    fun bindUserDataStore(baseUserDataStore: BaseUserDataStore): UserDataStore

    @Binds
    @Singleton
    fun bindDomainDataStore(domainDataStore: BaseDomainDataStore): DomainDataStore

    @Binds
    @Singleton
    fun bindResourceManager(baseResourceManager: BaseResourceManager): ResourceManager

}