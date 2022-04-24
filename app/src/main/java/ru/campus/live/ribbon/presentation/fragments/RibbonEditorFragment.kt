package ru.campus.live.ribbon.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.campus.live.R
import ru.campus.live.core.data.model.ErrorModel
import ru.campus.live.core.di.component.DaggerRibbonComponent
import ru.campus.live.core.di.component.RibbonComponent
import ru.campus.live.core.di.deps.AppDepsProvider
import ru.campus.live.core.presentation.BaseFragment
import ru.campus.live.core.presentation.Keyboard
import ru.campus.live.core.presentation.MyOnClick
import ru.campus.live.databinding.FragmentCreatePublicationBinding
import ru.campus.live.dialog.CustomDialog
import ru.campus.live.gallery.data.model.GalleryDataModel
import ru.campus.live.gallery.data.model.UploadMediaObject
import ru.campus.live.gallery.presentation.GalleryBottomSheetDialog
import ru.campus.live.gallery.presentation.adapter.UploadMediaAdapter
import ru.campus.live.ribbon.data.model.RibbonModel
import ru.campus.live.ribbon.data.model.RibbonPostModel
import ru.campus.live.ribbon.presentation.viewmodel.RibbonEditorViewModel


class RibbonEditorFragment : BaseFragment<FragmentCreatePublicationBinding>() {

    private val component: RibbonComponent by lazy {
        DaggerRibbonComponent.builder()
            .deps(AppDepsProvider.deps)
            .build()
    }

    private val viewModel: RibbonEditorViewModel by viewModels {
        component.viewModelsFactory()
    }

    private val deleteCallBack = object : MyOnClick<UploadMediaObject> {
        override fun item(view: View, item: UploadMediaObject) {
            viewModel.clearMediaList()
        }
    }

    private val uploadMediaAdapter = UploadMediaAdapter(deleteCallBack)
    override fun getViewBinding() = FragmentCreatePublicationBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentFragment?.setFragmentResultListener("mediaRequest") { _, bundle ->
            val params: GalleryDataModel? = bundle.getParcelable("item")
            if (params != null) viewModel.upload(params)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        binding.recyclerViewUploadMedia.adapter = uploadMediaAdapter
        binding.recyclerViewUploadMedia.layoutManager = LinearLayoutManager(requireContext())
        viewModel.success.observe(viewLifecycleOwner, success())
        viewModel.failure.observe(viewLifecycleOwner, failure())
        viewModel.upload.observe(viewLifecycleOwner, upload())
        binding.gallery.setOnClickListener {
            val galleryView = GalleryBottomSheetDialog()
            galleryView.show(requireActivity().supportFragmentManager, "GalleryView")
        }
        binding.editText.doAfterTextChanged {
            val count = 300 - binding.editText.text.toString().length
            binding.textCount.text = count.toString()
        }
    }

    private fun initToolbar() {
        binding.toolBar.setTitle(R.string.new_publication)
        binding.toolBar.setNavigationIcon(R.drawable.outline_clear_black_24)
        isVisibleToolBarMenu(true)
        binding.toolBar.setNavigationOnClickListener {
            Keyboard().hide(activity)
            findNavController().popBackStack()
        }
        binding.toolBar.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.send) {
                Keyboard().hide(activity)
                isVisibleToolBarMenu(false)
                binding.progressBar.isVisible = true
                val message = binding.editText.text.toString()
                if (message.isEmpty()) return@setOnMenuItemClickListener false
                val params = RibbonPostModel(message = message, attachment = 0)
                viewModel.post(params)
            }
            return@setOnMenuItemClickListener false
        }
    }

    private fun upload() = Observer<ArrayList<UploadMediaObject>> { newModel ->
        uploadMediaAdapter.setData(newModel)
    }

    private fun success() = Observer<RibbonModel> { model ->
        val bundle = Bundle()
        bundle.putParcelable("publication", model)
        parentFragment?.setFragmentResult("new_publication", bundle)
        findNavController().popBackStack()
    }

    private fun failure() = Observer<ErrorModel> { error ->
        isVisibleToolBarMenu(false)
        CustomDialog().apply {
            Bundle(1).apply {
                putParcelable("params", error)
            }
        }.show(requireActivity().supportFragmentManager, "CustomErrorDialog")
    }

    private fun isVisibleToolBarMenu(visible: Boolean) {
        binding.toolBar.apply {
            if (visible) inflateMenu(R.menu.send_menu) else menu.clear()
        }
    }

}