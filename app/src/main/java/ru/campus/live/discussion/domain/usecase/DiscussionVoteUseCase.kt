package ru.campus.live.discussion.domain.usecase

import ru.campus.live.core.data.model.ItemVoteDataModel
import ru.campus.live.core.data.model.VoteModel
import ru.campus.live.core.domain.ItemVoteEditUseCase
import ru.campus.live.discussion.data.model.DiscussionObject

class DiscussionVoteUseCase {

    fun execute(
        model: ArrayList<DiscussionObject>,
        voteModel: VoteModel
    ): ArrayList<DiscussionObject> {
        val index = model.indexOfFirst { it.id == voteModel.id }
        val rating = model[index].rating

        val itemVoteDataObject = ItemVoteDataModel(rating = rating, vote = model[index].vote)
        val newItemVoteDataObject = ItemVoteEditUseCase().execute(itemVoteDataObject, voteModel)

        val item = model[index]
        val newItem = item.copy(
            type = item.type,
            id = item.id,
            hidden = item.hidden,
            author = item.author,
            icon_id = item.icon_id,
            message = item.message,
            attachment = item.attachment,
            rating = newItemVoteDataObject.rating,
            vote = newItemVoteDataObject.vote,
            parent = item.parent,
            answered = item.answered,
            relativeTime = item.relativeTime
        )

        model.removeAt(index)
        model.add(index, newItem)
        return model
    }

}