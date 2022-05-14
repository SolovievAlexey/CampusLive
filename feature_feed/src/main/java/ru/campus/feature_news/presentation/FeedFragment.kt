package ru.campus.feature_news.presentation

import ru.campus.core.presentation.BaseFragment
import ru.campus.feature_news.databinding.FragmentFeedBinding


class FeedFragment : BaseFragment<FragmentFeedBinding>() {
    override fun getViewBinding() = FragmentFeedBinding.inflate(layoutInflater)
}