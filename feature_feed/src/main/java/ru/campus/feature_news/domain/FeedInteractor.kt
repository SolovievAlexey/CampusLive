package ru.campus.feature_news.domain

import ru.campus.core.data.ResponseObject
import ru.campus.feature_news.data.FeedModel
import ru.campus.feature_news.data.NewsRepository
import javax.inject.Inject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 15.05.2022 18:47
 */

class FeedInteractor @Inject constructor(private val repository: NewsRepository) {

    fun get(offset: Int): ResponseObject<ArrayList<FeedModel>> {
        return repository.get(offset = offset)
    }


}