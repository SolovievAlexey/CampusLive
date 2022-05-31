package ru.campus.feature_news.presentation.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import ru.campus.feature_news.data.model.FeedModel

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 15.05.2022 19:26
 */

class FeedDiffUtilCallBack(
    private val oldData: ArrayList<FeedModel>,
    private val newData: ArrayList<FeedModel>
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
        val oldComment = oldData[oldItemPosition].comments
        val newComment = newData[newItemPosition].comments
        return oldVote == newVote && oldRating == newRating &&
                oldRelativeTime == newRelativeTime && oldComment == newComment
    }

}