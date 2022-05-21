package ru.campus.feature_discussion.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.campus.core.presentation.MyOnClick
import ru.campus.feature_discussion.data.model.DiscussionModel
import ru.campus.feature_discussion.data.model.DiscussionViewType
import ru.campus.feature_discussion.presentation.adapter.holder.ChildViewHolder
import ru.campus.feature_discussion.presentation.adapter.holder.ParentViewHolder
import ru.campus.feaure_discussion.databinding.ItemChildBinding
import ru.campus.feaure_discussion.databinding.ItemParentBinding

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
        return if (DiscussionViewType.values()[viewType] == DiscussionViewType.PARENT) {
            val itemBinding = ItemParentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)
            ParentViewHolder(itemBinding, myOnClick)
        } else {
            val itemBinding = ItemChildBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)
            ChildViewHolder(itemBinding, myOnClick)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (model[position].type == DiscussionViewType.PARENT) {
            (holder as ParentViewHolder).bind(model[position])
        } else {
            (holder as ChildViewHolder).bind(model[position])
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