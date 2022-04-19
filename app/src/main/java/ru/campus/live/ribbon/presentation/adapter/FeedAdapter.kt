package ru.campus.live.ribbon.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.campus.live.core.presentation.ui.MyOnClick
import ru.campus.live.databinding.ItemFeedInviteBinding
import ru.campus.live.databinding.ItemFeedLocationBinding
import ru.campus.live.databinding.ItemPublicationBinding
import ru.campus.live.ribbon.presentation.adapter.diff.FeedDiffUtilCallBack
import ru.campus.live.ribbon.presentation.adapter.holders.FeedInviteViewHolder
import ru.campus.live.ribbon.presentation.adapter.holders.FeedLocationViewHolder
import ru.campus.live.ribbon.presentation.adapter.holders.FeedPublicationViewHolder
import ru.campus.live.ribbon.data.model.RibbonModel
import ru.campus.live.ribbon.data.model.RibbonViewType

class FeedAdapter(private val myOnClick: MyOnClick<RibbonModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val model = ArrayList<RibbonModel>()

    override fun getItemViewType(position: Int): Int {
        return model[position].viewType.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (RibbonViewType.values()[viewType]) {
            RibbonViewType.HEADING -> {
                val itemBinding =
                    ItemFeedLocationBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    )
                FeedLocationViewHolder(itemBinding)
            }
            RibbonViewType.PUBLICATION -> {
                val itemBinding =
                    ItemPublicationBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    )
                FeedPublicationViewHolder(itemBinding, myOnClick)
            }
            else -> {
                val itemBinding = ItemFeedInviteBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
                FeedInviteViewHolder(itemBinding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (model[position].viewType) {
            RibbonViewType.HEADING -> (holder as FeedLocationViewHolder).bind(model[position])
            RibbonViewType.PUBLICATION -> (holder as FeedPublicationViewHolder).bind(model[position])
            else -> (holder as FeedInviteViewHolder).bind()
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