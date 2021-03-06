package ru.campus.core.di

import android.content.Context
import retrofit2.Retrofit
import ru.campus.core.data.DomainDataStore
import ru.campus.core.data.ResourceManager
import ru.campus.core.data.UserDataStore

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 12.05.2022 21:43
 */

interface AppDeps {
    var context: Context
    var retrofit: Retrofit
    var coroutineDispatchers: CoroutineDispatchers
    var userDataStore: UserDataStore
    var domainDataStore: DomainDataStore
    var resourceManager: ResourceManager
}