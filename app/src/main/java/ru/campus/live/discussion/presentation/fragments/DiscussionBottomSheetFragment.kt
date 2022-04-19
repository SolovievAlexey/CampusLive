package ru.campus.live.discussion.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.graphics.toColorInt
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.navigation.navGraphViewModels
import ru.campus.live.R
import ru.campus.live.core.data.model.VoteModel
import ru.campus.live.core.di.component.DaggerDiscussionComponent
import ru.campus.live.core.di.component.DiscussionComponent
import ru.campus.live.core.di.deps.AppDepsProvider
import ru.campus.live.core.presentation.ui.BaseBottomSheetDialogFragment
import ru.campus.live.databinding.FragmentDiscussionBottomSheetBinding
import ru.campus.live.discussion.data.model.DiscussionObject
import ru.campus.live.discussion.presentation.viewmodel.DiscussionViewModel

class DiscussionBottomSheetFragment :
    BaseBottomSheetDialogFragment<FragmentDiscussionBottomSheetBinding>(), View.OnClickListener {

    private var item: DiscussionObject? = null
    private val component: DiscussionComponent by lazy {
        DaggerDiscussionComponent.builder()
            .deps(AppDepsProvider.deps)
            .build()
    }

    override fun getViewBinding() = FragmentDiscussionBottomSheetBinding.inflate(layoutInflater)
    private val viewModel: DiscussionViewModel by navGraphViewModels(R.id.discussionFragment) {
        component.viewModelsFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { item = it.getParcelable("item") }
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.commentId.text = "id" + item!!.id
        renderVoteView()
        renderRatingView()
        binding.complaint.setOnClickListener(this)
        binding.up.setOnClickListener(this)
        binding.down.setOnClickListener(this)
        binding.reply.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.complaint -> viewModel.
            complaint(item!!)
            R.id.reply -> {
                val bundle = Bundle()
                bundle.putParcelable("object", item)
                setFragmentResult("reply", bundle)
            }
            R.id.up -> {
                val voteObject = VoteModel(id = item!!.id, 1)
                viewModel.vote(params = voteObject)
            }
            R.id.down -> {
                val voteObject = VoteModel(id = item!!.id, 2)
                viewModel.vote(params = voteObject)
            }
        }
        dismiss()
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