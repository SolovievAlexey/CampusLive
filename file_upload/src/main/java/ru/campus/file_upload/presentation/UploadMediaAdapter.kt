package ru.campus.file_upload.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.campus.core.data.UploadMediaModel
import ru.campus.core.presentation.MyOnClick
import ru.campus.file_upload.databinding.ItemUploadBinding

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 17.05.2022 23:24
 */

class UploadMediaAdapter(
    private val myOnClick: MyOnClick<UploadMediaModel>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val model = ArrayList<UploadMediaModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding =
            ItemUploadBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UploadMediaViewHolder(itemBinding, myOnClick)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as UploadMediaViewHolder).bind(model[position])
    }

    override fun getItemCount(): Int {
        return model.size
    }

    fun setData(newModel: ArrayList<UploadMediaModel>) {
        val result =
            DiffUtil.calculateDiff(UploadMediaDiffUtilCallBack(model, newModel))
        model.clear()
        model.addAll(newModel)
        result.dispatchUpdatesTo(this)
    }

}