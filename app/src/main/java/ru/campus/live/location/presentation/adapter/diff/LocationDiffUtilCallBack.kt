package ru.campus.live.location.presentation.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import ru.campus.live.location.data.model.LocationModel

class LocationDiffUtilCallBack(
    private val oldData: ArrayList<LocationModel>,
    private val newData: ArrayList<LocationModel>,
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