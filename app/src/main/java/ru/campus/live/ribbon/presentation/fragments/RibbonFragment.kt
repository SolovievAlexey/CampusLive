package ru.campus.live.ribbon.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.graphics.toColorInt
import androidx.core.view.isVisible
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
import ru.campus.live.core.presentation.BaseFragment
import ru.campus.live.core.presentation.MyOnClick
import ru.campus.live.databinding.FragmentRibbonBinding
import ru.campus.live.ribbon.data.model.RibbonModel
import ru.campus.live.ribbon.data.model.RibbonStatusModel
import ru.campus.live.ribbon.presentation.adapter.RibbonAdapter
import ru.campus.live.ribbon.presentation.viewmodel.RibbonViewModel
import java.util.concurrent.atomic.AtomicBoolean


class RibbonFragment : BaseFragment<FragmentRibbonBinding>() {

    private var totalScrolled = 0
    private val component: RibbonComponent by lazy {
        DaggerRibbonComponent.builder()
            .deps(AppDepsProvider.deps)
            .build()
    }

    private val viewModel: RibbonViewModel by navGraphViewModels(R.id.feedFragment) { component.viewModelsFactory() }
    private val adapter = RibbonAdapter(myOnClick())
    private var linearLayoutManager: LinearLayoutManager? = null

    override fun getViewBinding() = FragmentRibbonBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()

        viewModel.list.observe(viewLifecycleOwner, listLiveData())
        viewModel.startDiscussion.observe(viewLifecycleOwner, startDiscussionEvent())
        viewModel.complaintEvent.observe(viewLifecycleOwner, complaintEvent())
        viewModel.missing.observe(viewLifecycleOwner, missingLiveData())
        viewModel.status.observe(viewLifecycleOwner, status())

        binding.swipeRefreshLayout.setColorSchemeColors("#517fba".toColorInt())
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.get(refresh = true)
        }
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_createPublicationFragment)
        }
    }

    private fun initRecyclerView() {
        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.scrollEvent()
    }

    private fun myOnClick() = object : MyOnClick<RibbonModel> {
        override fun item(view: View, item: RibbonModel) {
            if (view.id == R.id.fab) {
                findNavController().navigate(R.id.action_feedFragment_to_createPublicationFragment)
            } else {
                RibbonBottomSheetFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable("publication_object", item)
                    }
                }.show(requireActivity().supportFragmentManager, "RibbonBottomSheetDialog")
            }
        }
    }

    private fun listLiveData() = Observer<ArrayList<RibbonModel>> { newModel ->
        if (binding.swipeRefreshLayout.isRefreshing)
            binding.swipeRefreshLayout.isRefreshing = false
        binding.error.isVisible = false
        adapter.setData(newModel)
    }

    private fun status() = Observer<RibbonStatusModel> { model ->
        binding.name.text = model.location
        val karam = if(model.karma >= 0) "+ ${model.karma} " else model.karma.toString()
        val status = "${model.falovers} ${getString(R.string.falovers)} · $karam ${getString(R.string.karma)}"
        binding.status.text = status
    }

    private fun startDiscussionEvent() = Observer<RibbonModel> { model ->
        findNavController().navigate(R.id.action_feedFragment_to_discussionFragment,
            Bundle().apply {
                putParcelable("publication", viewModel.convertToDiscussionModel(model))
            }
        )
    }

    private fun missingLiveData() = Observer<Boolean> {
        if (binding.swipeRefreshLayout.isRefreshing)
            binding.swipeRefreshLayout.isRefreshing = false
        binding.icon.alpha = 0F
        binding.message.alpha = 0F
        binding.error.isVisible = it
        binding.icon.animate().alpha(1f).duration = 400
        binding.message.animate().alpha(1F).duration = 400
    }

    private fun complaintEvent() = Observer<RibbonModel> {
        val isCancel = AtomicBoolean(false)
        val snack = Snackbar.make(binding.root,
            getString(R.string.complaint_response),
            Snackbar.LENGTH_LONG)
        val snackView = snack.view
        val tv = snackView.findViewById<TextView>(com.google.android.material.R.id.snackbar_action)
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

        linearLayoutManager

    }

    private fun RecyclerView.scrollEvent() {
        this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                totalScrolled += dy
                binding.fab.apply { if(totalScrolled > 300) hide() else show() }
            }
        })
    }

}