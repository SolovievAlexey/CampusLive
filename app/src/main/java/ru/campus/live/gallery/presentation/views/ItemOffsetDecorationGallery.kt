package ru.campus.live.gallery.presentation.views

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ItemOffsetDecorationGallery(private val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val params = view.layoutParams as GridLayoutManager.LayoutParams
        outRect.left = space;
        outRect.right = 0;
        outRect.bottom = space;
    }
}