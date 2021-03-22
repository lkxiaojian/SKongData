package com.zky.basics.api.splash.entity

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.zky.basics.api.BR

/**
 *create_time : 21-3-5 下午3:10
 *author: lk
 *description： AccountLevel
 * "attr_idx": 3,
"attr_name": "县级",
"attr_tip": "请选择县",
"attr": "county"
 */
data class AccountLevel(
    @Bindable var attr_name: String?,
    @Bindable var attr_tip: String?,
    @Bindable var attr: String?,
    @Bindable var value: String?,
    @Bindable var attr_code: String?,
    @Bindable var valueCode: String?
) : BaseObservable() {
    @get:Bindable
    @set:Bindable
    var attr_idx: Int = -1
        set(value) {
            field = value
            notifyPropertyChanged(BR.attr_idx)
        }


}