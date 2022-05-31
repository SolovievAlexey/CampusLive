package ru.campus.feature_discussion.presentation.adapter.holder

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.core.graphics.toColorInt
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.campus.core.data.BaseDomainDataStore
import ru.campus.feature_discussion.data.model.DiscussionModel
import ru.campus.feaure_discussion.R
import ru.campus.feaure_discussion.databinding.ItemPublicationBinding

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 22.05.2022 17:30
 */

class PublicationViewHolder(
    private val binding: ItemPublicationBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val context = binding.root.context
    private val domain = BaseDomainDataStore(context).get()

    fun bind(model: DiscussionModel) {
        binding.message.text = model.message
        binding.date.text = model.relativeTime
        binding.comment.isInvisible = true
        renderMediaView(model)
        renderVoteView(model)
        renderRatingView(model)
    }

    private fun renderMediaView(model: DiscussionModel) {
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

    private fun renderVoteView(model: DiscussionModel) {
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
    private fun renderRatingView(model: DiscussionModel) {
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