package ru.campus.feature_start.presentation

import androidx.fragment.app.viewModels
import ru.campus.core.di.AppDepsProvider
import ru.campus.core.presentation.BaseFragment
import ru.campus.feature_start.databinding.FragmentStartBinding
import ru.campus.feature_start.di.DaggerStartComponent
import ru.campus.feature_start.di.StartComponent


class StartFragment : BaseFragment<FragmentStartBinding>() {

    private val component: StartComponent by lazy {
        DaggerStartComponent.builder()
            .deps(AppDepsProvider.deps)
            .build()
    }

    private val viewModel by viewModels<StartViewModel> {
        component.viewModelsFactory()
    }

    override fun getViewBinding() = FragmentStartBinding.inflate(layoutInflater)

}