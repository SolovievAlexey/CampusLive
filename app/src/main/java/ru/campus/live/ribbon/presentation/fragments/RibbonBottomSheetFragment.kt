package ru.campus.live.ribbon.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.graphics.toColorInt
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.navigation.navGraphViewModels
import ru.campus.live.R
import ru.campus.live.core.data.model.VoteModel
import ru.campus.live.core.di.component.DaggerRibbonComponent
import ru.campus.live.core.di.component.RibbonComponent
import ru.campus.live.core.di.deps.AppDepsProvider
import ru.campus.live.core.presentation.BaseBottomSheetDialogFragment
import ru.campus.live.databinding.FragmentFeedBottomSheetBinding
import ru.campus.live.ribbon.data.model.RibbonModel
import ru.campus.live.ribbon.presentation.viewmodel.RibbonViewModel

class RibbonBottomSheetFragment : BaseBottomSheetDialogFragment<FragmentFeedBottomSheetBinding>(),
    View.OnClickListener {

    private var model: RibbonModel? = null
    private val component: RibbonComponent by lazy {
        DaggerRibbonComponent.builder()
            .deps(AppDepsProvider.deps)
            .build()
    }

    private val viewModel: RibbonViewModel by navGraphViewModels(R.id.feedFragment) {
        component.viewModelsFactory()
    }

    override fun getViewBinding() = FragmentFeedBottomSheetBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let { model = it?.getParcelable("publication_object") }
    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.publicationId.text = "id" + model!!.id
        binding.comments.text = model!!.commentsString
        renderVoteView()
        renderRatingView()
        binding.commentsRoot.setOnClickListener(this)
        binding.up.setOnClickListener(this)
        binding.down.setOnClickListener(this)
        binding.complaint.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.complaint -> viewModel.complaint(model!!)
            R.id.up -> {
                val params = VoteModel(model!!.id, 1)
                viewModel.vote(params)
            }
            R.id.down -> {
                val params = VoteModel(model!!.id, 2)
                viewModel.vote(params)
            }
            R.id.commentsRoot -> viewModel.startDiscussion(model!!)
        }
        dismiss()
    }

    private fun renderVoteView() {
        when (model!!.vote) {
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
            model!!.rating == 0 -> {
                binding.rating.isInvisible = true
                binding.rating.text = model!!.rating.toString()
                binding.rating.setTextColor("#eeeeee".toColorInt())
            }
            model!!.rating > 0 -> {
                binding.rating.isVisible = true
                binding.rating.text = "+" + model!!.rating
                binding.rating.setTextColor("#2e703c".toColorInt())
            }
            else -> {
                binding.rating.isVisible = true
                binding.rating.text = model!!.rating.toString()
                binding.rating.setTextColor("#8a0c0c".toColorInt())
            }
        }
    }

}