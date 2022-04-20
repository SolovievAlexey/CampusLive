package ru.campus.live.gallery.presentation.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import ru.campus.live.gallery.data.model.GalleryDataModel

class GalleryDiffUtilCallBack(
    private val oldData: ArrayList<GalleryDataModel>,
    private val newData: ArrayList<GalleryDataModel>,
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