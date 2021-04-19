package com.zky.basics.common.view

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.zhihu.matisse.internal.utils.UIUtils
import me.jessyan.autosize.utils.AutoSizeUtils.dp2px

/**
 *create_time : 21-4-19 上午11:49
 *author: lk
 *description： MarginDecorationextends
 */
class MarginDecoration(context: Context): RecyclerView.ItemDecoration() {
    private var margin = dp2px(context, 10f)



    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
//        super.getItemOffsets(outRect, view, parent, state)
        if (parent.getChildLayoutPosition(view) % 3 == 0) {
            outRect.set(margin, margin, margin, 0);
        } else {
            outRect.set(0, margin, margin, 0);
        }
    }
}