package ru.campus.live.gallery.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.campus.live.core.presentation.MyOnClick
import ru.campus.live.databinding.ItemGalleryBinding
import ru.campus.live.gallery.data.model.GalleryDataModel
import ru.campus.live.gallery.presentation.adapter.diff.GalleryDiffUtilCallBack
import ru.campus.live.gallery.presentation.adapter.holder.GalleryViewHolder

class GalleryAdapter(private val myOnClick: MyOnClick<GalleryDataModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val model = ArrayList<GalleryDataModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding =
            ItemGalleryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GalleryViewHolder(itemBinding, myOnClick)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as GalleryViewHolder).bind(model[position])
    }

    override fun getItemCount(): Int {
        return model.size
    }

    fun setData(newModel: ArrayList<GalleryDataModel>) {
        val result =
            DiffUtil.calculateDiff(GalleryDiffUtilCallBack(model, newModel))
        model.clear()
        model.addAll(newModel)
        result.dispatchUpdatesTo(this)
    }

}