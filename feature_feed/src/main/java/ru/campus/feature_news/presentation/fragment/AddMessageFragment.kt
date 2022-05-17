package ru.campus.feature_news.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.campus.core.data.AlertMessageModel
import ru.campus.core.data.GalleryDataModel
import ru.campus.core.data.UploadMediaModel
import ru.campus.core.di.AppDepsProvider
import ru.campus.core.presentation.BaseFragment
import ru.campus.core.presentation.Keyboard
import ru.campus.core.presentation.MyOnClick
import ru.campus.feature_news.R
import ru.campus.feature_news.data.FeedModel
import ru.campus.feature_news.databinding.FragmentAddMessageBinding
import ru.campus.feature_news.di.DaggerFeedComponent
import ru.campus.feature_news.presentation.viewmodel.CreateMessageViewModel
import ru.campus.file_upload.presentation.UploadMediaAdapter


class AddMessageFragment : BaseFragment<FragmentAddMessageBinding>() {

    private val component by lazy {
        DaggerFeedComponent.builder()
            .deps(AppDepsProvider.deps)
            .build()
    }

    private val viewModel by viewModels<CreateMessageViewModel> {
        component.viewModelsFactory()
    }

    private val uploadMediaAdapterCallBack = object : MyOnClick<UploadMediaModel> {
        override fun item(view: View, item: UploadMediaModel) {

        }
    }

    private val uploadMediaAdapter = UploadMediaAdapter(uploadMediaAdapterCallBack)
    override fun getViewBinding() = FragmentAddMessageBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().supportFragmentManager
            .setFragmentResultListener("mediaRequest", viewLifecycleOwner) { _, bundle ->
                val params: GalleryDataModel? = bundle.getParcelable("item")
                if (params != null) viewModel.upload(params)
            }

        initToolBar()
        viewModel.success.observe(viewLifecycleOwner, success())
        viewModel.failure.observe(viewLifecycleOwner, failure())
        viewModel.mediaList.observe(viewLifecycleOwner, mediaList())

        binding.gallery.setOnClickListener {
            val request = NavDeepLinkRequest.Builder
                .fromUri("android-app://ru.campus.live/galleryFragment".toUri())
                .build()
            findNavController().navigate(request)
        }

        binding.recyclerViewUploadMedia.adapter = uploadMediaAdapter
        binding.recyclerViewUploadMedia.layoutManager = LinearLayoutManager(requireContext(),
            RecyclerView.HORIZONTAL, false)
    }

    private fun initToolBar() {
        binding.toolBar.title = getString(R.string.new_publication)
        binding.toolBar.inflateMenu(R.menu.send_menu)
        binding.toolBar.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.send) sendMessageOnServer()
            return@setOnMenuItemClickListener false
        }

        binding.toolBar.setNavigationIcon(R.drawable.arrow_back)
        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun sendMessageOnServer() {
        val message = binding.editText.text.toString()
        if (message.isEmpty()) return
        isVisibleProgressBar(visible = true)
        viewModel.post(message = message)
    }

    private fun success() = Observer<FeedModel> {
        Keyboard().hide(requireActivity())
        findNavController().popBackStack()
    }

    private fun failure() = Observer<AlertMessageModel> {
        isVisibleProgressBar(visible = false)
        Log.d("MyLog", "Произошла ошибка! Повторите попытку ещё раз")
    }

    private fun mediaList() = Observer<ArrayList<UploadMediaModel>> { model ->
        if(model[0].upload)
            binding.toolBar.menu.clear()
        else
            binding.toolBar.inflateMenu(R.menu.send_menu)
        uploadMediaAdapter.setData(model)
    }

    private fun isVisibleProgressBar(visible: Boolean) {
        binding.progressBar.isVisible = visible
        if(visible)
            binding.toolBar.menu.clear()
        else
            binding.toolBar.inflateMenu(R.menu.send_menu)
    }

}