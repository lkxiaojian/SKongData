package com.zky.basics.common.view

import android.graphics.drawable.BitmapDrawable
import android.util.DisplayMetrics
import android.view.*
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.zky.basics.common.R

class TipMessagePop(
    mView: View?,
    context: AppCompatActivity
) : PopupWindow() {
    init {
        val view: View = LayoutInflater.from(context).inflate(R.layout.tip_pop, null)
        contentView = view
        width = ViewGroup.LayoutParams.WRAP_CONTENT
        val lp: WindowManager.LayoutParams = context.window
            .attributes
//        lp.alpha = 0.4f
        context.window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        context.window.attributes = lp
        val dm = DisplayMetrics()
        context.windowManager.defaultDisplay.getMetrics(dm)
//        val v1 = (dm.heightPixels * 0.6).toInt()
        height = ViewGroup.LayoutParams.WRAP_CONTENT
        isFocusable = true
        setBackgroundDrawable(BitmapDrawable())
        isTouchable = true
        isOutsideTouchable = true
        val viewById = view.findViewById<TextView>(R.id.atv_b)


        viewById.setOnClickListener { v: View? -> dismiss() }
        showAtLocation(mView, Gravity.CENTER, 0, 0)
        setOnDismissListener {
            val lp1: WindowManager.LayoutParams = context.window
                .attributes
            lp1.alpha = 1f
            context.window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            context.window.attributes = lp1
        }
    }
}