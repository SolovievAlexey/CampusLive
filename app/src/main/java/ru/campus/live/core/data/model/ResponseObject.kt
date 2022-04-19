package ru.campus.live.core.data.model

sealed class ResponseObject<T> {
    class Success<T>(val data: T) : ResponseObject<T>()
    class Failure<T>(val error: ErrorObject) : ResponseObject<T>()
}