package ru.campus.feature_start.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.campus.core.presentation.BaseFragment
import ru.campus.feature_start.R
import ru.campus.feature_start.databinding.FragmentStartBinding


class StartFragment : BaseFragment<FragmentStartBinding>() {

    override fun getViewBinding() = FragmentStartBinding.inflate(layoutInflater)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}