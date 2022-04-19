package ru.campus.live.discussion.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.campus.live.core.presentation.ui.MyOnClick
import ru.campus.live.databinding.*
import ru.campus.live.discussion.presentation.adapter.diff.DiscussionDiffUtilCallBack
import ru.campus.live.discussion.presentation.adapter.holder.*
import ru.campus.live.discussion.data.model.DiscussionModel
import ru.campus.live.discussion.data.model.DiscussionViewType

class DiscussionAdapter(
    private val myOnClick: MyOnClick<DiscussionModel>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val model = ArrayList<DiscussionModel>()

    override fun getItemViewType(position: Int): Int {
        return model[position].type.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (DiscussionViewType.values()[viewType]) {
            DiscussionViewType.PARENT -> {
                val itemBinding =
                    ItemParentCommetBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    )
                return ParentCommentViewHolder(itemBinding, myOnClick)
            }

            DiscussionViewType.CHILD -> {
                val itemBinding =
                    ItemChildCommentBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    )
                return ChildCommentViewHolder(itemBinding, myOnClick)
            }

            DiscussionViewType.PARENT_SHIMMER -> {
                val itemBinding =
                    ItemParentCommentShimmerBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    )
                return ParentCommentShimmerViewHolder(itemBinding)
            }

            DiscussionViewType.CHILD_SHIMMER -> {
                val itemBinding = ItemChildCommentShimmerBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
                return ChildCommentShimmerViewHolder(itemBinding)
            }

            DiscussionViewType.DISCUSSION_NONE -> {
                val itemBinding = ItemDiscussionNoneBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return CommentsNoneViewHolder(itemBinding)
            }

            else -> {
                val itemBinding =
                    ItemPublicationBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    )
                return DiscussionPublicationViewHolder(itemBinding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (model[position].type) {
            DiscussionViewType.PARENT -> (holder as ParentCommentViewHolder).bind(model[position])
            DiscussionViewType.CHILD -> (holder as ChildCommentViewHolder).bind(model[position])
            DiscussionViewType.PARENT_SHIMMER -> (holder as ParentCommentShimmerViewHolder)
            DiscussionViewType.CHILD_SHIMMER -> (holder as ChildCommentShimmerViewHolder)
            DiscussionViewType.DISCUSSION_NONE -> (holder as CommentsNoneViewHolder)
            else -> (holder as DiscussionPublicationViewHolder).bind(model[position])
        }
    }

    override fun getItemCount(): Int {
        return model.size
    }

    fun setData(newModel: ArrayList<DiscussionModel>) {
        val result =
            DiffUtil.calculateDiff(DiscussionDiffUtilCallBack(model, newModel))
        model.clear()
        model.addAll(newModel)
        result.dispatchUpdatesTo(this)
    }

}