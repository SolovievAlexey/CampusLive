package ru.campus.core.di

import android.content.Context
import retrofit2.Retrofit

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 12.05.2022 21:43
 */

interface AppDeps {
    var context: Context
    var retrofit: Retrofit
    var coroutineDispatchers: CoroutineDispatchers
}