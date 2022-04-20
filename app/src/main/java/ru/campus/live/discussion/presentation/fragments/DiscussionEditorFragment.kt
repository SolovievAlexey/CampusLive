package ru.campus.live.discussion.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.campus.live.R
import ru.campus.live.core.di.component.DaggerDiscussionComponent
import ru.campus.live.core.di.component.DiscussionComponent
import ru.campus.live.core.di.deps.AppDepsProvider
import ru.campus.live.core.presentation.BaseFragment
import ru.campus.live.core.presentation.Keyboard
import ru.campus.live.core.presentation.MyOnClick
import ru.campus.live.databinding.FragmentCreateCommentBinding
import ru.campus.live.dialog.CustomDialog
import ru.campus.live.discussion.data.model.CommentCreateModel
import ru.campus.live.discussion.presentation.viewmodel.DiscussionCreateViewModel
import ru.campus.live.gallery.presentation.adapter.UploadMediaAdapter
import ru.campus.live.gallery.data.model.GalleryDataModel
import ru.campus.live.gallery.data.model.UploadMediaObject
import ru.campus.live.gallery.presentation.GalleryBottomSheetDialog


class DiscussionEditorFragment : BaseFragment<FragmentCreateCommentBinding>() {

    private val component: DiscussionComponent by lazy {
        DaggerDiscussionComponent.builder()
            .deps(AppDepsProvider.deps)
            .build()
    }

    private var publication: Int = 0
    private var parent: Int = 0
    private var answered: Int = 0
    private val viewModel by viewModels<DiscussionCreateViewModel> {
        component.viewModelsFactory()
    }

    private val uploadOnClick = object : MyOnClick<UploadMediaObject> {
        override fun item(view: View, item: UploadMediaObject) {
            viewModel.clearMediaUpload()
        }
    }

    private val uploadMediaAdapter = UploadMediaAdapter(uploadOnClick)
    override fun getViewBinding() = FragmentCreateCommentBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            publication = it.getInt("publication")
            parent = it.getInt("parent")
            answered = it.getInt("answered")
        }
        parentFragment?.setFragmentResultListener("mediaRequest") { _, bundle ->
            val params: GalleryDataModel? = bundle.getParcelable("item")
            if (params != null) {
                binding.toolBar.menu.clear()
                viewModel.upload(params)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        binding.gallery.setOnClickListener {
            val galleryView = GalleryBottomSheetDialog()
            galleryView.show(requireActivity().supportFragmentManager, "GalleryView")
        }
        binding.recyclerViewUploadMedia.adapter = uploadMediaAdapter
        binding.recyclerViewUploadMedia.layoutManager = LinearLayoutManager(context)
        onUploadLiveData()
        onSuccessEvent()
        onFailureEvent()
        binding.editText.doAfterTextChanged {
            val count = 200 - it.toString().length
            binding.textCount.text = count.toString()
        }
    }

    private fun initToolbar() {
        binding.toolBar.title = if (parent == 0)
            getString(R.string.new_comment)
        else
            getString(R.string.reply)
        binding.toolBar.inflateMenu(R.menu.send_menu)
        binding.toolBar.setNavigationIcon(R.drawable.baseline_close_black_24)
        binding.toolBar.setNavigationOnClickListener {
            Keyboard().hide(activity)
            findNavController().popBackStack()
        }
        binding.toolBar.setOnMenuItemClickListener {
            Keyboard().hide(activity)
            binding.toolBar.menu.clear()
            binding.progressBar.isVisible = true
            sendDataOnServer()
            return@setOnMenuItemClickListener false
        }
    }

    private fun sendDataOnServer() {
        val message = binding.editText.text.toString()
        if (message.isEmpty()) return
        val params = CommentCreateModel(
            icon = 0, message = message, attachment = 0,
            parent = parent, answered = answered, publication = publication)
        viewModel.post(params)
    }

    private fun onUploadLiveData() {
        viewModel.onUploadLiveData().observe(viewLifecycleOwner) { model ->
            if (model.size != 0) if (!model[0].upload) binding.toolBar.inflateMenu(R.menu.send_menu)
            uploadMediaAdapter.setData(model)
        }
    }

    private fun onSuccessEvent() {
        viewModel.onSuccessEvent().observe(viewLifecycleOwner) { item ->
            val bundle = Bundle()
            bundle.putParcelable("object", item)
            parentFragment?.setFragmentResult("discussionObject", bundle)
            findNavController().popBackStack()
        }
    }

    private fun onFailureEvent() {
        viewModel.onFailureEvent().observe(viewLifecycleOwner) { errorObject ->
            binding.progressBar.isVisible = false
            binding.toolBar.inflateMenu(R.menu.send_menu)
            val customDialog = CustomDialog()
            val bundle = Bundle()
            bundle.putString("message", errorObject.message)
            bundle.putInt("icon", errorObject.icon)
            bundle.putInt("code", errorObject.code)
            customDialog.arguments = bundle
            customDialog.show(requireActivity().supportFragmentManager, "ErrorDialog")
        }
    }

}