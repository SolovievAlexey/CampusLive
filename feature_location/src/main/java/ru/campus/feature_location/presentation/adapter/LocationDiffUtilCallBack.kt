package ru.campus.feature_location.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.campus.feature_location.data.model.LocationModel

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 14.05.2022 17:45
 */

internal class LocationDiffUtilCallBack(
    private val oldData: ArrayList<LocationModel>,
    private val newData: ArrayList<LocationModel>
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
