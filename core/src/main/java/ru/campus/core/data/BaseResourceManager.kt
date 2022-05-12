package ru.campus.core.data

import android.content.Context
import androidx.annotation.StringRes

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 12.05.2022 19:56
 */


class BaseResourceManager(private val context: Context): ResourceManager {

    override fun get(@StringRes id: Int): String {
        return context.getString(id)
    }

}