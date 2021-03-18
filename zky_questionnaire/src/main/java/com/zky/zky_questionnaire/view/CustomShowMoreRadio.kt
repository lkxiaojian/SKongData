package com.zky.zky_questionnaire.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.databinding.BindingAdapter
import com.zky.basics.common.util.DisplayUtil
import com.zky.zky_questionnaire.R


/**
 *create_time : 21-3-17 下午2:55
 *author: lk
 *description： CustomShowMoreRadio
 */
class CustomShowMoreRadio : RadioGroup, View.OnClickListener {
    private var marTop = DisplayUtil.dip2px(10f)
    private var maxChildHeight = 0
    private var minChildHeight = 0
    private var currentView: View? = null

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {


    }

    constructor(context: Context?) : super(context) {

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (childCount > 0) {

            var heig = getChildAt(0).measuredHeight
            var top = 0
            for (i: Int in 0 until childCount) {
                val v = getChildAt(i)
                val height = v.measuredHeight

                if (i > 0) {
                    if (i == childCount - 1) {

                        heig += (height + marTop)

                    } else {
                        heig += (height + top + marTop)
//                        top += (getChildAt(i - 1).measuredHeight + marTop)
                    }

                    top += (getChildAt(i - 1).measuredHeight + marTop)
                }

                v.layout(0, top, v.measuredWidth, heig)
            }
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)


        for (i in 0 until childCount) {
            val childAt = getChildAt(i)
            measureChild(childAt, widthMeasureSpec, heightMeasureSpec)
        }
        var height = 0
        if (childCount > 0) {
            for (i in 0 until childCount) {
                val childAt = getChildAt(i)
                childAt.setOnClickListener(this)
                val measuredHeight = childAt.measuredHeight
                height += measuredHeight + marTop
            }
            setMeasuredDimension(width, height)
        }
    }

    companion object {
        private val viewList = arrayListOf<RadioButton>()

        @BindingAdapter(value = ["listContent"], requireAll = false)
        @JvmStatic
        fun setListContent(view: RadioGroup, listContent: String) {

            var listContent =
                "选择题选项每一选项项每一选项项每一选项项每一选项项每一选项项每一选项占一sdasdsadasdasdasdasdasdasdsadas行选,11择题选项每一选项项每一选项项每一选项项每一选项项每一选项项每一选项项行,选择题选项每一选项占一行"
            val split = listContent.split(",")

            split.forEach {
                val v: View = LayoutInflater.from(view.context).inflate(R.layout.radio_cus, null)
                if (v !is RadioButton) return@forEach
                v.text = it
                view.addView(v)
            }
            view.invalidate()
        }
    }

    override fun onClick(v: View?) {
        if (v is RadioButton) {

            Log.e("", "")
        }

    }


}