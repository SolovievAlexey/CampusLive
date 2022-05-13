package ru.campus.live

import android.app.Application
import ru.campus.core.di.AppDepsStore
import ru.campus.live.di.AppComponent
import ru.campus.live.di.DaggerAppComponent

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 13.05.2022 22:22
 */

class App : Application() {

    private val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .context(context = this)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        AppDepsStore.deps = appComponent
    }
}