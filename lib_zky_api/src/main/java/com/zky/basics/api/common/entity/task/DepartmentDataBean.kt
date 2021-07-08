package com.zky.basics.api.common.entity.task

import android.os.Parcel
import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.zky.basics.api.BR

/**
 *create_time : 2021/6/3 下午4:09
 *author: lk
 *description： DepartmentDataBean
 */
data class DepartmentDataBean(
    var datasetCode: String,
    var taskCode: String,
    var datasetName: String
)

data class DepartmentDataList(
    var tableCode: String,
    var tableDisplayName: String,
    var tableName: String,
    var tableRows: String?,
    var dataList: ArrayList<Any>?,
    var importDateList: ArrayList<String>?,
    var valueList: ArrayList<ArrayList<KeyAndValue>>
) : BaseObservable() {
    @get:Bindable
    @set:Bindable
    var dataTime:String=""
        set(value) {
            field = value
            notifyPropertyChanged(BR.dataTime)
        }

    @get:Bindable
    @set:Bindable
    var titleBName: String = ""
        get() {
//            return "$tableDisplayName-$tableName"
            return "$tableDisplayName"

        }
        set(value) {
            field = value
            notifyPropertyChanged(BR.titleBName)
        }


    @get:Bindable
    @set:Bindable
    var showRv: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.showRv)
        }
}


data class KeyAndValue(var key: String?, var value: String?): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(key)
        parcel.writeString(value)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<KeyAndValue> {
        override fun createFromParcel(parcel: Parcel): KeyAndValue {
            return KeyAndValue(parcel)
        }
        override fun newArray(size: Int): Array<KeyAndValue?> {
            return arrayOfNulls(size)
        }
    }

}