package ru.campus.feature_news.domain

import ru.campus.core.data.ErrorMessageHandler
import ru.campus.feature_news.data.repository.ErrorDataSource
import javax.inject.Inject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 19.05.2022 20:39
 */

class BaseErrorMessageHandler @Inject constructor(
    private val errorDataSource: ErrorDataSource
) : ErrorMessageHandler {

    override fun get(statusCode: Int): String {
        return errorDataSource.get(statusCode = statusCode)
    }

}