package ru.campus.feature_start.data.repository

import ru.campus.feature_start.data.model.StartModel

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 12.05.2022 19:45
 */

interface StartRepository {
    suspend fun start(): List<StartModel>
}