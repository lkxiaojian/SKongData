package com.zky.zky_questionnaire.view

import android.content.Context
import android.util.AttributeSet
import android.widget.RadioGroup
import androidx.appcompat.widget.AppCompatRadioButton


/**
 *create_time : 21-3-22 下午3:56
 *author: lk
 *description： SingTopRadioButton
 */
class SingTopRadioButton(context: Context?, attrs: AttributeSet?) : AppCompatRadioButton(
    context,
    attrs
) {

    override fun toggle() {
        isChecked = !isChecked
        if (!isChecked) {
            (parent as RadioGroup).clearCheck()
        }
    }
}