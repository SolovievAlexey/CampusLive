package ru.campus.live.discussion.presentation.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import ru.campus.live.discussion.data.model.DiscussionModel

class DiscussionDiffUtilCallBack(
    private val oldData: ArrayList<DiscussionModel>,
    private val newData: ArrayList<DiscussionModel>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldData.size
    }

    override fun getNewListSize(): Int {
        return newData.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldData[oldItemPosition].id == newData[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldVote = oldData[oldItemPosition].vote
        val newVote = newData[newItemPosition].vote
        val oldRating = oldData[oldItemPosition].rating
        val newRating = newData[newItemPosition].rating
        val oldRelativeTime = oldData[oldItemPosition].relativeTime
        val newRelativeTime = newData[newItemPosition].relativeTime
        return oldVote == newVote && oldRating == newRating &&
                oldRelativeTime == newRelativeTime
    }

}