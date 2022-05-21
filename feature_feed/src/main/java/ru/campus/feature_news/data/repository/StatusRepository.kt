package ru.campus.feature_news.data.repository

import ru.campus.feature_news.data.model.StatusModel

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 21.05.2022 1:48
 */

interface StatusRepository {
    fun read(): StatusModel
    fun refresh(): StatusModel
}