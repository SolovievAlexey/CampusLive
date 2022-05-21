package ru.campus.feature_discussion.presentation.adapter.holder

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.toColorInt
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.campus.core.data.BaseDomainDataStore
import ru.campus.core.presentation.MyOnClick
import ru.campus.feature_discussion.data.model.DiscussionModel
import ru.campus.feaure_discussion.R
import ru.campus.feaure_discussion.databinding.ItemChildBinding

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 21.05.2022 17:01
 */

class ChildViewHolder(
    private val itemBinding: ItemChildBinding,
    private val myOnClick: MyOnClick<DiscussionModel>
) : RecyclerView.ViewHolder(itemBinding.root) {

    private val context = itemBinding.root.context
    private val domain = BaseDomainDataStore(context).get()

    fun bind(model: DiscussionModel) {
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

    private fun renderMediaView(model: DiscussionModel) {
        if (model.attachment == null) {
            itemBinding.photo.isVisible = false
        } else {
            itemBinding.photo.isVisible = true
            val params: ViewGroup.LayoutParams = itemBinding.photo.layoutParams
            params.width = model.mediaWidth
            params.height = model.mediaHeight
            itemBinding.photo.layoutParams = params
            Glide.with(context)
                .load(domain + model.attachment.path)
                .into(itemBinding.photo)
        }
    }

    private fun renderVoteView(model: DiscussionModel) {
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
    private fun renderRatingView(model: DiscussionModel) {
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