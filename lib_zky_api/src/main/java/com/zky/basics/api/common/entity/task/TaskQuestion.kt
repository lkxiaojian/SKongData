package com.zky.basics.api.common.entity.task

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.zky.basics.api.BR

/**
 *create_time : 21-3-23 下午12:00
 *author: lk
 *description： TaskQuestion
 */
class TaskQuestion : BaseObservable() {
    @get:Bindable
    @set:Bindable
    var q_index: Int? = -1 // 下标
        set(value) {
            field = value
            notifyPropertyChanged(BR.q_index)
        }

    @get:Bindable
    @set:Bindable
    var nullable: Int? = -1  // 1 不能为空  2 能为空
        set(value) {
            field = value
            notifyPropertyChanged(BR.nullable)
        }

    //题编号
    var q_code: String? = ""

    @get:Bindable
    @set:Bindable
    var q_title: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.q_title)
        }

    //题的类型 radio chebox
    @get:Bindable
    @set:Bindable
    var q_type: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.q_type)
        }

    @get:Bindable
    @set:Bindable
    var q_option: List<String>? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.q_option)
        }

    @get:Bindable
    @set:Bindable
    var display: Boolean? = true // true 显示  false 不显示
        set(value) {
            field = value
            notifyPropertyChanged(BR.display)
        }

    @get:Bindable
    @set:Bindable
    var tipSelect = "请选择"
        set(value) {
            field = value
            notifyPropertyChanged(BR.tipSelect)
        }


    @get:Bindable
    @set:Bindable
    var answer: String? = "" //选择的答案
        set(value) {
            field = value
            notifyPropertyChanged(BR.answer)
        }
}