package ru.campus.live.dialog

import android.content.Context
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import ru.campus.live.core.data.model.ErrorObject
import ru.campus.live.core.presentation.ui.BaseDialogFragment
import ru.campus.live.databinding.FragmentCustomDialogBinding

class ErrorDialog : BaseDialogFragment<FragmentCustomDialogBinding>() {

    private var params: ErrorObject = ErrorObject()
    override fun getViewBinding() = FragmentCustomDialogBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { params = it.getParcelable("params")!! }
        if (params.code == 401) clearUserToken()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.message.text = params.message
        Glide.with(requireContext()).load(params.icon).into(binding.icon)
        binding.close.setOnClickListener {
            dismiss()
        }
    }

    private fun clearUserToken() {
        val sPref = context?.getSharedPreferences("AppDB", Context.MODE_PRIVATE)
        with(sPref!!.edit()) {
            putInt("UID", 0)
            putString("TOKEN", "")
            putInt("RATING", 0)
            apply()
        }
    }

}