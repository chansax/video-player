package com.chansax.videoplayer.adapter

import androidx.recyclerview.widget.RecyclerView
import android.R.attr.right
import android.R.attr.left
import android.graphics.Rect
import android.view.View


/**
 * Created by chandan on 2019-08-31.
 */

class GridItemDecoration(var space: Int = 0) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        outRect.left = space
        outRect.right = space
        outRect.bottom = space

        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.top = space
        } else {
            outRect.top = 0
        }
    }
}