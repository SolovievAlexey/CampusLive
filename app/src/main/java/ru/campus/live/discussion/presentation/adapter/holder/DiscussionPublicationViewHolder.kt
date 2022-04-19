package ru.campus.live.discussion.presentation.adapter.holder

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.core.graphics.toColorInt
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.campus.live.R
import ru.campus.live.core.data.source.HostDataSource
import ru.campus.live.databinding.ItemPublicationBinding
import ru.campus.live.discussion.data.model.DiscussionObject

class DiscussionPublicationViewHolder(private val itemBinding: ItemPublicationBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    private val context = itemBinding.root.context
    private val host = HostDataSource(context).domain()

    fun bind(model: DiscussionObject) {
        itemBinding.message.text = model.message
        itemBinding.date.text = model.relativeTime
        itemBinding.comment.isInvisible = true
        renderMediaView(model)
        renderVoteView(model)
        renderRatingView(model)
    }

    private fun renderMediaView(model: DiscussionObject) {
        if (model.attachment != null) {
            itemBinding.media.isVisible = true
            val params: ViewGroup.LayoutParams = itemBinding.media.layoutParams
            params.width = model.mediaWidth
            params.height = model.mediaHeight
            itemBinding.media.layoutParams = params
            Glide.with(context).load(host + model.attachment.path).into(itemBinding.media)
        } else {
            itemBinding.media.isVisible = false
        }
    }

    private fun renderVoteView(model: DiscussionObject) {
        when (model.vote) {
            0 -> {
                itemBinding.likeStatus.isVisible = false
            }
            1 -> {
                itemBinding.likeStatus.isVisible = true
                itemBinding.likeStatus.setImageResource(R.drawable.like)
            }
            2 -> {
                itemBinding.likeStatus.isVisible = true
                itemBinding.likeStatus.setImageResource(R.drawable.dislike)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun renderRatingView(model: DiscussionObject) {
        when {
            model.rating == 0 -> {
                itemBinding.rating.isVisible = false
            }
            model.rating > 0 -> {
                itemBinding.rating.isVisible = true
                itemBinding.rating.text = "+" + model.rating
                itemBinding.rating.setTextColor("#2e703c".toColorInt())
            }
            else -> {
                itemBinding.rating.isVisible = true
                itemBinding.rating.text = model.rating.toString()
                itemBinding.rating.setTextColor("#8a0c0c".toColorInt())
            }
        }
    }


}