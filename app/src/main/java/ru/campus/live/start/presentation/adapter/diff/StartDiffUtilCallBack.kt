package ru.campus.live.start.presentation.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import ru.campus.live.start.data.model.StartModel

class StartDiffUtilCallBack(
    private val oldData: ArrayList<StartModel>,
    private val newData: ArrayList<StartModel>
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
        return oldData[oldItemPosition].id == newData[newItemPosition].id
    }

}