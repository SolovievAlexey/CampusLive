package ru.campus.live.ribbon.presentation.adapter.holders

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.campus.live.R
import ru.campus.live.databinding.ItemFeedInviteBinding

class FeedInviteViewHolder(private val itemBinding: ItemFeedInviteBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    private val context = itemBinding.root.context

    fun bind() {
        Glide.with(context).load(R.drawable.talk).into(itemBinding.icon)
    }

}