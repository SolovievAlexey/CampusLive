package ru.campus.feature_gallery.presentation.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.campus.core.presentation.MyOnClick
import ru.campus.feature_gallery.data.GalleryDataModel
import ru.campus.feature_gallery.databinding.ItemGalleryBinding

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 15.05.2022 23:22
 */

class GalleryViewHolder(
    private val binding: ItemGalleryBinding,
    private val myOnClick: MyOnClick<GalleryDataModel>
) : RecyclerView.ViewHolder(binding.root) {

    private val context = binding.root.context

    fun bind(model: GalleryDataModel) {
        Glide.with(context).load(model.fullPath).into(binding.image)
        binding.container.setOnClickListener {
            myOnClick.item(binding.container, model)
        }
    }

}