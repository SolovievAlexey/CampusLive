package ru.campus.feature_support

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import ru.campus.core.presentation.BaseBottomSheetDialogFragment
import ru.campus.feature_support.databinding.FragmentSupportBottomSheetBinding


/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 22.05.2022 20:18
 */

class SupportBottomSheetFragment: BaseBottomSheetDialogFragment<FragmentSupportBottomSheetBinding>() {

    override fun getViewBinding() = FragmentSupportBottomSheetBinding.inflate(layoutInflater)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.email.setOnClickListener {
            val testIntent = Intent(Intent.ACTION_VIEW)
            val data =
                Uri.parse("mailto:?subject=" + "Support message"+ "&to=" + "soloviev@internet.ru")
            testIntent.data = data
            startActivity(testIntent)
            dismiss()
        }

        binding.regulations.setOnClickListener {
            val intent = Intent(requireContext(), RulesActivity::class.java)
            startActivity(intent)
            dismiss()
        }
    }

}