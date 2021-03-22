package com.zky.zky_questionnaire.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.BindingAdapter
import com.zky.zky_questionnaire.TestData

/**
 *create_time : 21-3-22 下午5:41
 *author: lk
 *description： CustomEditTextView
 */
class CustomEditTextView(context: Context, attrs: AttributeSet?) :
    AppCompatEditText(context, attrs) {
    var flag = false

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        if (!flag) {
            flag = true
            return
        }
        TestData.itemChange?.getItem(null)

    }


    companion object {
        @BindingAdapter(value = ["itemCode"], requireAll = false)
        @JvmStatic
        fun setListContent(
            view: CustomEditTextView,
            itemCode: String
        ) {

        }


    }


}