package ru.campus.core.data

import org.jetbrains.annotations.NotNull

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 12.05.2022 20:14
 */

sealed class ResponseObject<T: Any> {
    class Success<T : Any>(@NotNull val data: T): ResponseObject<T>()
    class Failure<T : Any>(val code: Int): ResponseObject<T>()
}