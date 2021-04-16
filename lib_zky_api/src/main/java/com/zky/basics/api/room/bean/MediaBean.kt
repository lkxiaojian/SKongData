package com.zky.basics.api.room.bean

import android.os.Parcel
import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zky.basics.annotation.AllowNoArg
import com.zky.basics.api.BR
import java.io.Serializable

/**
 *create_time : 21-3-5 上午9:45
 *author: lk
 *description： MediaBean
 *   @ColumnInfo(name = "user_name")
 */

@AllowNoArg
data class MediaBean(
    var code: String,
    var file_type: String,
    @Bindable
    var file_path: String,
    var file_name: String,
    var create_data: String,
    var isupload: Boolean,
    var videoImagePath: String?,
    var user_code: String?,
    var uploader: String? = "",
    var mediaType2:String,
    var mediaType3:String,
) : Parcelable, BaseObservable() {

    //true 上传 false 缓存
    @get:Bindable
    @set:Bindable
    var upload: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.upload)
        }

    @get:Bindable
    @set:Bindable
    var filePathIsNUll: Boolean = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.filePathIsNUll)
        }


    @get:Bindable
    @set:Bindable
    var check: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.check)
        }


    @get:Bindable
    @set:Bindable
    var startIng: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.startIng)
        }


    @get:Bindable
    @set:Bindable
    var lastTime: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.lastTime)
        }

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(code)
        parcel.writeString(file_type)
        parcel.writeString(file_path)
        parcel.writeString(file_name)
        parcel.writeString(create_data)
        parcel.writeByte(if (isupload) 1 else 0)
        parcel.writeString(videoImagePath)
        parcel.writeString(user_code)
        parcel.writeString(uploader)
        parcel.writeString(mediaType2)
        parcel.writeString(mediaType3)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MediaBean> {
        override fun createFromParcel(parcel: Parcel): MediaBean {
            return MediaBean(parcel)
        }

        override fun newArray(size: Int): Array<MediaBean?> {
            return arrayOfNulls(size)
        }
    }


}