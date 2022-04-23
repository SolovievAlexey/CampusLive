package ru.campus.live.ribbon.presentation.adapter.holders

import androidx.recyclerview.widget.RecyclerView
import ru.campus.live.databinding.ItemFeedLocationBinding
import ru.campus.live.ribbon.data.model.RibbonModel

class RibbonLocationViewHolder(
    private val itemBinding: ItemFeedLocationBinding
) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(model: RibbonModel) {
        itemBinding.name.text = model.location!!.locationName
    }

}