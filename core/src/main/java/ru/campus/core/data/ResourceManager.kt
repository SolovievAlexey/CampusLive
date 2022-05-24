package ru.campus.core.data

import androidx.annotation.StringRes

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 12.05.2022 19:55
 */

interface ResourceManager {
    fun get(@StringRes id: Int): String
}