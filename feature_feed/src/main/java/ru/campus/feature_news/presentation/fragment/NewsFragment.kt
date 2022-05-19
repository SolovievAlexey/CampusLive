package ru.campus.feature_news.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.graphics.toColorInt
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.campus.core.data.DomainDataStore
import ru.campus.core.data.GalleryDataModel
import ru.campus.core.di.AppDepsProvider
import ru.campus.core.presentation.BaseFragment
import ru.campus.core.presentation.MyOnClick
import ru.campus.feature_dialog.DialogDataModel
import ru.campus.feature_news.data.FeedModel
import ru.campus.feature_news.databinding.FragmentFeedBinding
import ru.campus.feature_news.di.DaggerFeedComponent
import ru.campus.feature_news.di.FeedComponent
import ru.campus.feature_news.presentation.viewmodel.FeedViewModel
import ru.campus.feature_news.presentation.adapter.FeedAdapter
import javax.inject.Inject


class NewsFragment : BaseFragment<FragmentFeedBinding>() {

    private val component: FeedComponent by lazy {
        DaggerFeedComponent.builder()
            .deps(AppDepsProvider.deps)
            .build()
    }

    @Inject
    lateinit var domainDataStore: DomainDataStore
    private val viewModel by viewModels<FeedViewModel> {
        component.viewModelsFactory()
    }

    private val myOnClick = object : MyOnClick<FeedModel> {
        override fun item(view: View, item: FeedModel) {
            NewsMenuSheetFragment().show(requireActivity().supportFragmentManager,
                "FeedMenuSheetFragment")
        }
    }

    private val adapter by lazy { FeedAdapter(domainDataStore, myOnClick) }
    override fun getViewBinding() = FragmentFeedBinding.inflate(layoutInflater)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.get()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().supportFragmentManager
            .setFragmentResultListener("publication", viewLifecycleOwner) { _, bundle ->
                val publication: FeedModel? = bundle.getParcelable("publication")
                if (publication != null) viewModel.insert(publication = publication)
            }

        viewModel.list.observe(viewLifecycleOwner, listLiveData())
        viewModel.failure.observe(viewLifecycleOwner, failure())

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.scrollEvent()

        binding.swipeRefreshLayout.setColorSchemeColors("#517fba".toColorInt())
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.get()
        }

        binding.fab.setOnClickListener {
            val request = NavDeepLinkRequest.Builder
                .fromUri("android-app://ru.campus.live/addMessageFragment".toUri())
                .build()
            findNavController().navigate(request)
        }
    }

    private fun listLiveData() = Observer<ArrayList<FeedModel>> { newModel ->
        binding.error.isVisible = false
        if (binding.swipeRefreshLayout.isRefreshing)
            binding.swipeRefreshLayout.isRefreshing = false
        adapter.setData(newModel)
        binding.recyclerView.smoothScrollToPosition(0)
    }

    private fun failure() = Observer<String> { error ->
        if (binding.swipeRefreshLayout.isRefreshing)
            binding.swipeRefreshLayout.isRefreshing = false
        binding.error.isVisible = true
        binding.message.text = error
    }

    private fun RecyclerView.scrollEvent() {
        this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                binding.fab.apply { if (isVisibleFab(dy)) show() else hide() }
            }
        })
    }

    private fun isVisibleFab(dy: Int): Boolean {
        return dy <= 0
    }

}