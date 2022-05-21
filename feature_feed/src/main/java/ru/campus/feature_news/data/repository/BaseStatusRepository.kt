package ru.campus.feature_news.data.repository

import ru.campus.feature_news.data.model.StatusModel
import javax.inject.Inject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 21.05.2022 1:50
 */

class BaseStatusRepository @Inject constructor(
    private val statusDataSource: StatusDataSource
) : StatusRepository {

    override fun read(): StatusModel {
        return statusDataSource.read()
    }

    override fun refresh(): StatusModel {
        val result = statusDataSource.refresh()
        statusDataSource.save(params = result)
        return result
    }

}