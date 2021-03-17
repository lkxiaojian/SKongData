package com.zky.basics.api.common.entity.task

import android.os.Parcel
import android.os.Parcelable

/**
 *create_time : 21-3-17 上午10:13
 *author: lk
 *description： TaskBean
 */
data class TaskBean(
    var taskCode: String?,
    var taskName: String?,
    var startDate: String?,
    var endDate: String?,
    var dataAttr1: String?,
    var dataAttr2: String?,
    var dataAttr3: String?,
    var spaceDataType: String?,
    var mediaDataType: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(taskCode)
        parcel.writeString(taskName)
        parcel.writeString(startDate)
        parcel.writeString(endDate)
        parcel.writeString(dataAttr1)
        parcel.writeString(dataAttr2)
        parcel.writeString(dataAttr3)
        parcel.writeString(spaceDataType)
        parcel.writeString(mediaDataType)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TaskBean> {
        override fun createFromParcel(parcel: Parcel): TaskBean {
            return TaskBean(parcel)
        }

        override fun newArray(size: Int): Array<TaskBean?> {
            return arrayOfNulls(size)
        }
    }

}