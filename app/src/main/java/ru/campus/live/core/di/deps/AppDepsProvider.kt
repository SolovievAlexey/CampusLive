package ru.campus.live.core.di.deps

import kotlin.properties.Delegates.notNull

interface AppDepsProvider {
    val deps: AppDeps

    companion object : AppDepsProvider by AppDepsStore
}

object AppDepsStore : AppDepsProvider {
    override var deps: AppDeps by notNull()
}