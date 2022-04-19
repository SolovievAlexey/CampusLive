package ru.campus.live.core.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.campus.live.core.data.APIService
import ru.campus.live.core.data.source.IUserDataSource
import ru.campus.live.core.data.source.UserDataSource
import ru.campus.live.core.di.deps.AppDeps
import ru.campus.live.core.di.module.AppModule
import ru.campus.live.ribbon.data.db.AppDatabase
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent : AppDeps {

    override var apiService: APIService
    override var appDatabase: AppDatabase
    override var context: Context
    override var userDatabase: UserDataSource
    override val userDataSource: IUserDataSource

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): AppComponent
    }

}