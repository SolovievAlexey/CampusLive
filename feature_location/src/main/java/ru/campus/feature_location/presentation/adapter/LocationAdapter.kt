package ru.campus.feature_location.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.campus.core.presentation.MyOnClick
import ru.campus.feature_location.data.LocationModel
import ru.campus.feature_location.databinding.ItemLocationBinding

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 14.05.2022 17:30
 */

internal class LocationAdapter(
    private val myOnClick: MyOnClick<LocationModel>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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

    fun setData(newModel: ArrayList<LocationModel>) {
        val result =
            DiffUtil.calculateDiff(LocationDiffUtilCallBack(model, newModel))
        model.clear()
        model.addAll(newModel)
        result.dispatchUpdatesTo(this)
    }

}