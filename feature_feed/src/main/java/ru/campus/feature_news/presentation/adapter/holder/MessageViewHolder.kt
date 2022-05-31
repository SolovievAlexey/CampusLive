package ru.campus.feature_news.presentation.adapter.holder

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.core.graphics.toColorInt
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.campus.core.data.DomainDataStore
import ru.campus.core.presentation.MyOnClick
import ru.campus.feature_news.R
import ru.campus.feature_news.data.model.FeedModel
import ru.campus.feature_news.databinding.ItemMessageBinding

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 15.05.2022 19:01
 */

class MessageViewHolder(
    private val binding: ItemMessageBinding,
    private val myOnClick: MyOnClick<FeedModel>,
    domainDataStore: DomainDataStore
) : RecyclerView.ViewHolder(binding.root) {

    private val context = binding.root.context
    private val domain = domainDataStore.get()

    fun bind(model: FeedModel) {
        binding.message.text = model.message
        binding.date.text = model.relativeTime
        binding.comment.text = model.commentsString
        renderMediaView(model)
        renderVoteView(model)
        renderRatingView(model)
        binding.container.setOnClickListener {
            myOnClick.item(binding.container, model)
        }
    }

    private fun renderMediaView(model: FeedModel) {
        if (model.attachment != null && model.attachment.id != 0) {
            binding.media.isVisible = true
            val params: ViewGroup.LayoutParams = binding.media.layoutParams
            params.width = model.mediaWidth
            params.height = model.mediaHeight
            binding.media.layoutParams = params
            Glide.with(context).load(domain + model.attachment.path).into(binding.media)
        } else {
            binding.media.isVisible = false
        }
    }

    private fun renderVoteView(model: FeedModel) {
        when (model.vote) {
            0 -> {
                binding.likeStatus.isVisible = false
            }
            1 -> {
                binding.likeStatus.isVisible = true
                binding.likeStatus.setImageResource(R.drawable.like)
            }
            2 -> {
                binding.likeStatus.isVisible = true
                binding.likeStatus.setImageResource(R.drawable.dislike)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun renderRatingView(model: FeedModel) {
        when {
            model.rating == 0 -> {
                binding.rating.isVisible = false
            }
            model.rating > 0 -> {
                binding.rating.isVisible = true
                binding.rating.text = "+" + model.rating
                binding.rating.setTextColor("#2e703c".toColorInt())
            }
            else -> {
                binding.rating.isVisible = true
                binding.rating.text = model.rating.toString()
                binding.rating.setTextColor("#8a0c0c".toColorInt())
            }
        }
    }

}