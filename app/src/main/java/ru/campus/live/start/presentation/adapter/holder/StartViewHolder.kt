package ru.campus.live.start.presentation.adapter.holder

import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.campus.live.databinding.ItemPresentationBinding
import ru.campus.live.start.data.model.StartModel

class StartViewHolder(
    @NonNull private val itemBinding: ItemPresentationBinding
) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(item: StartModel) {
        itemBinding.title.text = item.title
        itemBinding.message.text = item.message
        val context = itemBinding.root.context
        Glide.with(context).load(item.icon).into(itemBinding.icon)
    }

}