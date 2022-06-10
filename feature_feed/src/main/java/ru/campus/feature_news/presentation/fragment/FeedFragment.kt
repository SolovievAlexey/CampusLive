package ru.campus.feature_news.presentation.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.graphics.toColorInt
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import ru.campus.core.data.DomainDataStore
import ru.campus.core.di.AppDepsProvider
import ru.campus.core.presentation.BaseFragment
import ru.campus.core.presentation.MyOnClick
import ru.campus.feature_news.R
import ru.campus.feature_news.data.model.FeedModel
import ru.campus.feature_news.data.model.StatusModel
import ru.campus.feature_news.databinding.FragmentFeedBinding
import ru.campus.feature_news.di.DaggerFeedComponent
import ru.campus.feature_news.di.FeedComponent
import ru.campus.feature_news.presentation.adapter.FeedAdapter
import ru.campus.feature_news.presentation.viewmodel.FeedViewModel
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject


class FeedFragment : BaseFragment<FragmentFeedBinding>(), AppBarLayout.OnOffsetChangedListener {

    private val component: FeedComponent by lazy {
        DaggerFeedComponent.builder()
            .deps(AppDepsProvider.deps)
            .build()
    }

    @Inject
    lateinit var domainDataStore: DomainDataStore
    private val viewModel: FeedViewModel by navGraphViewModels(R.id.feedFragment) {
        component.viewModelsFactory()
    }

    private val myOnClick = object : MyOnClick<FeedModel> {
        override fun item(view: View, item: FeedModel) {
            if (view.id == R.id.container) {
                val bundle = Bundle(1).apply { putParcelable("item", item) }
                findNavController().navigate(R.id.newsFeedBottomSheetFragment, bundle)

            } else {
                val request = NavDeepLinkRequest.Builder
                    .fromUri("android-app://ru.campus.live/mediaViewFragment/?url=${item.attachment?.path}".toUri())
                    .build()
                findNavController().navigate(request)
            }
        }
    }

    private val adapter by lazy { FeedAdapter(domainDataStore, myOnClick) }
    override fun getViewBinding() = FragmentFeedBinding.inflate(layoutInflater)
    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
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
        viewModel.scrollOnPositionEvent.observe(viewLifecycleOwner, scrollOnPositionEvent())
        viewModel.complaintLiveData.observe(viewLifecycleOwner, complaintLiveData())
        viewModel.discussionLiveData.observe(viewLifecycleOwner, discussionLiveData())
        viewModel.statusLiveData.observe(viewLifecycleOwner, statusLiveData())

        initRecyclerView()
        binding.fab.setOnClickListener {
            val request = NavDeepLinkRequest.Builder
                .fromUri("android-app://ru.campus.live/addMessageFragment".toUri())
                .build()
            findNavController().navigate(request)
        }

        binding.menu.setOnClickListener {
            val request = NavDeepLinkRequest.Builder
                .fromUri("android-app://ru.campus.live/supportBottomSheetFragment".toUri())
                .build()
            findNavController().navigate(request)
        }

        binding.swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#017bac"))
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.get(refresh = true)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.appBarLayout.addOnOffsetChangedListener(this)
    }

    override fun onPause() {
        super.onPause()
        binding.appBarLayout.removeOnOffsetChangedListener(this)
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        binding.swipeRefreshLayout.isEnabled = verticalOffset == 0
    }

    private fun initRecyclerView() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.scrollEvent()
    }

    private fun listLiveData() = Observer<ArrayList<FeedModel>> { newModel ->
        binding.swipeRefreshLayout.isRefreshing = false
        binding.errorMessage.isVisible = false
        adapter.setData(newModel)
    }

    private fun scrollOnPositionEvent() = Observer<Int> { position ->
        binding.recyclerView.smoothScrollToPosition(position)
    }

    private fun failure() = Observer<String> { error ->
        if (binding.swipeRefreshLayout.isRefreshing)
            binding.swipeRefreshLayout.isRefreshing = false
        binding.errorMessage.isVisible = true
        binding.errorMessage.text = error
    }

    @SuppressLint("SetTextI18n")
    private fun statusLiveData() = Observer<StatusModel> { model ->
        binding.location.text = model.location
        binding.status.text = "${model.views} views • karma ${model.karma}"
    }

    private fun complaintLiveData() = Observer<FeedModel> { item ->
        val isCancel = AtomicBoolean(false)
        val snack = Snackbar.make(
            binding.root, getString(R.string.complaint_response),
            Snackbar.LENGTH_LONG
        )
        val snackView = snack.view
        val tv = snackView.findViewById<TextView>(com.google.android.material.R.id.snackbar_action)
        tv.setTextColor("#f44336".toColorInt())
        snack.setAction(R.string.close) { isCancel.set(true) }
        snack.addCallback(object : Snackbar.Callback() {
            override fun onDismissed(snackbar: Snackbar, event: Int) {
                if (!isCancel.get()) {
                    viewModel.sendComplaintDataOnServer(item = item)
                    binding.fab.show()
                }
            }

            override fun onShown(snackbar: Snackbar) {
                binding.fab.hide()
            }

        })
        snack.show()
    }

    private fun discussionLiveData() = Observer<FeedModel> { item ->
        val json = Gson().toJson(item)
        val request = NavDeepLinkRequest.Builder
            .fromUri("android-app://ru.campus.live/discussionFragment/?publication=${json}".toUri())
            .build()
        findNavController().navigate(request)
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