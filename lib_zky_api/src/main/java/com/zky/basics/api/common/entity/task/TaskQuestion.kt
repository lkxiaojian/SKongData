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
    var q_index: Int? = -1
        set(value) {
            field = value
            notifyPropertyChanged(BR.q_index)
        }
    var is_nullable: Int? = -1
    var q_code: String? = ""
    @get:Bindable
    @set:Bindable
    var q_title: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.q_title)
        }
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
    var display: Boolean? = true
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
    var selectValue:String= ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.selectValue)
        }
}