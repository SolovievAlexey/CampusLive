package ru.campus.feature_start.data.repository

import kotlinx.coroutines.withContext
import ru.campus.core.di.CoroutineDispatchers
import ru.campus.feature_start.data.model.StartModel
import javax.inject.Inject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 12.05.2022 19:46
 */

class BaseStartRepository @Inject constructor(
    private val startDataSource: StartDataSource,
    private val dispatchers: CoroutineDispatchers
) : StartRepository {

    override suspend fun start(): List<StartModel> {
        val result: List<StartModel>
        withContext(dispatchers.io) {
            result = startDataSource.execute()
        }
        return result
    }

}