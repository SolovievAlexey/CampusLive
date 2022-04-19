package ru.campus.live.core.domain

import ru.campus.live.core.data.model.ItemVoteDataModel
import ru.campus.live.core.data.model.VoteModel

private const val NOT_VOTE = 0
private const val LIKE = 1
private const val DISLIKE = 2

class ItemVoteEditUseCase {

    fun execute(item: ItemVoteDataModel, voteModel: VoteModel): ItemVoteDataModel {
        val rating = item.rating
        when(item.vote) {
            0 -> {
                if (voteModel.vote == LIKE) {
                    item.rating = rating + 1
                    item.vote = LIKE
                } else {
                    item.rating = rating - 1
                    item.vote = DISLIKE
                }
            }
            1 -> {
                if (voteModel.vote == LIKE) {
                    item.rating = rating - 1
                    item.vote = NOT_VOTE
                } else {
                    item.rating = rating - 2
                    item.vote = DISLIKE
                }
            }
            2 -> {
                if (voteModel.vote == DISLIKE) {
                    item.rating = rating + 1
                    item.vote = NOT_VOTE
                } else {
                    item.rating = rating + 2
                    item.vote = LIKE
                }
            }
        }
        return item
    }

}