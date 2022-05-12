package ru.campus.core.data

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 12.05.2022 20:26
 */

interface UserDataStore {
    fun login(uid: Int, token: String): Boolean
}