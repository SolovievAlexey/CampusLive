package ru.campus.live.ribbon.presentation.adapter.holders

import androidx.recyclerview.widget.RecyclerView
import ru.campus.live.databinding.ItemRibbonErrorBinding
import ru.campus.live.ribbon.data.model.RibbonModel

class RibbonErrorViewHolder(private val itemBinding: ItemRibbonErrorBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(model: RibbonModel) {
        itemBinding.message.text = model.message
    }

}