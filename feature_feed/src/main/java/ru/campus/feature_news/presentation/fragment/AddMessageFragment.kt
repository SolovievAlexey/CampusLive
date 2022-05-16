package ru.campus.feature_news.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import ru.campus.core.presentation.BaseFragment
import ru.campus.feature_news.R
import ru.campus.feature_news.databinding.FragmentAddMessageBinding


class AddMessageFragment : BaseFragment<FragmentAddMessageBinding>() {

    override fun getViewBinding() = FragmentAddMessageBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.gallery.setOnClickListener {
            val request = NavDeepLinkRequest.Builder
                .fromUri("android-app://ru.campus.live/galleryFragment".toUri())
                .build()
            findNavController().navigate(request)
        }
    }

}