package ru.campus.core.data

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 15.05.2022 19:15
 */

class BaseDomainDataStore @Inject constructor(context: Context) : DomainDataStore {

    private val sPref: SharedPreferences =
        context.getSharedPreferences("AppDB", Context.MODE_PRIVATE)

    override fun get(): String {
        return sPref.getString("DOMAIN", "http://apiburg.beget.tech") ?: "http://apiburg.beget.tech"
    }

}