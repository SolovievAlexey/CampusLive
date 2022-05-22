package ru.campus.feature_discussion.presentation.fragment

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.graphics.toColorInt
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import ru.campus.core.di.AppDepsProvider
import ru.campus.core.presentation.BaseFragment
import ru.campus.core.presentation.MyOnClick
import ru.campus.feature_discussion.data.model.DiscussionModel
import ru.campus.feature_discussion.data.model.DiscussionViewType
import ru.campus.feature_discussion.data.model.FeedModel
import ru.campus.feature_discussion.di.DaggerDiscussionComponent
import ru.campus.feature_discussion.di.DiscussionComponent
import ru.campus.feature_discussion.presentation.adapter.DiscussionAdapter
import ru.campus.feature_discussion.presentation.viewmodel.DiscussionViewModel
import ru.campus.feature_discussion.presentation.viewmodel.DiscussionViewModelFactory
import ru.campus.feaure_discussion.R
import ru.campus.feaure_discussion.databinding.FragmentDiscussionBinding
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject


class DiscussionFragment : BaseFragment<FragmentDiscussionBinding>() {

    private lateinit var publication: DiscussionModel
    private val component: DiscussionComponent by lazy {
        DaggerDiscussionComponent.builder()
            .deps(AppDepsProvider.deps)
            .build()

    }

    @Inject
    lateinit var factory: DiscussionViewModelFactory.Factory
    private val viewModel: DiscussionViewModel by navGraphViewModels(R.id.discussionFragment) {
        factory.create(publication)
    }

    private val myOnClick = object : MyOnClick<DiscussionModel> {
        override fun item(view: View, item: DiscussionModel) {
            if (item.hidden == 0) {
                DiscussionBottomSheetFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable("item", item)
                    }
                }.show((requireActivity().supportFragmentManager), "DiscussionBottomSheetFragment")
            }
        }
    }

    private val adapter = DiscussionAdapter(myOnClick)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val json = arguments?.getString("publication")
        val feedModel = Gson().fromJson(json, FeedModel::class.java)
        publication = DiscussionModel(
            type = DiscussionViewType.PUBLICATION,
            id = feedModel.id,
            mediaWidth = feedModel.mediaWidth,
            mediaHeight = feedModel.mediaHeight,
            message = feedModel.message,
            attachment = feedModel.attachment,
            rating = feedModel.rating,
            vote = feedModel.vote,
            relativeTime = feedModel.relativeTime)
        component.inject(this)
        viewModel.get(publicationId = publication.id)
    }

    override fun getViewBinding() = FragmentDiscussionBinding.inflate(layoutInflater)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.scrollEvent()

        viewModel.listLiveData.observe(viewLifecycleOwner, listLiveData())
        viewModel.titleLiveData.observe(viewLifecycleOwner, titleLiveData())
        viewModel.complaintLiveData.observe(viewLifecycleOwner, complaintLiveData())
        viewModel.replyEvent.observe(viewLifecycleOwner, replyEvent())

        binding.fab.isVisible = true
        binding.fab.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("publication", publication.id)
            findNavController().navigate(R.id.action_discussionFragment_to_createCommentFragment,
                bundle)
        }

        requireActivity().supportFragmentManager
            .setFragmentResultListener("new_comment", viewLifecycleOwner) { _, bundle ->
                val comment: DiscussionModel? = bundle.getParcelable("item")
                if (comment != null) viewModel.insert(comment)
            }
    }

    private fun initToolbar() {
        binding.toolbar.title = getString(R.string.update)
        binding.toolbar.inflateMenu(R.menu.refresh)
        binding.toolbar.setNavigationIcon(R.drawable.outline_arrow_back_black_24)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.refresh) {
                isVisibleProgressBar(visible = true)
                viewModel.get(publicationId = publication.id)
            }
            return@setOnMenuItemClickListener false
        }
    }

    private fun listLiveData() = Observer<ArrayList<DiscussionModel>> { model ->
        binding.progressBar.isVisible = false
        if (!binding.toolbar.menu.hasVisibleItems()) binding.toolbar.inflateMenu(R.menu.refresh)
        if (!binding.recyclerView.isVisible) binding.recyclerView.isVisible = true
        adapter.setData(model)
        binding.fab.show()
    }

    private fun titleLiveData() = Observer<String> { title ->
        binding.toolbar.title = title
    }

    private fun complaintLiveData() = Observer<DiscussionModel> { item ->
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
                    viewModel.sendComplaintDataOnServer(id = item.id)
                }
            }

        })
        snack.show()
    }

    private fun replyEvent() = Observer<DiscussionModel> { item ->
        val parent = if (item.parent == 0) item.id else item.parent
        val bundle = Bundle()
        bundle.putInt("publication", publication.id)
        bundle.putInt("parent", parent)
        bundle.putInt("answered", item.id)
        findNavController().navigate(R.id.action_discussionFragment_to_createCommentFragment,
            bundle)
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

    private fun isVisibleProgressBar(visible: Boolean) {
        binding.progressBar.isVisible = visible
        if(visible)
            binding.toolbar.menu.clear()
        else
            if(!binding.toolbar.menu.hasVisibleItems()) binding.toolbar.inflateMenu(R.menu.refresh)
    }

}