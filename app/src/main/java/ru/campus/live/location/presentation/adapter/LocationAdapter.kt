package ru.campus.live.location.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.campus.live.core.presentation.ui.MyOnClick
import ru.campus.live.databinding.ItemLocationBinding
import ru.campus.live.location.presentation.adapter.diff.LocationDiffUtilCallBack
import ru.campus.live.location.presentation.adapter.holder.LocationViewHolder
import ru.campus.live.location.data.model.LocationModel

class LocationAdapter(private val myOnClick: MyOnClick<LocationModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val model = ArrayList<LocationModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding =
            ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationViewHolder(itemBinding, myOnClick)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as LocationViewHolder).bind(model[position])
    }

    override fun getItemCount(): Int {
        return model.size
    }

    fun setData(newModel: List<LocationModel>) {
        val result =
            DiffUtil.calculateDiff(LocationDiffUtilCallBack(model,
                newModel as ArrayList<LocationModel>))
        model.clear()
        model.addAll(newModel)
        result.dispatchUpdatesTo(this)
    }

}