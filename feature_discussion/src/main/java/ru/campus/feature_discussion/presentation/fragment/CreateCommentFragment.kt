package ru.campus.feature_discussion.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import ru.campus.core.di.AppDepsProvider
import ru.campus.core.presentation.BaseFragment
import ru.campus.core.presentation.Keyboard
import ru.campus.feature_discussion.data.model.DiscussionModel
import ru.campus.feature_discussion.data.model.DiscussionPostModel
import ru.campus.feature_discussion.di.DaggerDiscussionComponent
import ru.campus.feature_discussion.di.DiscussionComponent
import ru.campus.feature_discussion.presentation.viewmodel.CreateCommentViewModel
import ru.campus.feaure_discussion.R
import ru.campus.feaure_discussion.databinding.FragmentCreateCommentBinding


class CreateCommentFragment : BaseFragment<FragmentCreateCommentBinding>() {

    private val publication by lazy { arguments?.getInt("publication") ?: 0 }
    private val parent by lazy { arguments?.getInt("parent") ?: 0 }
    private val answered by lazy { arguments?.getInt("answered") ?: 0 }

    private val component: DiscussionComponent by lazy {
        DaggerDiscussionComponent.builder()
            .deps(AppDepsProvider.deps)
            .build()
    }

    private val viewModel by viewModels<CreateCommentViewModel> {
        component.viewModelsFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MyLog", "id publication = $publication")
    }

    override fun getViewBinding() = FragmentCreateCommentBinding.inflate(layoutInflater)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        viewModel.successLiveData.observe(viewLifecycleOwner, successLiveData())
        viewModel.failureLiveData.observe(viewLifecycleOwner, failureLiveData())
        binding.editText.doAfterTextChanged {
            val count = 200 - binding.editText.text.toString().length
            binding.textCount.text = count.toString()
        }

        binding.gallery.setOnClickListener {
            val request = NavDeepLinkRequest.Builder
                .fromUri("android-app://ru.campus.live/galleryFragment".toUri())
                .build()
            findNavController().navigate(request)
        }
    }

    private fun initToolbar() {
        binding.toolBar.title = getString(R.string.new_comment)
        binding.toolBar.inflateMenu(R.menu.send)
        binding.toolBar.setNavigationIcon(R.drawable.ic_action_close)
        binding.toolBar.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.toolBar.setOnMenuItemClickListener { menuItem ->
            if(menuItem.itemId == R.id.send) sentCommentDataOnServer()
            return@setOnMenuItemClickListener false
        }
    }

    private fun sentCommentDataOnServer() {
        Keyboard().hide(activity = requireActivity())
        val message = binding.editText.text.toString()
        if(message.isNotEmpty()) {
            isProgressBarVisible(visible = true)
            val params = DiscussionPostModel(message = message, parent = parent,
                answered = answered, publication = publication)
            viewModel.post(params)
        }
    }

    private fun successLiveData() = Observer<DiscussionModel> { item ->
        val bundle = Bundle()
        bundle.putParcelable("item", item)
        requireActivity().supportFragmentManager.setFragmentResult("new_comment", bundle)
        findNavController().popBackStack()
    }

    private fun failureLiveData() = Observer<String> { error ->
        isProgressBarVisible(visible = false)
    }

    private fun isProgressBarVisible(visible: Boolean) {
        binding.progressBar.isVisible = visible
        if(isVisible)
            binding.toolBar.menu.clear()
        else
            binding.toolBar.inflateMenu(R.menu.send)
    }

}