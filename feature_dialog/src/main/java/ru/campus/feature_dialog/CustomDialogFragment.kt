package ru.campus.feature_dialog

import android.os.Bundle
import android.view.View
import ru.campus.feature_dialog.databinding.FragmentCustomDialogBinding

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 19.05.2022 18:04
 */

class CustomDialogFragment : BaseDialogFragment<FragmentCustomDialogBinding>() {

    override fun getViewBinding() = FragmentCustomDialogBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.close.setOnClickListener { dismiss() }
    }

}