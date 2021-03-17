package com.zky.basics.api.common.entity.task

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.zky.basics.api.BR

/**
 *create_time : 21-3-17 上午10:56
 *author: lk
 *description： TaskItem
 */
data class TaskItem(
    var taskCode: String?,
    var itemCode: String?,
    var dataAttr1: String?,
    var dataAttr2: String?,
    var dataAttr3: String?,
    var collectStatus: String?
): BaseObservable() {
    var fData: TaskBean? = null
//    @get:Bindable
//    @set:Bindable
//    var searchMessage: String = ""
//        set(value) {
//            field = value
//            notifyPropertyChanged(BR.searchMessage)
//        }

}