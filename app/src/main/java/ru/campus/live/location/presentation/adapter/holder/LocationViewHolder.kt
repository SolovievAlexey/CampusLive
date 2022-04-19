package ru.campus.live.location.presentation.adapter.holder

import androidx.recyclerview.widget.RecyclerView
import ru.campus.live.core.presentation.ui.MyOnClick
import ru.campus.live.databinding.ItemLocationBinding
import ru.campus.live.location.data.model.LocationModel

class LocationViewHolder(
    private val itemBinding: ItemLocationBinding,
    private val myOnClick: MyOnClick<LocationModel>,
) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(item: LocationModel) {
        itemBinding.name.text = item.name
        itemBinding.address.text = item.address
        itemBinding.container.setOnClickListener {
            myOnClick.item(itemBinding.container, item)
        }
    }

}