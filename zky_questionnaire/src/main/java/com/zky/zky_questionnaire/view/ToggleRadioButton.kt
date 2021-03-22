package com.zky.zky_questionnaire.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.CompoundButton
import android.widget.RadioGroup
import androidx.appcompat.widget.AppCompatRadioButton
import com.zky.zky_questionnaire.R

/**
 *create_time : 21-3-22 上午11:06
 *author: lk
 *description： ToggleRadioButton
 */
class ToggleRadioButton(context: Context?, attrs: AttributeSet?) : AppCompatRadioButton(
    context,
    attrs
),
    CompoundButton.OnCheckedChangeListener {


    override fun toggle() {
//        super.toggle()
        isChecked = !isChecked
        if (!isChecked) {
            (parent as RadioGroup).clearCheck();
        }
    }


    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {

        Log.e("", "")

    }


}