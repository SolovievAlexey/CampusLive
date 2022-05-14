package ru.campus.feature_location.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import ru.campus.core.di.AppDepsProvider
import ru.campus.core.presentation.BaseFragment
import ru.campus.core.presentation.MyOnClick
import ru.campus.feature_location.data.LocationModel
import ru.campus.feature_location.databinding.FragmentLocationBinding
import ru.campus.feature_location.di.DaggerLocationComponent
import ru.campus.feature_location.di.LocationComponent
import ru.campus.feature_location.presentation.adapter.LocationAdapter


class LocationFragment : BaseFragment<FragmentLocationBinding>() {

    private val component: LocationComponent by lazy {
        DaggerLocationComponent.builder()
            .deps(AppDepsProvider.deps)
            .build()
    }

    private val viewModel by viewModels<LocationViewModel> { component.viewModelsFactory() }
    private val myOnClick = object : MyOnClick<LocationModel> {
        override fun item(view: View, item: LocationModel) {
            binding.progressBar.isVisible = true
        }
    }

    private val adapter = LocationAdapter(myOnClick)
    override fun getViewBinding() = FragmentLocationBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.location(null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewModel.list.observe(viewLifecycleOwner, list)
        binding.editText.doAfterTextChanged {
            binding.progressBar.isVisible = true
            viewModel.location(it.toString())
        }
    }

    private val list = Observer<ArrayList<LocationModel>> { newModel ->
        binding.progressBar.isVisible = false
        adapter.setData(newModel)
    }

    private val success = Observer<LocationModel> {
        binding.progressBar.isVisible = false
    }

}