package ru.campus.core.di

import kotlin.properties.Delegates

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 12.05.2022 21:57
 */

interface AppDepsProvider {
    val deps: AppDeps
    companion object : AppDepsProvider by AppDepsStore
}

object AppDepsStore : AppDepsProvider {
    override var deps: AppDeps by Delegates.notNull()
}