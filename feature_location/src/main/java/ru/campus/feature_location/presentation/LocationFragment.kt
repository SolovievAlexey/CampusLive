package ru.campus.feature_location.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.messaging.FirebaseMessaging
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
            FirebaseMessaging.getInstance().subscribeToTopic("location_" + item.id)
                .addOnSuccessListener { viewModel.registration(locationModel = item) }
                .addOnFailureListener { viewModel.registration(locationModel = item) }
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

        viewModel.list.observe(viewLifecycleOwner, list())
        viewModel.success.observe(viewLifecycleOwner, success())

        binding.editText.doAfterTextChanged {
            binding.progressBar.isVisible = true
            viewModel.location(it.toString())
        }
    }

    private fun list() = Observer<ArrayList<LocationModel>> { newModel ->
        binding.progressBar.isVisible = false
        adapter.setData(newModel)
    }

    private fun success() = Observer<Boolean> {
        binding.progressBar.isVisible = false

        val navGraphResourceId = resources.getIdentifier(
            "main_navigation", "id", requireContext().packageName)

        val options = NavOptions.Builder().setPopUpTo(
            destinationId = navGraphResourceId, inclusive = true).build()

        val request = NavDeepLinkRequest.Builder
            .fromUri("android-app://ru.campus.live/feedFragment".toUri())
            .build()
        findNavController().navigate(request, options)
    }

}