package ru.campus.live.ribbon.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.campus.live.core.presentation.MyOnClick
import ru.campus.live.databinding.ItemFeedLocationBinding
import ru.campus.live.databinding.ItemPublicationBinding
import ru.campus.live.databinding.ItemRibbonMessageBinding
import ru.campus.live.ribbon.data.model.RibbonModel
import ru.campus.live.ribbon.data.model.RibbonViewType
import ru.campus.live.ribbon.presentation.adapter.diff.FeedDiffUtilCallBack
import ru.campus.live.ribbon.presentation.adapter.holders.RibbonErrorViewHolder
import ru.campus.live.ribbon.presentation.adapter.holders.RibbonLocationViewHolder
import ru.campus.live.ribbon.presentation.adapter.holders.RibbonViewHolder

class RibbonAdapter(private val myOnClick: MyOnClick<RibbonModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val model = ArrayList<RibbonModel>()

    override fun getItemViewType(position: Int): Int {
        return model[position].viewType.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (RibbonViewType.values()[viewType]) {
            RibbonViewType.LOCATION -> {
                val itemBinding =
                    ItemFeedLocationBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    )
                RibbonLocationViewHolder(itemBinding)
            }
            RibbonViewType.PUBLICATION -> {
                val itemBinding =
                    ItemPublicationBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    )
                RibbonViewHolder(itemBinding, myOnClick)
            }
            else -> {
                val itemBinding = ItemRibbonMessageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
                RibbonErrorViewHolder(itemBinding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (model[position].viewType) {
            RibbonViewType.LOCATION -> (holder as RibbonLocationViewHolder).bind(model[position])
            RibbonViewType.PUBLICATION -> (holder as RibbonViewHolder).bind(model[position])
            else -> (holder as RibbonErrorViewHolder).bind(model[position])
        }
    }

    override fun getItemCount(): Int {
        return model.size
    }

    fun setData(newModel: ArrayList<RibbonModel>) {
        val result =
            DiffUtil.calculateDiff(FeedDiffUtilCallBack(model, newModel))
        model.clear()
        model.addAll(newModel)
        result.dispatchUpdatesTo(this)
    }

}