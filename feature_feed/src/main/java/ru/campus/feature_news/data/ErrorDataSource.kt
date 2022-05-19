package ru.campus.feature_news.data

import ru.campus.core.data.BaseResourceManager
import ru.campus.feature_news.R
import javax.inject.Inject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 19.05.2022 20:40
 */

class ErrorDataSource @Inject constructor(private val resourceManager: BaseResourceManager) {

    fun get(statusCode: Int): String {
        return when (statusCode) {
            0 -> resourceManager.get(R.string.error_network_connection)
            404 -> resourceManager.get(R.string.error_404)
            else -> resourceManager.get(R.string.error_unknown)
        }
    }

}