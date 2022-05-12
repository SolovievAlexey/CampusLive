package ru.campus.core.data

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 12.05.2022 20:14
 */

sealed class ResponseObject<T> {
    class Success<T>(val data: T) : ResponseObject<T>()
    class Failure<T>(val code: Int) : ResponseObject<T>()
}