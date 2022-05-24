package ru.campus.feature_discussion.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.campus.core.data.GalleryDataModel
import ru.campus.core.data.UploadMediaModel
import ru.campus.core.di.AppDepsProvider
import ru.campus.core.presentation.BaseFragment
import ru.campus.core.presentation.Keyboard
import ru.campus.core.presentation.MyOnClick
import ru.campus.feature_discussion.data.model.DiscussionModel
import ru.campus.feature_discussion.data.model.DiscussionPostModel
import ru.campus.feature_discussion.di.DaggerDiscussionComponent
import ru.campus.feature_discussion.di.DiscussionComponent
import ru.campus.feature_discussion.presentation.viewmodel.CreateCommentViewModel
import ru.campus.feaure_discussion.R
import ru.campus.feaure_discussion.databinding.FragmentCreateCommentBinding
import ru.campus.file_upload.presentation.UploadMediaAdapter


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

    private val uploadMediaAdapterCallback = object : MyOnClick<UploadMediaModel> {
        override fun item(view: View, item: UploadMediaModel) {
            viewModel.uploadListClear()
        }
    }

    private val uploadMediaAdapter = UploadMediaAdapter(uploadMediaAdapterCallback)

    override fun getViewBinding() = FragmentCreateCommentBinding.inflate(layoutInflater)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()

        binding.recyclerViewUploadMedia.adapter = uploadMediaAdapter
        binding.recyclerViewUploadMedia.layoutManager = LinearLayoutManager(requireContext())

        viewModel.successLiveData.observe(viewLifecycleOwner, successLiveData())
        viewModel.failureLiveData.observe(viewLifecycleOwner, failureLiveData())
        viewModel.mediaListLiveData.observe(viewLifecycleOwner, mediaListLiveData())


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

        requireActivity().supportFragmentManager
            .setFragmentResultListener("mediaRequest", viewLifecycleOwner) { _, bundle ->
                val params: GalleryDataModel? = bundle.getParcelable("item")
                if (params != null) viewModel.upload(params)
            }
    }

    private fun initToolbar() {
        binding.toolBar.title =
            if (parent == 0) getString(R.string.new_comment) else getString(R.string.comment_reply)
        binding.toolBar.inflateMenu(R.menu.send)
        binding.toolBar.setNavigationIcon(R.drawable.outline_arrow_back_black_24)
        binding.toolBar.setOnClickListener {
            Keyboard(activity = requireActivity()).hide()
            findNavController().popBackStack()
        }

        binding.toolBar.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.send) sentCommentDataOnServer()
            return@setOnMenuItemClickListener false
        }
    }

    private fun sentCommentDataOnServer() {
        Keyboard(activity = requireActivity()).hide()
        val message = binding.editText.text.toString()
        if (message.isNotEmpty()) {
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

    private fun mediaListLiveData() = Observer<ArrayList<UploadMediaModel>> { model ->
        try {
            if (model[0].upload)
                binding.toolBar.menu.clear()
            else
                binding.toolBar.inflateMenu(R.menu.send)
        } catch (e: Exception) { }
        uploadMediaAdapter.setData(model)
    }

    private fun isProgressBarVisible(visible: Boolean) {
        binding.progressBar.isVisible = visible
        if (isVisible)
            binding.toolBar.menu.clear()
        else
            binding.toolBar.inflateMenu(R.menu.send)
    }

}