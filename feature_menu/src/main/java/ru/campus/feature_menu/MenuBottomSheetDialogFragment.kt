package ru.campus.feature_menu

import ru.campus.core.presentation.BaseBottomSheetDialogFragment
import ru.campus.feature_menu.databinding.MenuFragmentBinding

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 15.05.2022 16:05
 */

class MenuBottomSheetDialogFragment : BaseBottomSheetDialogFragment<MenuFragmentBinding>() {

    override fun getViewBinding() = MenuFragmentBinding.inflate(layoutInflater)

}