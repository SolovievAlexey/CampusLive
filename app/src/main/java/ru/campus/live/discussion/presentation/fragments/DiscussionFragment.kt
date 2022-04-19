package ru.campus.live.discussion.presentation.fragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.graphics.toColorInt
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ru.campus.live.R
import ru.campus.live.core.di.component.DaggerDiscussionComponent
import ru.campus.live.core.di.component.DiscussionComponent
import ru.campus.live.core.di.deps.AppDepsProvider
import ru.campus.live.core.presentation.ui.BaseFragment
import ru.campus.live.core.presentation.ui.BounceEdgeEffectFactory
import ru.campus.live.core.presentation.ui.MyOnClick
import ru.campus.live.databinding.FragmentDiscussionBinding
import ru.campus.live.discussion.data.model.DiscussionModel
import ru.campus.live.discussion.presentation.adapter.DiscussionAdapter
import ru.campus.live.discussion.presentation.viewmodel.DiscussionViewModel
import ru.campus.live.ribbon.data.model.RibbonModel
import java.util.concurrent.atomic.AtomicBoolean


class DiscussionFragment : BaseFragment<FragmentDiscussionBinding>() {

    private var publicationObject: RibbonModel? = null
    private val component: DiscussionComponent by lazy {
        DaggerDiscussionComponent.builder()
            .deps(AppDepsProvider.deps)
            .build()
    }

    private val viewModel: DiscussionViewModel by navGraphViewModels(R.id.discussionFragment) {
        component.viewModelsFactory()
    }

    private val myOnClick = object : MyOnClick<DiscussionModel> {
        override fun item(view: View, item: DiscussionModel) {
            if (item.hidden == 0) {
                val bottomSheetDialog = DiscussionBottomSheetFragment()
                val bundle = Bundle()
                bundle.putParcelable("item", item)
                bottomSheetDialog.arguments = bundle
                bottomSheetDialog.show(
                    (requireActivity().supportFragmentManager),
                    "BottomSheetDialog"
                )
            }
        }
    }

    private val adapter = DiscussionAdapter(myOnClick)

    override fun getViewBinding() = FragmentDiscussionBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { publicationObject = it.getParcelable("publication")!! }
        parentFragment?.setFragmentResultListener("discussionObject") { _, bundle ->
            val params: DiscussionModel? = bundle.getParcelable("object")
            if (params != null) viewModel.insert(params)
        }
        parentFragment?.setFragmentResultListener("reply") { _, bundle ->
            val params: DiscussionModel? = bundle.getParcelable("object")
            if (params != null) onReplyEvent(params)
        }
        viewModel.set(publicationObject!!)
        viewModel.get(publicationObject!!.comments)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolBar()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.edgeEffectFactory = BounceEdgeEffectFactory()
        binding.recyclerView.scrollEvent()
        viewModel.list.observe(viewLifecycleOwner, listLiveData)
        viewModel.title.observe(viewLifecycleOwner, titleLiveData)
        viewModel.complaintEvent.observe(viewLifecycleOwner, complaintEvent)
    }

    private fun initToolBar() {
        isProgressBarVisible(false)

        if (publicationObject?.comments == 0) {
            isProgressBarVisible(true)
        } else {
            isProgressBarVisible(false)
        }

        binding.toolbar.setNavigationIcon(R.drawable.baseline_close_black_24)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.toolbar.setOnMenuItemClickListener {
            isProgressBarVisible(true)
            viewModel.refresh()
            return@setOnMenuItemClickListener false
        }
        binding.fab.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("publication", publicationObject?.id ?: 0)
            findNavController().navigate(
                R.id.action_discussionFragment_to_createCommentFragment,
                bundle
            )
        }
    }

    private val listLiveData = Observer<ArrayList<DiscussionModel>> { model ->
        if (model.size > 1) isProgressBarVisible(false)
        adapter.setData(model)
    }

    private val titleLiveData = Observer<String> { title ->
        binding.toolbar.title = title
    }

    private val complaintEvent = Observer<DiscussionModel> {
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
                    binding.fab.show()
                }
            }

            override fun onShown(snackbar: Snackbar) {
                binding.fab.hide()
            }
        })
        snack.show()
    }


    private fun onReplyEvent(item: DiscussionModel) {
        val parent = if (item.parent == 0) item.id else item.parent
        val bundle = Bundle()
        bundle.putInt("publication", publicationObject!!.id)
        bundle.putInt("parent", parent)
        bundle.putInt("answered", item.id)
        findNavController().navigate(
            R.id.action_discussionFragment_to_createCommentFragment,
            bundle
        )
    }

    private fun isProgressBarVisible(visible: Boolean) {
        binding.progressBar.isVisible = visible
        if (visible) {
            binding.toolbar.menu.clear()
        } else {
            if (!binding.toolbar.menu.hasVisibleItems())
                binding.toolbar.inflateMenu(R.menu.discussion_menu)
        }
    }

    private fun RecyclerView.scrollEvent() {
        this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy < 0 && !binding.fab.isShown) {
                    binding.fab.show()
                } else if (dy > 0 && binding.fab.isShown) {
                    binding.fab.hide()
                }
            }
        })
    }

}