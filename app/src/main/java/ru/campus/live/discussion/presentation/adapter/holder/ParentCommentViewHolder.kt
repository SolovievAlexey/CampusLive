package ru.campus.live.discussion.presentation.adapter.holder

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.core.graphics.toColorInt
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.campus.live.R
import ru.campus.live.core.data.source.HostDataSource
import ru.campus.live.core.presentation.ui.MyOnClick
import ru.campus.live.databinding.ItemParentCommetBinding
import ru.campus.live.discussion.data.model.DiscussionObject

class ParentCommentViewHolder(
    private val itemBinding: ItemParentCommetBinding,
    private val myOnClick: MyOnClick<DiscussionObject>
) : RecyclerView.ViewHolder(itemBinding.root) {

    private val context = itemBinding.root.context
    private val host = HostDataSource(context).domain()

    fun bind(model: DiscussionObject) {
        val color = if (model.hidden == 0) "#000000".toColorInt() else "#999999".toColorInt()
        itemBinding.message.setTextColor(color)
        itemBinding.message.text = model.message
        itemBinding.date.text = model.relativeTime
        Glide.with(context).load(model.userAvatar).into(itemBinding.userPhoto)

        renderMediaView(model)
        renderVoteView(model)
        renderRatingView(model)
        itemBinding.container.setOnClickListener {
            myOnClick.item(itemBinding.container, model)
        }
    }

    private fun renderMediaView(model: DiscussionObject) {
        if (model.attachment == null) {
            itemBinding.photo.isVisible = false
        } else {
            itemBinding.photo.isVisible = true
            val params: ViewGroup.LayoutParams = itemBinding.photo.layoutParams
            params.width = model.mediaWidth
            params.height = model.mediaHeight
            itemBinding.photo.layoutParams = params
            Glide.with(context)
                .load(host + model.attachment.path)
                .into(itemBinding.photo)
        }
    }

    private fun renderVoteView(model: DiscussionObject) {
        itemBinding.likeStatus.isVisible = model.vote != 0
        when (model.vote) {
            1 -> itemBinding.likeStatus.setImageResource(R.drawable.like)
            2 -> itemBinding.likeStatus.setImageResource(R.drawable.dislike)
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