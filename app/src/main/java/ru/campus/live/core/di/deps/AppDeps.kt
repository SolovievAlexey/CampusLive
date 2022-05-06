package ru.campus.live.core.di.deps

import android.content.Context
import ru.campus.live.core.di.coroutines.IDispatchers
import ru.campus.live.core.data.APIService
import ru.campus.live.core.data.source.IUserDataStore
import ru.campus.live.core.data.source.UserDataStore
import ru.campus.live.ribbon.data.db.AppDatabase

interface AppDeps {
    var apiService: APIService
    var appDatabase: AppDatabase
    var context: Context
    var userDatabase: UserDataStore
    val userDataStore: IUserDataStore
    val dispatchers: IDispatchers
}