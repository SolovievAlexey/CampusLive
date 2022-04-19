package ru.campus.live.gallery.presentation.adapter.holder

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.campus.live.R
import ru.campus.live.core.presentation.MyOnClick
import ru.campus.live.databinding.ItemAttachmentBinding
import ru.campus.live.gallery.data.model.UploadMediaObject

class UploadViewHolder(
    private val itemBinding: ItemAttachmentBinding,
    private val myOnClick: MyOnClick<UploadMediaObject>
) :
    RecyclerView.ViewHolder(itemBinding.root) {

    private val context = itemBinding.root.context

    fun bind(modelUpload: UploadMediaObject) {
        Glide.with(context).load(modelUpload.fullPath).into(itemBinding.imageView)
        itemBinding.shadow.isVisible = modelUpload.error || modelUpload.upload
        itemBinding.progressBar.isVisible = modelUpload.upload
        itemBinding.close.isVisible = !modelUpload.upload
        itemBinding.error.isVisible = modelUpload.error
        if (modelUpload.animation) {
            val animShake: Animation = AnimationUtils.loadAnimation(context, R.anim.shaking)
            itemBinding.imageView.startAnimation(animShake)
        }
        itemBinding.close.setOnClickListener {
            myOnClick.item(itemBinding.close, modelUpload)
        }
    }

}