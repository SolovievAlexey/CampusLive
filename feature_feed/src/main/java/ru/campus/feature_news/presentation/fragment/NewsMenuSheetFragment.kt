package ru.campus.feature_news.presentation.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.graphics.toColorInt
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.navigation.navGraphViewModels
import ru.campus.core.di.AppDepsProvider
import ru.campus.core.presentation.BaseBottomSheetDialogFragment
import ru.campus.feature_news.R
import ru.campus.feature_news.data.model.FeedModel
import ru.campus.feature_news.data.model.VoteTypeModel
import ru.campus.feature_news.databinding.FragmentMenuBottomSheetBinding
import ru.campus.feature_news.di.DaggerFeedComponent
import ru.campus.feature_news.di.FeedComponent
import ru.campus.feature_news.presentation.viewmodel.FeedViewModel

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 15.05.2022 22:43
 */

class NewsMenuSheetFragment : BaseBottomSheetDialogFragment<FragmentMenuBottomSheetBinding>() {

    private val component: FeedComponent by lazy {
        DaggerFeedComponent.builder()
            .deps(AppDepsProvider.deps)
            .build()
    }

    private lateinit var item: FeedModel
    private val viewModel: FeedViewModel by navGraphViewModels(R.id.feedFragment) {
        component.viewModelsFactory()
    }

    override fun getViewBinding() = FragmentMenuBottomSheetBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        item = arguments?.getParcelable("item")!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.comments.text = item.commentsString
        renderVoteView()
        renderRatingView()
        binding.complaint.setOnClickListener {
            viewModel.complaint(item = item)
            dismiss()
        }

        binding.up.setOnClickListener {
            viewModel.vote(item = item, vote = VoteTypeModel.LIKE.ordinal)
            dismiss()
        }

        binding.down.setOnClickListener {
            viewModel.vote(item = item, vote = VoteTypeModel.DISLIKE.ordinal)
            dismiss()
        }

        binding.discussionRoot.setOnClickListener {
            viewModel.discussion(item = item)
            dismiss()
        }

    }

    private fun renderVoteView() {
        when (item.vote) {
            0 -> {
                binding.down.setColorFilter("#eeeeee".toColorInt())
                binding.up.setColorFilter("#eeeeee".toColorInt())
            }
            1 -> {
                binding.down.setColorFilter("#eeeeee".toColorInt())
                binding.up.setColorFilter("#2e703c".toColorInt())
            }
            else -> {
                binding.down.setColorFilter("#8a0c0c".toColorInt())
                binding.up.setColorFilter("#eeeeee".toColorInt())
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun renderRatingView() {
        when {
            item.rating == 0 -> {
                binding.rating.isInvisible = true
                binding.rating.text = item.rating.toString()
                binding.rating.setTextColor("#eeeeee".toColorInt())
            }
            item.rating > 0 -> {
                binding.rating.isVisible = true
                binding.rating.text = "+" + item.rating
                binding.rating.setTextColor("#2e703c".toColorInt())
            }
            else -> {
                binding.rating.isVisible = true
                binding.rating.text = item.rating.toString()
                binding.rating.setTextColor("#8a0c0c".toColorInt())
            }
        }
    }

}