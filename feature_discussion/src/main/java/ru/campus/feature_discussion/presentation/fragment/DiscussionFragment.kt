package ru.campus.feature_discussion.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    private val viewModel: DiscussionViewModel by viewModels() {
        factory.create(DiscussionModel())
    }

    private val myOnClick = object : MyOnClick<DiscussionModel> {
        override fun item(view: View, item: DiscussionModel) {
            Log.d("MyLog", "Призошел клик на элемент!")
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
        viewModel.failureLiveData.observe(viewLifecycleOwner, failureLiveData())
        viewModel.titleLiveData.observe(viewLifecycleOwner, titleLiveData())

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
        binding.toolbar.setNavigationIcon(R.drawable.outline_close_black_24)
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
        if (binding.error.isVisible) binding.error.isVisible = false
        if (!binding.recyclerView.isVisible) binding.recyclerView.isVisible = true
        adapter.setData(model)
        binding.fab.show()
    }

    private fun failureLiveData() = Observer<String> { error ->
        binding.error.text = error
        if (binding.recyclerView.isVisible) binding.recyclerView.isVisible = false
        if (!binding.error.isVisible) binding.error.isVisible = true
        isVisibleProgressBar(visible = false)
    }

    private fun titleLiveData() = Observer<String> { title ->
        binding.toolbar.title = title
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