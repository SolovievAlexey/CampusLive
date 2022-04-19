package ru.campus.live.ribbon.presentation.fragments.base

import ru.campus.live.core.presentation.ui.BaseFragment
import ru.campus.live.databinding.FragmentFeedBinding

class BaseRibbonFragment: BaseFragment<FragmentFeedBinding>() {

    override fun getViewBinding() = FragmentFeedBinding.inflate(layoutInflater)
}