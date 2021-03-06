package ru.campus.feature_discussion.presentation.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.graphics.toColorInt
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.navigation.navGraphViewModels
import ru.campus.core.di.AppDepsProvider
import ru.campus.core.presentation.BaseBottomSheetDialogFragment
import ru.campus.feature_discussion.data.model.DiscussionModel
import ru.campus.feature_discussion.data.model.VoteTypeModel
import ru.campus.feature_discussion.di.DaggerDiscussionComponent
import ru.campus.feature_discussion.di.DiscussionComponent
import ru.campus.feature_discussion.presentation.viewmodel.DiscussionViewModel
import ru.campus.feature_discussion.presentation.viewmodel.DiscussionViewModelFactory
import ru.campus.feaure_discussion.R
import ru.campus.feaure_discussion.databinding.FragmentBottomSheetBinding
import javax.inject.Inject

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 22.05.2022 18:32
 */

class DiscussionBottomSheetFragment : BaseBottomSheetDialogFragment<FragmentBottomSheetBinding>() {

    private val item by lazy { arguments?.getParcelable<DiscussionModel>("item") }

    private val component: DiscussionComponent by lazy {
        DaggerDiscussionComponent.builder()
            .deps(AppDepsProvider.deps)
            .build()
    }

    @Inject
    lateinit var factory: DiscussionViewModelFactory.Factory
    private val viewModel: DiscussionViewModel by navGraphViewModels(R.id.discussionFragment) {
        component.viewModelsFactory()
    }

    override fun getViewBinding() = FragmentBottomSheetBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.commentId.text = "id" + item!!.id
        renderVoteView()
        renderRatingView()

        binding.complaint.setOnClickListener {
            viewModel.complaint(item!!)
            dismiss()
        }

        binding.up.setOnClickListener {
            viewModel.vote(item!!, vote = VoteTypeModel.LIKE.ordinal)
            dismiss()
        }

        binding.down.setOnClickListener {
            viewModel.vote(item = item!!, vote = VoteTypeModel.DISLIKE.ordinal)
            dismiss()
        }

        binding.reply.setOnClickListener {
            viewModel.reply(item = item!!)
            dismiss()
        }
    }

    private fun renderVoteView() {
        when (item!!.vote) {
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
            item!!.rating == 0 -> {
                binding.rating.isInvisible = true
                binding.rating.text = item!!.rating.toString()
                binding.rating.setTextColor("#eeeeee".toColorInt())
            }
            item!!.rating > 0 -> {
                binding.rating.isVisible = true
                binding.rating.text = "+" + item!!.rating
                binding.rating.setTextColor("#2e703c".toColorInt())
            }
            else -> {
                binding.rating.isVisible = true
                binding.rating.text = item!!.rating.toString()
                binding.rating.setTextColor("#8a0c0c".toColorInt())
            }
        }
    }


}