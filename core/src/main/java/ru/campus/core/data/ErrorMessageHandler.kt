package ru.campus.core.data

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 19.05.2022 21:23
 */

interface ErrorMessageHandler {
    fun get(statusCode: Int): String
}