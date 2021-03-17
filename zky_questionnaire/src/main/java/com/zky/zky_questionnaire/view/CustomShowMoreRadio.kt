package com.zky.zky_questionnaire.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RelativeLayout
import androidx.databinding.BindingAdapter
import com.zky.zky_questionnaire.R


/**
 *create_time : 21-3-17 下午2:55
 *author: lk
 *description： CustomShowMoreRadio
 */
class CustomShowMoreRadio : ViewGroup {


    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        var listContent = "111,2,33"
        val split = listContent.split(",")
        split.forEach {
            val v: View = LayoutInflater.from(context).inflate(R.layout.radio_cus, null)
            if (v !is RadioButton) return@forEach
            val layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )
//            layoutParams.height=200
//            layoutParams.width=1000
//            layoutParams.topMargin=1000
            v.layoutParams = layoutParams
            v.text = it
            v.setOnClickListener {
                val radioButton = it as RadioButton
                val text = radioButton.text
                Log.e("","")
            }
            addView(v)

        }

    }

    constructor(context: Context?) : super(context) {

    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (childCount > 0) {
            var heig=0
            for (i: Int in 0 until childCount) {
                val v = getChildAt(i);
                heig+=v.measuredHeight+100
                v.layout(0, 0, v.measuredWidth,heig)
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        var width = MeasureSpec.getSize(widthMeasureSpec);
        var height = 0


        for (i in 0 until childCount) {
            val childAt = getChildAt(i)
            height += (150)
            measureChild(childAt, widthMeasureSpec, heightMeasureSpec);

        }
        setMeasuredDimension(width, height)

    }

    companion object {
        private val viewList = arrayListOf<RadioButton>()

        @BindingAdapter(value = ["listContent"], requireAll = false)
        @JvmStatic
        fun setListContent(viewGroup: ViewGroup, listContent: String) {
            return
            val split = listContent.split(",")
            split.forEach {
                val v: View =
                    LayoutInflater.from(viewGroup.context).inflate(R.layout.radio_cus, null)
                if (v !is RadioButton) return
                val params =
                    LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
                params.width = 400
                params.height = 400
                v.setLayoutParams(params);
//                v.text = it
                viewList.add(v)
                viewGroup.addView(v)
            }
            viewGroup.invalidate()
        }
    }


}