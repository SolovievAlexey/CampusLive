package ru.campus.feature_start.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayoutMediator
import ru.campus.core.di.AppDepsProvider
import ru.campus.core.presentation.BaseFragment
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
        viewModel.success.observe(viewLifecycleOwner, success())
        viewModel.failure.observe(viewLifecycleOwner, failure())
        viewModel.list.observe(viewLifecycleOwner, list())
        binding.start.setOnClickListener {
            isVisibleProgressBar(true)
            viewModel.login()
        }
    }

    private fun list() = Observer<ArrayList<StartModel>> { newModel ->
        Log.d("MyLog", "Полученны новые данные для отображения!")
        adapter.setData(newModel)
    }

    private fun success() = Observer<LoginModel> {
        Log.d("MyLog", "Тут должен был быть переход на экран выбора локации")
    }

    private fun failure() = Observer<Int> {
        isVisibleProgressBar(false)
        Log.d("MyLog", "Произошла ошибка!")
    }

    private fun isVisibleProgressBar(visible: Boolean) {
        binding.progressBar.isVisible = visible
        binding.start.isVisible = !visible
    }

}