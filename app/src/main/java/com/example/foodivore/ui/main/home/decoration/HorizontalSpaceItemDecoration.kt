package com.example.foodivore.ui.main.home.decoration

import android.R.attr.spacing
import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView


class HorizontalSpaceItemDecoration(val context: Context, @DimenRes val spaceStartResId: Int, @DimenRes val spaceBetweenResId: Int) : RecyclerView.ItemDecoration() {
    private var startSpace: Int = context.resources.getDimensionPixelOffset(spaceStartResId)
    private var spacing: Int = context.resources.getDimensionPixelOffset(spaceBetweenResId)
    private var endSpaceCache = 0

    private fun isLastItem(view: View, parent: RecyclerView): Boolean {
        val adapterSize = parent.adapter!!.itemCount
        val pos = parent.getChildAdapterPosition(view)
        return pos == adapterSize - 1
    }

    private fun isFirstItem(view: View, parent: RecyclerView): Boolean {
        val pos = parent.getChildAdapterPosition(view)
        return pos == 0
    }

    private fun endSpacing(view: View, parent: RecyclerView): Int {
        if (endSpaceCache == 0) {
            val aw = parent.measuredWidth
            val vw: Int = view.measuredWidth
            if (vw != 0) {  // yes, happens
                endSpaceCache = (aw - vw + spacing) / 2
            }
        }
        return endSpaceCache
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        if (isFirstItem(view, parent)) {
            outRect.left = startSpace
        }
        if (isLastItem(view, parent)) {
            outRect.right = endSpacing(view, parent)
        } else {
            outRect.right = spacing
        }
    }
}