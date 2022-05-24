package ru.campus.feature_news.presentation.fragment

import ru.campus.core.presentation.BaseBottomSheetDialogFragment
import ru.campus.feature_news.databinding.FragmentMenuBottomSheetBinding

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 15.05.2022 22:43
 */

class NewsMenuSheetFragment: BaseBottomSheetDialogFragment<FragmentMenuBottomSheetBinding>() {

    override fun getViewBinding() = FragmentMenuBottomSheetBinding.inflate(layoutInflater)
}