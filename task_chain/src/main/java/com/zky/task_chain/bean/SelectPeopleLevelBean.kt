package com.zky.task_chain.bean

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.zky.basics.api.BR

/**
 * @Description:     java类作用描述
 * @Author:         lk
 * @CreateDate:     2021/8/3 17:15
 */
class SelectPeopleLevelBean(var type: String, var hintText:String?,var data: Any?) : BaseObservable() {
    @get:Bindable
    @set:Bindable
    var value: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.value)
        }

    @get:Bindable
    @set:Bindable
    var valueCode: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.valueCode)
        }

    @get:Bindable
    @set:Bindable
    var attr: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.attr)
        }
}