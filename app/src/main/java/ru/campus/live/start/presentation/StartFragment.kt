package ru.campus.live.start.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import ru.campus.live.R
import ru.campus.live.core.data.model.ErrorObject
import ru.campus.live.core.di.component.DaggerStartComponent
import ru.campus.live.core.di.component.StartComponent
import ru.campus.live.core.di.deps.AppDepsProvider
import ru.campus.live.core.presentation.ui.BaseFragment
import ru.campus.live.databinding.FragmentStartBinding
import ru.campus.live.dialog.ErrorDialog
import ru.campus.live.start.data.model.LoginModel
import ru.campus.live.start.data.model.StartModel
import ru.campus.live.start.presentation.adapter.StartAdapter


class StartFragment : BaseFragment<FragmentStartBinding>() {

    private val adapter = StartAdapter()
    private val component: StartComponent by lazy {
        DaggerStartComponent.builder()
            .deps(AppDepsProvider.deps)
            .build()
    }

    private val viewModel by viewModels<StartViewModel> { component.viewModelsFactory() }
    override fun getViewBinding() = FragmentStartBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { _, _ -> }.attach()
        viewModel.success.observe(viewLifecycleOwner, success)
        viewModel.failure.observe(viewLifecycleOwner, failure)
        viewModel.list.observe(viewLifecycleOwner, list)
        binding.start.setOnClickListener {
            isVisibleProgressBar(true)
            viewModel.login()
        }
    }

    private fun isVisibleProgressBar(visibility: Boolean) {
        binding.progressBar.isVisible = visibility
        binding.start.isVisible = !visibility
    }

    private val list = Observer<ArrayList<StartModel>> { newModel ->
        adapter.setData(newModel)
    }

    private val success = Observer<LoginModel> {
        findNavController().navigate(R.id.action_onBoarFragment_to_locationFragment)
    }

    private val failure = Observer<ErrorObject> { errorObject ->
        isVisibleProgressBar(false)
        val bundle = Bundle()
        bundle.putParcelable("params", errorObject)
        val customDialog = ErrorDialog()
        customDialog.arguments = bundle
        customDialog.show(requireActivity().supportFragmentManager, "CustomErrorDialog")
    }

}