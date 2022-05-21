package ru.campus.feature_discussion.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.campus.core.di.AppDepsProvider
import ru.campus.core.presentation.BaseFragment
import ru.campus.core.presentation.MyOnClick
import ru.campus.feature_discussion.data.model.DiscussionModel
import ru.campus.feature_discussion.di.DaggerDiscussionComponent
import ru.campus.feature_discussion.di.DiscussionComponent
import ru.campus.feature_discussion.presentation.adapter.DiscussionAdapter
import ru.campus.feaure_discussion.R
import ru.campus.feaure_discussion.databinding.FragmentDiscussionBinding


class DiscussionFragment : BaseFragment<FragmentDiscussionBinding>() {

    private val publicationId by lazy { arguments?.getInt("id") }
    private val component: DiscussionComponent by lazy {
        DaggerDiscussionComponent.builder()
            .deps(AppDepsProvider.deps)
            .build()
    }

    private val viewModel by viewModels<DiscussionViewModel> {
        component.viewModelsFactory()
    }

    private val myOnClick = object : MyOnClick<DiscussionModel> {
        override fun item(view: View, item: DiscussionModel) {
            Log.d("MyLog", "Призошел клик на элемент!")
        }
    }

    private val adapter = DiscussionAdapter(myOnClick)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.get(publicationId = publicationId!!)
    }

    override fun getViewBinding() = FragmentDiscussionBinding.inflate(layoutInflater)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.listLiveData.observe(viewLifecycleOwner, listLiveData())
        viewModel.failureLiveData.observe(viewLifecycleOwner, failureLiveData())
    }

    private fun initToolbar() {
        binding.toolbar.inflateMenu(R.menu.refresh)
        binding.toolbar.setNavigationIcon(R.drawable.outline_close_black_24)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun listLiveData() = Observer<ArrayList<DiscussionModel>> { model ->
        adapter.setData(model)
        binding.fab.show()
    }

    private fun failureLiveData() = Observer<String> { error ->
        Log.d("MyLog", "Произошла ошибка! Расшифровка = $error")
    }

}