package ru.campus.feature_start.presentation.adapter.holder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.campus.feature_start.data.model.StartModel
import ru.campus.feature_start.databinding.ItemPresentationBinding

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 13.05.2022 20:01
 */

class StartViewHolder(private val itemBinding: ItemPresentationBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(item: StartModel) {
        itemBinding.title.text = item.title
        itemBinding.message.text = item.message
        val context = itemBinding.root.context
        Glide.with(context).load(item.icon).into(itemBinding.icon)
    }

}