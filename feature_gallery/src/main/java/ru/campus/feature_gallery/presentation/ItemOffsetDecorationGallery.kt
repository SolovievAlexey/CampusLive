package ru.campus.feature_gallery.presentation

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Soloviev Alexey
 * @contacts soloviev@internet.ru
 * @date 15.05.2022 23:19
 */

class ItemOffsetDecorationGallery(private val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State,
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val params = view.layoutParams as GridLayoutManager.LayoutParams
        outRect.left = space;
        outRect.right = 0;
        outRect.bottom = space;
    }

}