package ru.campus.live.core

import android.app.Application
import ru.campus.core.di.AppComponent
import ru.campus.core.di.AppDepsStore
import ru.campus.core.di.DaggerAppComponent


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