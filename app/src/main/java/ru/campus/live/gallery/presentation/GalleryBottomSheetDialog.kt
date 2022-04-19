package ru.campus.live.gallery.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import ru.campus.live.R
import ru.campus.live.core.di.component.DaggerGalleryComponent
import ru.campus.live.core.di.component.GalleryComponent
import ru.campus.live.core.di.deps.AppDepsProvider
import ru.campus.live.core.presentation.BaseBottomSheetDialogFragment
import ru.campus.live.core.presentation.MyOnClick
import ru.campus.live.databinding.FragmentGalleryBottomSheetBinding
import ru.campus.live.gallery.presentation.adapter.GalleryAdapter
import ru.campus.live.gallery.data.model.GalleryDataObject
import ru.campus.live.gallery.presentation.views.ItemOffsetDecorationGallery

class GalleryBottomSheetDialog :
    BaseBottomSheetDialogFragment<FragmentGalleryBottomSheetBinding>() {

    private val component: GalleryComponent by lazy {
        DaggerGalleryComponent.builder()
            .deps(AppDepsProvider.deps)
            .build()
    }

    private val viewModel by viewModels<GalleryViewModel> { component.viewModelsFactory() }
    private val myOnClick = object : MyOnClick<GalleryDataObject> {
        override fun item(view: View, item: GalleryDataObject) {
            val bundle = Bundle()
            bundle.putParcelable("item", item)
            setFragmentResult("mediaRequest", bundle)
            dismiss()
        }
    }

    private val adapter = GalleryAdapter(myOnClick)
    private val permissionResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            if (result) {
                binding.permissionsRoot.isVisible = false
                binding.recyclerView.isVisible = true
                viewModel.execute()
            } else {
                binding.permissionsRoot.isVisible = false
            }
        }

    override fun getViewBinding() = FragmentGalleryBottomSheetBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        permission()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.spacing)
        binding.recyclerView.addItemDecoration(ItemOffsetDecorationGallery(spacingInPixels))
        viewModel.list.observe(viewLifecycleOwner, listLiveData)
    }

    private val listLiveData = Observer<ArrayList<GalleryDataObject>> { newModel ->
        adapter.setData(newModel)
    }

    private fun permission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                visiblePermissionView()
            } else {
                viewModel.execute()
            }
        } else {
            viewModel.execute()
        }
    }

    private fun visiblePermissionView() {
        binding.recyclerView.isVisible = false
        binding.permissionsRoot.isVisible = true
        binding.permissionsButton.setOnClickListener {
            permissionResult.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

}