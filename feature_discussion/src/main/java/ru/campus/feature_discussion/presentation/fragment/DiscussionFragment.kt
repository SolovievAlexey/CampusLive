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
import ru.campus.core.di.AppDepsProvider
import ru.campus.core.presentation.BaseFragment
import ru.campus.core.presentation.MyOnClick
import ru.campus.feature_discussion.data.model.DiscussionModel
import ru.campus.feature_discussion.di.DaggerDiscussionComponent
import ru.campus.feature_discussion.di.DiscussionComponent
import ru.campus.feature_discussion.presentation.adapter.DiscussionAdapter
import ru.campus.feature_discussion.presentation.viewmodel.DiscussionViewModel
import ru.campus.feaure_discussion.R
import ru.campus.feaure_discussion.databinding.FragmentDiscussionBinding


class DiscussionFragment : BaseFragment<FragmentDiscussionBinding>() {

    private val publicationId by lazy { arguments?.getInt("id") ?: 0 }
    private val component: DiscussionComponent by lazy {
        DaggerDiscussionComponent.builder()
            .deps(AppDepsProvider.deps)
            .build()
    }

    private val viewModel by viewModels<DiscussionViewModel> { component.viewModelsFactory() }
    private val myOnClick = object : MyOnClick<DiscussionModel> {
        override fun item(view: View, item: DiscussionModel) {
            Log.d("MyLog", "Призошел клик на элемент!")
        }
    }

    private val adapter = DiscussionAdapter(myOnClick)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.get(publicationId = publicationId)
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

        binding.fab.isVisible = true
        binding.fab.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("publication", publicationId)
            findNavController().navigate(R.id.action_discussionFragment_to_createCommentFragment, bundle)
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
            if(menuItem.itemId == R.id.refresh) {
                binding.toolbar.menu.clear()
                binding.progressBar.isVisible = true
                viewModel.get(publicationId)
            }
            return@setOnMenuItemClickListener false
        }
    }

    private fun listLiveData() = Observer<ArrayList<DiscussionModel>> { model ->
        binding.toolbar.title = "10 Комментариев"
        binding.progressBar.isVisible = false
        if(!binding.toolbar.menu.hasVisibleItems())
            binding.toolbar.inflateMenu(R.menu.refresh)
        adapter.setData(model)
        binding.fab.show()
    }

    private fun failureLiveData() = Observer<String> { error ->
        Log.d("MyLog", "Произошла ошибка! Расшифровка = $error")
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