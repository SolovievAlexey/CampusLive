package ru.campus.live.ribbon.domain.usecase

import ru.campus.live.core.data.model.ItemVoteDataModel
import ru.campus.live.core.data.model.VoteModel
import ru.campus.live.core.domain.ItemVoteEditUseCase
import ru.campus.live.ribbon.data.model.RibbonModel

class RibbonVoteUseCase {

    fun execute(model: ArrayList<RibbonModel>, voteModel: VoteModel): ArrayList<RibbonModel> {
        val index = model.indexOfFirst { it.id == voteModel.id }
        val rating = model[index].rating

        val itemVoteDataObject = ItemVoteDataModel(rating = rating, vote = model[index].vote)
        val newItemVoteDataObject = ItemVoteEditUseCase().execute(itemVoteDataObject, voteModel)

        val item = model[index]
        val newItem = item.copy(
            viewType = item.viewType,
            location = item.location,
            id = item.id,
            message = item.message,
            attachment = item.attachment,
            rating = newItemVoteDataObject.rating,
            comments = item.comments,
            vote = newItemVoteDataObject.vote,
            relativeTime = item.relativeTime
        )

        model.removeAt(index)
        model.add(index, newItem)
        return model
    }

}