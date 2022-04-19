package ru.campus.live.core.di.module

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.campus.live.core.di.coroutines.IDispatchers
import ru.campus.live.core.di.coroutines.Dispatchers
import ru.campus.live.core.data.APIService
import ru.campus.live.core.data.source.HostDataSource
import ru.campus.live.core.data.source.IUserDataSource
import ru.campus.live.core.data.source.UserDataSource
import ru.campus.live.ribbon.data.db.AppDatabase
import javax.inject.Singleton

@Module(includes = [AppBindModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideAPIService(hostDataSource: HostDataSource): APIService {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(hostDataSource.domain())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(APIService::class.java)
    }

    @Singleton
    @Provides
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "publication_db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideDispatchersImpl(): Dispatchers {
        return Dispatchers()
    }

    @Singleton
    @Provides
    fun provideUserDataSource(context: Context): UserDataSource {
        return UserDataSource(context = context)
    }

}

@Module
interface AppBindModule {

    @Singleton
    @Binds
    fun bindUserDataSource(userDataSource: UserDataSource): IUserDataSource

    @Binds
    fun bindDispatchers(dispatchers: Dispatchers): IDispatchers

}