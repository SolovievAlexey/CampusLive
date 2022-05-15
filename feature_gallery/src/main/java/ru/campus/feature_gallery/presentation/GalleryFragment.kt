package ru.campus.feature_gallery.presentation

import ru.campus.core.di.AppDepsProvider
import ru.campus.core.presentation.BaseBottomSheetDialogFragment
import ru.campus.feature_gallery.databinding.FragmentGalleryBinding

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 15.05.2022 23:12
 */

class GalleryFragment: BaseBottomSheetDialogFragment<FragmentGalleryBinding>() {

    /*
    private val component: GalleryComponent by lazy {
        DaggerGalleryComponent.builder()
            .deps(AppDepsProvider.deps)
            .build()
    }

     */

    override fun getViewBinding() = FragmentGalleryBinding.inflate(layoutInflater)

}