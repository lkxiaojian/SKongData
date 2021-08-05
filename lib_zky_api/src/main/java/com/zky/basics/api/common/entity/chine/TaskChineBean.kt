package com.zky.basics.api.common.entity.chine

import android.os.Parcel
import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.zky.basics.api.BR
import com.zky.basics.api.room.bean.MediaBean

/**
 * @Description:     java类作用描述
 * @Author:         lk
 * @CreateDate:     2021/7/30 17:10
 */
class TaskChineBean(var totalNum: Int, var pageList: ArrayList<TaskChineItemBean>?)


class TaskChineItemBean(
    var code: String?, var parentCode: String?,
    var userCode: String?, var userName: String?,
    var type: String?, var content: String?,
    var longitude: String?, var latitude: String?,
    var address: String?, var createDate: String?,
    var status: Int,
    var fileList: List<ChineMedia>?
)

class ChineMedia(
    var code: String,
    var relationCode: String?,
    var fileCategory: String?,
    var fileType: String?,
    var fileName: String,
    var filePath:String,
    var createDate:String

):Parcelable{
    constructor(parcel: Parcel) : this(
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
        parcel.writeString(code)
        parcel.writeString(relationCode)
        parcel.writeString(fileCategory)
        parcel.writeString(fileType)
        parcel.writeString(fileName)
        parcel.writeString(filePath)
        parcel.writeString(createDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ChineMedia> {
        override fun createFromParcel(parcel: Parcel): ChineMedia {
            return ChineMedia(parcel)
        }

        override fun newArray(size: Int): Array<ChineMedia?> {
            return arrayOfNulls(size)
        }
    }


}

class SelectPeople(
    var code: String?,
    var address: String?,
    var phone: String?,
    var attr_name: String?,
    var username: String?
) : BaseObservable(), Parcelable {


    @get:Bindable
    @set:Bindable
    var check: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.check)
        }


    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(code)
        parcel.writeString(address)
        parcel.writeString(phone)
        parcel.writeString(attr_name)
        parcel.writeString(username)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SelectPeople> {
        override fun createFromParcel(parcel: Parcel): SelectPeople {
            return SelectPeople(parcel)
        }

        override fun newArray(size: Int): Array<SelectPeople?> {
            return arrayOfNulls(size)
        }
    }

}