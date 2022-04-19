package ru.campus.live.ribbon.presentation.fragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.graphics.toColorInt
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ru.campus.live.R
import ru.campus.live.core.di.component.DaggerRibbonComponent
import ru.campus.live.core.di.component.RibbonComponent
import ru.campus.live.core.di.deps.AppDepsProvider
import ru.campus.live.core.presentation.ui.BaseFragment
import ru.campus.live.core.presentation.ui.MyOnClick
import ru.campus.live.databinding.FragmentFeedBinding
import ru.campus.live.ribbon.data.model.RibbonModel
import ru.campus.live.ribbon.presentation.adapter.FeedAdapter
import ru.campus.live.ribbon.presentation.viewmodel.RibbonViewModel
import java.util.concurrent.atomic.AtomicBoolean


class RibbonFragment : BaseFragment<FragmentFeedBinding>() {

    private val component: RibbonComponent by lazy {
        DaggerRibbonComponent.builder()
            .deps(AppDepsProvider.deps)
            .build()
    }

    private val myOnClick = object : MyOnClick<RibbonModel> {
        override fun item(view: View, item: RibbonModel) {
            if (view.id == R.id.fab) {
                findNavController().navigate(R.id.action_feedFragment_to_createPublicationFragment)
            } else {
                val bottomSheetDialog = RibbonBottomSheetFragment()
                val bundle = Bundle()
                bundle.putParcelable("publication_object", item)
                bottomSheetDialog.arguments = bundle
                bottomSheetDialog.show(
                    requireActivity().supportFragmentManager,
                    "FeedBottomSheetDialog"
                )
            }
        }
    }

    private val viewModel: RibbonViewModel by navGraphViewModels(R.id.feedFragment) {
        component.viewModelsFactory()
    }

    private val adapter = FeedAdapter(myOnClick)
    private var linearLayoutManager: LinearLayoutManager? = null
    override fun getViewBinding() = FragmentFeedBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.scrollEvent()
        viewModel.list.observe(viewLifecycleOwner, list)
        viewModel.startDiscussion.observe(viewLifecycleOwner, startDiscussion)
        viewModel.complaint.observe(viewLifecycleOwner, complaint)
        binding.swipeRefreshLayout.setColorSchemeColors("#517fba".toColorInt())
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.get(refresh = true)
        }

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_createPublicationFragment)
        }
    }

    private val list = Observer<ArrayList<RibbonModel>> { newModel ->
        if (binding.swipeRefreshLayout.isRefreshing)
            binding.swipeRefreshLayout.isRefreshing = false
        adapter.setData(newModel)
    }

    private val startDiscussion = Observer<RibbonModel> { model ->
        val bundle = Bundle()
        bundle.putParcelable("publication", model)
        findNavController().navigate(R.id.action_feedFragment_to_discussionFragment, bundle)
    }

    private val complaint = Observer<RibbonModel> {
        val isCancel = AtomicBoolean(false)
        val snack = Snackbar.make(
            binding.root, getString(R.string.complaint_response),
            Snackbar.LENGTH_LONG
        )
        val snackView = snack.view
        val tv =
            snackView.findViewById<TextView>(com.google.android.material.R.id.snackbar_action)
        tv.setTextColor("#f44336".toColorInt())
        snack.setAction(R.string.close) { isCancel.set(true) }
        snack.addCallback(object : Snackbar.Callback() {
            override fun onDismissed(snackbar: Snackbar, event: Int) {
                if (!isCancel.get()) {
                    viewModel.sendComplaintOnServer(it)
                    binding.fab.show()
                }
            }

            override fun onShown(snackbar: Snackbar) {
                binding.fab.hide()
            }

        })
        snack.show()
    }

    private fun RecyclerView.scrollEvent() {
        this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = linearLayoutManager?.childCount ?: 0
                val totalItemCount = linearLayoutManager?.itemCount ?: 0
                val firstVisibleItem = linearLayoutManager?.findFirstVisibleItemPosition() ?: 0
                if (visibleItemCount + firstVisibleItem >= totalItemCount) viewModel.get()

                if (dy < 0 && !binding.fab.isShown) {
                    binding.fab.show()
                } else if (dy > 0 && binding.fab.isShown) {
                    binding.fab.hide()
                }
            }
        })
    }

}