package ru.campus.feature_news.data.repository

import ru.campus.core.data.BaseResourceManager
import ru.campus.feature_news.R
import javax.inject.Inject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 19.05.2022 22:13
 */

class ErrorDataSource @Inject constructor(private val resource: BaseResourceManager) {

    fun get(statusCode: Int): String {
        return when(statusCode) {
            0 -> resource.get(R.string.error_network_connection)
            401 -> resource.get(R.string.error_401)
            403 -> resource.get(R.string.error_403)
            404 -> resource.get(R.string.error_404)
            405 -> resource.get(R.string.error_wall_405)
            407 -> resource.get(R.string.error_wall_407)
            408 -> resource.get(R.string.error_408)
            else -> resource.get(R.string.error_unknown)
        }
    }

}