package ru.campus.live.start.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.campus.live.databinding.ItemPresentationBinding
import ru.campus.live.start.presentation.adapter.diff.StartDiffUtilCallBack
import ru.campus.live.start.presentation.adapter.holder.StartViewHolder
import ru.campus.live.start.data.model.StartModel

class StartAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val model = ArrayList<StartModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding =
            ItemPresentationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StartViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as StartViewHolder).bind(model[position])
    }

    override fun getItemCount(): Int {
        return model.size
    }

    fun setData(newModel: ArrayList<StartModel>) {
        val result =
            DiffUtil.calculateDiff(StartDiffUtilCallBack(model, newModel))
        model.clear()
        model.addAll(newModel)
        result.dispatchUpdatesTo(this)
    }

}