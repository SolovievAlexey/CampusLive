package ru.campus.live.core.presentation

import android.view.View

interface MyOnClick<T> {
    fun item(view: View, item: T)
}