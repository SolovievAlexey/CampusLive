package ru.campus.file_upload.presentation

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.campus.core.data.UploadMediaModel
import ru.campus.core.presentation.MyOnClick
import ru.campus.file_upload.R
import ru.campus.file_upload.databinding.ItemUploadBinding

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 17.05.2022 23:37
 */

class UploadMediaViewHolder(
    private val itemBinding: ItemUploadBinding,
    private val myOnClick: MyOnClick<UploadMediaModel>
) : RecyclerView.ViewHolder(itemBinding.root) {

    private val context = itemBinding.root.context

    fun bind(model: UploadMediaModel) {
        Glide.with(context).load(model.fullPath).into(itemBinding.imageView)
        itemBinding.shadow.isVisible = model.error || model.upload
        itemBinding.progressBar.isVisible = model.upload
        itemBinding.close.isVisible = !model.upload
        itemBinding.error.isVisible = model.error
        if (model.animation) {
            val animShake: Animation = AnimationUtils.loadAnimation(context, R.anim.shaking)
            itemBinding.imageView.startAnimation(animShake)
        }
        itemBinding.close.setOnClickListener {
            myOnClick.item(itemBinding.close, model)
        }
    }

}