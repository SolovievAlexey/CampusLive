package ru.campus.live.core.di.coroutines

import kotlinx.coroutines.CoroutineDispatcher

class Dispatchers : IDispatchers {

    override val main: CoroutineDispatcher
        get() = kotlinx.coroutines.Dispatchers.Main
    override val io: CoroutineDispatcher
        get() = kotlinx.coroutines.Dispatchers.IO

}