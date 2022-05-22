package ru.campus.feature_discussion.domain

import ru.campus.feature_discussion.data.model.DiscussionModel

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 22.05.2022 19:07
 */

class DiscussionVoteUseCase {

    fun execute(
        model: ArrayList<DiscussionModel>,
        item: DiscussionModel,
        vote: Int,
    ): ArrayList<DiscussionModel> {
        val index = model.indexOf(item)
        val newItem = VoteEditUseCase().execute(item = item, vote = vote)
        model.removeAt(index)
        model.add(index, newItem)
        return model
    }

}