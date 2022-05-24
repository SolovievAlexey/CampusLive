package ru.campus.core.di

import kotlinx.coroutines.CoroutineDispatcher

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 12.05.2022 20:37
 */

class BaseCoroutineDispatchers : CoroutineDispatchers {
    override val main: CoroutineDispatcher
        get() = kotlinx.coroutines.Dispatchers.Main
    override val io: CoroutineDispatcher
        get() = kotlinx.coroutines.Dispatchers.IO
}