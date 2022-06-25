package ru.campus.feature_start.presentation.adapter.holder

import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.campus.feature_start.R
import ru.campus.feature_start.data.model.StartModel
import ru.campus.feature_start.databinding.ItemPresentationBinding

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 13.05.2022 20:01
 */

class StartViewHolder(
    private val itemBinding: ItemPresentationBinding
) : RecyclerView.ViewHolder(itemBinding.root) {

    private val context = itemBinding.root.context

    fun bind(item: StartModel) {
        itemBinding.title.text = item.title
        itemBinding.message.text = item.message
        Glide.with(context).load(item.icon).into(itemBinding.icon)

        if (layoutPosition == 0 && !item.animation) {
            item.animation = true
            val animation = AnimationUtils.loadAnimation(context, R.anim.appearance)
            itemBinding.icon.startAnimation(animation)
        }
    }

}