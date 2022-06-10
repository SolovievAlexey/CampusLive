package ru.campus.feature_location.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import ru.campus.core.presentation.MyOnClick
import ru.campus.feature_location.data.LocationModel
import ru.campus.feature_location.databinding.ItemLocationBinding

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 14.05.2022 17:32
 */

internal class LocationViewHolder(
    private val itemBinding: ItemLocationBinding,
    private val myOnClick: MyOnClick<LocationModel>
) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(item: LocationModel) {
        itemBinding.name.text = item.name
        itemBinding.address.text = item.address
        itemBinding.container.setOnClickListener {
            myOnClick.item(itemBinding.container, item)
        }
    }

}