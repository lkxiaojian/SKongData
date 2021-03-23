package com.zky.zky_questionnaire

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.zky.basics.api.BR
import com.zky.zky_questionnaire.inter.itemChangeListener

/**
 *create_time : 21-3-22 下午1:47
 *author: lk
 *description： TestData
 */
class TestData : BaseObservable() {
    companion object {
        var itemChange: itemChangeListener? = null
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
    var message = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.message)
        }

    @get:Bindable
    @set:Bindable
    var selectValue:String= ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.selectValue)
        }


}