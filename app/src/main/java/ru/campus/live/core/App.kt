package ru.campus.live.core

import android.app.Application
import ru.campus.core.di.AppComponent
import ru.campus.core.di.AppDepsStore
import ru.campus.core.di.DaggerAppComponent

class App : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().context(context = this).build()
        AppDepsStore.deps = appComponent
    }

}