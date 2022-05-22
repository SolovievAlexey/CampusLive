package ru.campus.feature_discussion.presentation.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.campus.core.presentation.MyOnClick
import ru.campus.feature_discussion.data.model.DiscussionModel
import ru.campus.feature_discussion.data.model.DiscussionViewType
import ru.campus.feature_discussion.presentation.adapter.holder.*
import ru.campus.feaure_discussion.databinding.*

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 21.05.2022 16:58
 */

class DiscussionAdapter(
    private val myOnClick: MyOnClick<DiscussionModel>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val model = ArrayList<DiscussionModel>()

    override fun getItemViewType(position: Int): Int {
        return model[position].type.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (DiscussionViewType.values()[viewType]) {
            DiscussionViewType.PARENT -> {
                val itemBinding = ItemParentBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false)
                ParentViewHolder(itemBinding, myOnClick)
            }

            DiscussionViewType.CHILD -> {
                val itemBinding = ItemChildBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false)
                ChildViewHolder(itemBinding, myOnClick)
            }

            DiscussionViewType.PARENT_SHIMMER -> {
                val itemBinding = ItemParentShimmerBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false)
                ParentShimmerViewHolder(itemBinding)
            }

            DiscussionViewType.PUBLICATION -> {
                val itemBinding = ItemPublicationBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false)
                PublicationViewHolder(itemBinding)
            }

            else -> {
                val itemBinding = ItemChildShimmerBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false)
                ChildShimmerViewHolder(itemBinding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("MyLog", "ViewType = "+model[position].type)
        return when (model[position].type) {
            DiscussionViewType.PARENT -> (holder as ParentViewHolder).bind(model[position])
            DiscussionViewType.CHILD -> (holder as ChildViewHolder).bind(model[position])
            DiscussionViewType.PARENT_SHIMMER -> (holder as ParentShimmerViewHolder).bind()
            DiscussionViewType.PUBLICATION -> (holder as PublicationViewHolder).bind(model[position])
            else -> (holder as ChildShimmerViewHolder).bind()
        }
    }

    override fun getItemCount(): Int {
        return model.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newModel: ArrayList<DiscussionModel>) {
        model.clear()
        model.addAll(newModel)
        notifyDataSetChanged()
    }

}