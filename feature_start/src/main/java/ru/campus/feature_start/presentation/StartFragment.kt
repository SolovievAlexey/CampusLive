package ru.campus.feature_start.presentation

import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import ru.campus.core.di.AppDepsProvider
import ru.campus.core.presentation.BaseFragment
import ru.campus.feature_dialog.CustomDialogFragment
import ru.campus.feature_start.data.model.LoginModel
import ru.campus.feature_start.data.model.StartModel
import ru.campus.feature_start.databinding.FragmentStartBinding
import ru.campus.feature_start.di.DaggerStartComponent
import ru.campus.feature_start.di.StartComponent
import ru.campus.feature_start.presentation.adapter.StartAdapter


class StartFragment : BaseFragment<FragmentStartBinding>() {

    private val adapter = StartAdapter()
    private val component: StartComponent by lazy {
        DaggerStartComponent.builder()
            .deps(AppDepsProvider.deps)
            .build()
    }

    private val viewModel by viewModels<StartViewModel> { component.viewModelsFactory() }
    override fun getViewBinding() = FragmentStartBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.start()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { _, _ -> }.attach()
        viewModel.successLiveData.observe(viewLifecycleOwner, success())
        viewModel.failureLiveData.observe(viewLifecycleOwner, failure())
        viewModel.listLiveData.observe(viewLifecycleOwner, list())
        binding.start.setOnClickListener {
            isVisibleProgressBar(true)
            viewModel.login()
        }
    }

    private fun list() = Observer<ArrayList<StartModel>> { newModel ->
        adapter.setData(newModel)
    }

    private fun success() = Observer<LoginModel> {
        val navGraphResourceId = resources.getIdentifier(
            "main_navigation", "id", requireContext().packageName)

        val options = NavOptions.Builder().setPopUpTo(
            destinationId = navGraphResourceId, inclusive = true).build()

        val request = NavDeepLinkRequest.Builder
            .fromUri("android-app://ru.campus.live/locationFragment".toUri())
            .build()
        findNavController().navigate(request, options)
    }

    private fun failure() = Observer<String> { error ->
        isVisibleProgressBar(false)
        CustomDialogFragment().apply {
            arguments = Bundle(1).apply {
                putString("message", error)
            }
        }.show(requireActivity().supportFragmentManager, "CustomDialogFragment")
    }

    private fun isVisibleProgressBar(visible: Boolean) {
        binding.progressBar.isVisible = visible
        binding.start.isVisible = !visible
    }

}