package ru.campus.live.gallery.presentation.adapter.holder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.campus.live.gallery.data.model.GalleryDataObject
import ru.campus.live.databinding.ItemGalleryBinding
import ru.campus.live.core.presentation.MyOnClick

class GalleryViewHolder(
    private val itemBinding: ItemGalleryBinding,
    private val myOnClick: MyOnClick<GalleryDataObject>
) :
    RecyclerView.ViewHolder(itemBinding.root) {

    private val context = itemBinding.root.context

    fun bind(dataObject: GalleryDataObject) {
        Glide.with(context).load(dataObject.fullPath).into(itemBinding.image)
        itemBinding.container.setOnClickListener {
            myOnClick.item(itemBinding.container, dataObject)
        }
    }

}