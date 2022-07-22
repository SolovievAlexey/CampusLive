package ru.campus.feature_location.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import ru.campus.core.di.AppDepsProvider
import ru.campus.core.presentation.BaseFragment
import ru.campus.core.presentation.MyOnClick
import ru.campus.feature_location.R
import ru.campus.feature_location.data.model.LocationModel
import ru.campus.feature_location.databinding.FragmentBesideBinding
import ru.campus.feature_location.di.DaggerLocationComponent
import ru.campus.feature_location.di.LocationComponent
import ru.campus.feature_location.presentation.adapter.LocationAdapter


class BesideFragment : BaseFragment<FragmentBesideBinding>() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val component: LocationComponent by lazy {
        DaggerLocationComponent.builder()
            .deps(AppDepsProvider.deps)
            .build()
    }

    private val viewMode by viewModels<BesideViewModel> {
        component.viewModelsFactory()
    }

    private val myOnClick = object : MyOnClick<LocationModel> {
        override fun item(view: View, item: LocationModel) {
        }
    }

    private val adapter = LocationAdapter(myOnClick)

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val result = permissions["android.permission.ACCESS_COARSE_LOCATION"] ?: false
        if (result) startLocationClient()
    }

    override fun getViewBinding() = FragmentBesideBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initPermission()

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewMode.liveData.observe(viewLifecycleOwner) { model ->
            adapter.setData(model)
            binding.progressBar.isVisible = false
        }
    }

    private fun initToolbar() {
        binding.toolbar.setTitle(R.string.gps)
        binding.toolbar.setNavigationIcon(R.drawable.ic_action_back)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initPermission() {
        locationPermissionRequest.launch(
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION)
        )
    }

    @SuppressLint("MissingPermission")
    private fun startLocationClient() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            Log.d("MyLog", "latitude = " + location?.latitude)
            Log.d("MyLog", "longitude = " + location?.longitude)
            viewMode.get(latitude = 46.355023624107226, longitude = 48.05598367987016)
        }
    }

}