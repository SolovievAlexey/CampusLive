package ru.campus.feature_start.data.repository

import ru.campus.core.data.ErrorMessageHandler
import ru.campus.core.data.ResourceManager
import ru.campus.feature_start.R
import javax.inject.Inject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 24.05.2022 21:14
 */

class ErrorDataSource @Inject constructor(
    private val resourceManager: ResourceManager
) : ErrorMessageHandler {

    override fun get(statusCode: Int): String {
        return if (statusCode == 0)
            resourceManager.get(R.string.start_error_connection)
        else
            resourceManager.get(R.string.start_error_unknown)
    }

}