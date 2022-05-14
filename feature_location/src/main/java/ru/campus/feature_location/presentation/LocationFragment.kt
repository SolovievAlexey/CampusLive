package ru.campus.feature_location.presentation

import android.os.Bundle
import android.view.View
import ru.campus.core.presentation.BaseFragment
import ru.campus.feature_location.databinding.FragmentLocationBinding


class LocationFragment : BaseFragment<FragmentLocationBinding>() {

    override fun getViewBinding() = FragmentLocationBinding.inflate(layoutInflater)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}