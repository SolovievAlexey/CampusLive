package ru.campus.feature_mediaview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.bumptech.glide.Glide
import ru.campus.core.data.BaseDomainDataStore
import ru.campus.core.presentation.BaseFragment
import ru.campus.feature_mediaview.databinding.FragmentMediaViewBinding

class MediaViewFragment : BaseFragment<FragmentMediaViewBinding>() {

    private val url by lazy { arguments?.getString("url") }

    override fun getViewBinding() = FragmentMediaViewBinding.inflate(layoutInflater)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val domain = BaseDomainDataStore(context = requireContext()).get()
        Glide.with(requireContext()).load(domain + url).into(binding.image)
    }

    override fun onDestroy() {
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        super.onDestroy()
    }

}