package com.zky.zky_questionnaire.view

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.BindingAdapter
import com.zky.zky_questionnaire.inter.Co.itemChange

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
     itemChange?.getIndex(position)
    }


    companion object {
        private var position:Int?=-1
        @BindingAdapter(value = ["dataValue"], requireAll = false)
        @JvmStatic
        fun setListContent(
            view: CustomEditTextView,
            dataValue: Int
        ) {
            position=dataValue
        }

        @BindingAdapter(value = ["contentType"], requireAll = false)
        @JvmStatic
        fun setType(
            view: CustomEditTextView,
            type: String
        ) {
            if("input_number"==type){
                view.inputType= InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            }
        }


    }


}