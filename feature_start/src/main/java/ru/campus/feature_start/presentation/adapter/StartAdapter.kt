package ru.campus.feature_start.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.campus.feature_start.data.model.StartModel
import ru.campus.feature_start.databinding.ItemPresentationBinding
import ru.campus.feature_start.presentation.adapter.diff.StartDiffUtilCallBack
import ru.campus.feature_start.presentation.adapter.holder.StartViewHolder

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 13.05.2022 20:00
 */

class StartAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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