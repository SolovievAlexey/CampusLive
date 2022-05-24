package ru.campus.core.presentation

import android.view.View

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 14.05.2022 17:42
 */

interface MyOnClick<T> {
    fun item(view: View, item: T)
}