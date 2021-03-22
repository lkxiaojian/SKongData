package com.zky.zky_questionnaire

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.zky.basics.api.BR

/**
 *create_time : 21-3-22 下午1:47
 *author: lk
 *description： TestData
 */
class TestData : BaseObservable() {
    @get:Bindable
    @set:Bindable
    var message=""
        set(value) {
            field = value
            notifyPropertyChanged(BR.message)
        }

    @get:Bindable
    @set:Bindable
    var selectValue: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.selectValue)
        }


}