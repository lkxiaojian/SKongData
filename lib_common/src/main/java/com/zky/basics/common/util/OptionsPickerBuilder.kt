package com.zky.basics.common.util

import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.TimePickerView
import com.zky.basics.common.R
import views.ViewOption.OptionsPickerBuilder
import java.util.*

/**
 *create_time : 21-3-23 上午9:21
 *author: lk
 *description： OptionsPickerBuilder
 */
class OptionsPickerBuilder {


    fun pickerBuilder(context: Context): OptionsPickerBuilder? = initPicker(context)


    fun pickerBuilderTime(
        context: Context,
        type: String?,
        listener: OnTimeSelectListener
    ): TimePickerView? =
        initPickerTime(context, type, listener)

    private fun initPickerTime(
        context: Context,
        type: String?,
        listener: OnTimeSelectListener
    ): TimePickerView? {

        val selectedDate = Calendar.getInstance()
        val startDate = Calendar.getInstance()
        startDate[1900, 1] = 1
        val endDate = Calendar.getInstance()
        endDate[2050, 1] = 1
        val booleans = booleanArrayOf(true, true, true, false, false, false)
        //时间选择器
        return TimePickerBuilder(context) { date, v ->
            listener.onTimeSelect(date, v)
        }
            .setCancelText("取消")//取消按钮文字
            .setSubmitText("确定")//确认按钮文字
            .setType(booleans)
            .setLabel("年", "月", "日", "", "", "")
            .isCenterLabel(true)
            .setDividerColor(Color.DKGRAY)
            .setDate(selectedDate)
            .setRangDate(startDate, endDate)
            .setDecorView(null)
            .build()
    }


    private fun initPicker(application: Context): OptionsPickerBuilder? {

        return OptionsPickerBuilder(application)
            .setCancelText("取消")
            .setCancelColor(ContextCompat.getColor(application, R.color.color_b0b0b0))
            .setSubCalSize(16)
            .setSubmitColor(ContextCompat.getColor(application, R.color.color_4a90e2))
            .setSubmitText("确定")
            .setContentTextSize(20) //滚轮文字大小
            .setTextColorCenter(
                ContextCompat.getColor(
                    application,
                    R.color.color_333
                )
            ) //设置选中文本的颜色值
            .setLineSpacingMultiplier(2f) //行间距
            .setDividerColor(
                ContextCompat.getColor(
                    application,
                    R.color.color_f5f5f5
                )
            )
    }


}