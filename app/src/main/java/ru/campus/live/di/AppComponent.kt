package ru.campus.live.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import retrofit2.Retrofit
import ru.campus.core.data.DomainDataStore
import ru.campus.core.data.ResourceManager
import ru.campus.core.data.UserDataStore
import ru.campus.core.di.AppDeps
import ru.campus.core.di.CoroutineDispatchers
import javax.inject.Singleton

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 12.05.2022 21:42
 */


@Singleton
@Component(modules = [AppModule::class])
interface AppComponent : AppDeps {

    override var context: Context
    override var retrofit: Retrofit
    override var coroutineDispatchers: CoroutineDispatchers
    override var userDataStore: UserDataStore
    override var domainDataStore: DomainDataStore
    override var resourceManager: ResourceManager
    
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): AppComponent
    }

}