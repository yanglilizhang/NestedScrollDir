package com.stone.persistent.widget

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager


class GridItemDecoration(divider: Float) : RecyclerView.ItemDecoration() {

    private val divider = divider.toInt()

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val layoutParams = view.layoutParams as StaggeredGridLayoutManager.LayoutParams
        val spanIndex = layoutParams.spanIndex //可以获取item在span中的下标
        /**
         * 根据params.getSpanIndex()来判断左右边确定分割线
         * 第一列设置左边距为space，右边距为space/2  （第二列反之）
         */
        if (spanIndex == 0) {
            outRect.left = divider
            outRect.right = divider / 2
        } else {
            outRect.right = divider
            outRect.left = divider / 2
        }

//        if (params.getSpanIndex() % 2 == 0) {
//            outRect.left = space;
//            outRect.right = space / 2;
//        } else {
//            outRect.left = space / 2;
//            outRect.right = space;
//        }


        outRect.bottom = divider
        outRect.top = if (layoutParams.viewAdapterPosition < 2) {
            divider
        } else {
            0
        }
    }
}