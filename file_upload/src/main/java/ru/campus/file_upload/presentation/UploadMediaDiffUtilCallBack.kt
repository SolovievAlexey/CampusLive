package ru.campus.file_upload.presentation

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import ru.campus.core.data.UploadMediaModel

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 17.05.2022 23:29
 */

class UploadMediaDiffUtilCallBack(
    private val oldData: ArrayList<UploadMediaModel>,
    private val newData: ArrayList<UploadMediaModel>
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
                && oldData[oldItemPosition].upload == newData[newItemPosition].upload
                && oldData[oldItemPosition].animation == newData[newItemPosition].animation
                && oldData[oldItemPosition].error == newData[newItemPosition].error
    }

}