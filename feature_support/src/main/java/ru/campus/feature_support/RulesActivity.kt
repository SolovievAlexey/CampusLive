package ru.campus.feature_support

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import ru.campus.feature_support.databinding.ActivityRulesBinding

class RulesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRulesBinding
    private val viewModel by viewModels<RulesViewModel>()
    private val adapter = RulesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRulesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        viewModel.get()
        viewModel.liveData.observe(this, liveData())
        initToolbar()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun initToolbar() {
        binding.toolbar.title = getString(R.string.regulations)
        binding.toolbar.setNavigationIcon(R.drawable.ic_action_back)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun liveData() = Observer<ArrayList<String>> { model ->
        adapter.setData(model)
    }

}