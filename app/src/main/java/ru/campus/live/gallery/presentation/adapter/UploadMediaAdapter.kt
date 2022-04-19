package ru.campus.live.gallery.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.campus.live.core.presentation.MyOnClick
import ru.campus.live.databinding.ItemAttachmentBinding
import ru.campus.live.gallery.presentation.adapter.holder.UploadViewHolder
import ru.campus.live.gallery.data.model.UploadMediaObject

class UploadMediaAdapter(
    private val myOnClick: MyOnClick<UploadMediaObject>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val model = ArrayList<UploadMediaObject>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding =
            ItemAttachmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UploadViewHolder(itemBinding, myOnClick)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as UploadViewHolder).bind(model[position])
    }

    override fun getItemCount(): Int {
        return model.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newModelUpload: ArrayList<UploadMediaObject>) {
        model.clear()
        model.addAll(newModelUpload)
        notifyDataSetChanged()
    }

}