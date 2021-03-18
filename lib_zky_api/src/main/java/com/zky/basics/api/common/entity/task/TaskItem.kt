package com.zky.basics.api.common.entity.task

import android.os.Parcel
import android.os.Parcelable
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
): BaseObservable() , Parcelable {
    var fData: TaskBean? = null

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
        fData = parcel.readParcelable(TaskBean::class.java.classLoader)
    }
//    @get:Bindable
//    @set:Bindable
//    var searchMessage: String = ""
//        set(value) {
//            field = value
//            notifyPropertyChanged(BR.searchMessage)
//        }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(taskCode)
        parcel.writeString(itemCode)
        parcel.writeString(dataAttr1)
        parcel.writeString(dataAttr2)
        parcel.writeString(dataAttr3)
        parcel.writeString(collectStatus)
        parcel.writeParcelable(fData, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TaskItem> {
        override fun createFromParcel(parcel: Parcel): TaskItem {
            return TaskItem(parcel)
        }

        override fun newArray(size: Int): Array<TaskItem?> {
            return arrayOfNulls(size)
        }
    }

}