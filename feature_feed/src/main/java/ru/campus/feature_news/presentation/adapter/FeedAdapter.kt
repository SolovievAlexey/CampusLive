package ru.campus.feature_news.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.campus.core.data.DomainDataStore
import ru.campus.core.presentation.MyOnClick
import ru.campus.feature_news.data.FeedModel
import ru.campus.feature_news.data.FeedViewType
import ru.campus.feature_news.databinding.ItemMessageBinding
import ru.campus.feature_news.databinding.ItemNotAvailableBinding
import ru.campus.feature_news.presentation.adapter.diff.FeedDiffUtilCallBack
import ru.campus.feature_news.presentation.adapter.holder.MessageViewHolder
import ru.campus.feature_news.presentation.adapter.holder.NotAvailableViewHolder

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 15.05.2022 18:53
 */

class FeedAdapter(
    private val domainDataStore: DomainDataStore,
    private val myOnClick: MyOnClick<FeedModel>
    ): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val model = ArrayList<FeedModel>()

    override fun getItemViewType(position: Int): Int {
        return model[position].viewType.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == FeedViewType.PUBLICATION.ordinal) {
            val itemBinding =
                ItemMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            MessageViewHolder(itemBinding, myOnClick, domainDataStore)
        } else {
            val itemBinding =
                ItemNotAvailableBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            NotAvailableViewHolder(itemBinding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(model[position].viewType == FeedViewType.PUBLICATION) {
            (holder as MessageViewHolder).bind(model[position])
        } else {
            (holder as NotAvailableViewHolder).bind()
        }
    }

    override fun getItemCount(): Int {
        return model.size
    }

    fun setData(newModel: ArrayList<FeedModel>) {
        val result =
            DiffUtil.calculateDiff(FeedDiffUtilCallBack(model, newModel))
        model.clear()
        model.addAll(newModel)
        result.dispatchUpdatesTo(this)
    }

}