package ru.campus.feature_support

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.campus.feature_support.databinding.ItemRulesBinding

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 22.05.2022 21:56
 */

class RulesAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val model = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding = ItemRulesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RulesViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RulesViewHolder).bind(model[position])
    }

    override fun getItemCount(): Int {
        return model.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: ArrayList<String>) {
        model.clear()
        model.addAll(newData)
        notifyDataSetChanged()
    }

}