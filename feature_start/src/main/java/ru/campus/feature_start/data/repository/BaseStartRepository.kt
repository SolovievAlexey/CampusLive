package ru.campus.feature_start.data.repository

import ru.campus.feature_start.data.model.StartModel
import javax.inject.Inject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 12.05.2022 19:46
 */

class BaseStartRepository @Inject constructor(
    private val startDataSource: StartDataSource
) : StartRepository {

    override fun start(): List<StartModel> {
        return startDataSource.execute()
    }

}