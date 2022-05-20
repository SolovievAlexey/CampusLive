package ru.campus.feature_news.domain

import ru.campus.feature_news.data.model.FeedModel

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 21.05.2022 0:13
 */

class VoteUseCase {

    fun execute(item: FeedModel, model: ArrayList<FeedModel>, vote: Int): ArrayList<FeedModel> {
        val index = model.indexOf(item)
        val newItem = VoteEditUseCase().execute(item = item, vote = vote)
        model.removeAt(index)
        model.add(index, newItem)
        return model
    }

}