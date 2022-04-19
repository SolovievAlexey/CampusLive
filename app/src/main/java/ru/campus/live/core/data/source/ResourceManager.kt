package ru.campus.live.core.data.source

import android.content.Context
import javax.inject.Inject

class ResourceManager @Inject constructor(
    private val context: Context
) {

    fun get(id: Int): String = context.getString(id)

}