package ru.campus.feature_news.domain

import android.util.Log
import ru.campus.feature_news.data.model.FeedModel
import ru.campus.feature_news.data.model.VoteTypeModel

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 21.05.2022 0:13
 */

class VoteEditUseCase {

    fun execute(item: FeedModel, vote: Int): FeedModel {
        val rating = item.rating
        val newItem = item.copy()
        when(VoteTypeModel.values()[item.vote]) {
            VoteTypeModel.UNKNOWN -> {
                if(VoteTypeModel.values()[vote] == VoteTypeModel.LIKE) {
                    newItem.rating = rating + 1
                    newItem.vote = VoteTypeModel.LIKE.ordinal
                } else {
                    newItem.rating = rating - 1
                    newItem.vote = VoteTypeModel.DISLIKE.ordinal
                }
            }

            VoteTypeModel.LIKE -> {
                if(VoteTypeModel.values()[vote] == VoteTypeModel.LIKE) {
                    newItem.rating = rating - 1
                    newItem.vote = VoteTypeModel.UNKNOWN.ordinal
                } else {
                    newItem.rating = rating - 2
                    newItem.vote = VoteTypeModel.DISLIKE.ordinal
                }
            }

            VoteTypeModel.DISLIKE -> {
                if(VoteTypeModel.values()[vote] == VoteTypeModel.DISLIKE) {
                    newItem.rating = rating + 1
                    newItem.vote = VoteTypeModel.UNKNOWN.ordinal
                } else {
                    newItem.rating = rating + 2
                    newItem.vote = VoteTypeModel.LIKE.ordinal
                }
            }
        }

        return newItem
    }

}