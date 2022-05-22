package ru.campus.feature_support

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.campus.feature_support.databinding.ItemRulesBinding

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 22.05.2022 21:58
 */

class RulesViewHolder(
    private val itemBinding: ItemRulesBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(message: String) {
        itemBinding.message.text = message
    }

}