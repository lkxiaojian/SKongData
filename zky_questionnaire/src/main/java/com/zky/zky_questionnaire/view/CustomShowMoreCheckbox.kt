package com.zky.zky_questionnaire.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.zky.basics.common.util.DisplayUtil
import com.zky.basics.common.util.spread.removeLastString
import com.zky.zky_questionnaire.R
import com.zky.zky_questionnaire.inter.Co.itemChange
import java.lang.Exception


/**
 *create_time : 21-3-17 下午2:55
 *author: lk
 *description： CustomShowMoreRadio
 */
class CustomShowMoreCheckbox : RadioGroup, CompoundButton.OnCheckedChangeListener {
    private var marTop = DisplayUtil.dip2px(7f)
    var listener: InverseBindingListener? = null

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
                    heig += (height + marTop)
                    top += (getChildAt(i - 1).measuredHeight + marTop)
                }

                v.layout(0, top, v.measuredWidth, heig)
            }

            if (!setSuucced) {
                setValue(value)
            }
        }


    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var height = 0
        for (i in 0 until childCount) {
            val childAt = getChildAt(i) as CheckBox
            measureChild(childAt, widthMeasureSpec,heightMeasureSpec)
            childAt.setOnCheckedChangeListener(this)
            val measuredHeight = childAt.measuredHeight
            height += (measuredHeight + marTop)
        }
        setMeasuredDimension(width, height+marTop)
    }

    private var value: String? = ""
    private var setSuucced = true
    fun setValue(v: String?) {
        try {
            if (!v.isNullOrEmpty()) {
                if (childCount > 0) {
                    setSuucced = true
                    val split = v.split(",")
                    for (i in 0 until childCount) {
                        split?.forEach {
                            if (i == it.toInt()) {
                                val childAt = getChildAt(i) as CheckBox
                                childAt.isChecked = true
                            }
                        }
                    }
                } else {
                    value = v
                    setSuucced = false
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    companion object {
        private var position: Int? = -1

        @BindingAdapter(value = ["listContent", "dataValue"], requireAll = false)
        @JvmStatic
        fun setListContent(
            view: CustomShowMoreCheckbox,
            listContent: List<String>,
            dataValue: Int?
        ) {
            if (view.childCount > 0) {
                return
            }
            position = dataValue
            listContent.forEach {
                val v: View = LayoutInflater.from(view.context).inflate(R.layout.checkbox_cus, null)
                if (v !is CheckBox) return@forEach
                v.text = it
                view.addView(v)
            }
            view.invalidate()
        }


        @BindingAdapter(value = ["selectValues"], requireAll = false)
        @JvmStatic
        fun setValue(view: CustomShowMoreCheckbox, value: String?) {
            view.setValue(value)
        }

        @InverseBindingAdapter(attribute = "selectValues", event = "valueOfSetsAttrChanged")
        @JvmStatic
        fun getValue(view: CustomShowMoreCheckbox): String {
            var va = ""
            for (i in 0 until view.childCount) {
                val checkBox = view.getChildAt(i) as CheckBox
                if (checkBox.isChecked) {
                    va += "$i,"
                }
            }
            if (va.isNotEmpty()) {
                va = va.removeLastString()
            }
            return va
        }

        @BindingAdapter(value = ["valueOfSetsAttrChanged"], requireAll = false)
        @JvmStatic
        fun setListener(view: CustomShowMoreCheckbox, listener: InverseBindingListener?) {
            view.listener = listener
        }

    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        listener?.onChange()
        itemChange?.getIndex(position)
    }


}