package ru.campus.feature_gallery.presentation

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
import ru.campus.core.di.AppDepsProvider
import ru.campus.core.presentation.BaseBottomSheetDialogFragment
import ru.campus.core.presentation.MyOnClick
import ru.campus.feature_gallery.R
import ru.campus.core.data.GalleryDataModel
import ru.campus.feature_gallery.databinding.FragmentGalleryBinding
import ru.campus.feature_gallery.di.DaggerGalleryComponent
import ru.campus.feature_gallery.di.GalleryComponent
import ru.campus.feature_gallery.presentation.adapter.GalleryAdapter

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 15.05.2022 23:12
 */

class GalleryFragment: BaseBottomSheetDialogFragment<FragmentGalleryBinding>() {

    private val component: GalleryComponent by lazy {
        DaggerGalleryComponent.builder()
            .deps(AppDepsProvider.deps)
            .build()
    }

    private val viewModel by viewModels<GalleryViewModel> { component.viewModelsFactory() }
    private val myOnClick = object : MyOnClick<GalleryDataModel> {
        override fun item(view: View, item: GalleryDataModel) {
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

    override fun getViewBinding() = FragmentGalleryBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        permission()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.spacing)
        binding.recyclerView.addItemDecoration(ItemOffsetDecorationGallery(spacingInPixels))
        viewModel.list.observe(viewLifecycleOwner, listLiveData())
    }

    private fun listLiveData() = Observer<ArrayList<GalleryDataModel>> { newModel ->
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