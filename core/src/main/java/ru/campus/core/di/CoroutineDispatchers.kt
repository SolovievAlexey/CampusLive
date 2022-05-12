package ru.campus.core.di

import kotlinx.coroutines.CoroutineDispatcher

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 12.05.2022 20:36
 */

interface CoroutineDispatchers {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
}