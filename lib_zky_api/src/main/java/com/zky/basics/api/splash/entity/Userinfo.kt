package com.zky.basics.api.splash.entity

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by lk
 * Date 2019-11-11
 * Time 14:01
 * Detail:
 */
  class Userinfo(
    var code: String? = null,
    var provinceCode: String? = null,
    var cityCode: String? = null,
    var countyCode: String? = null,
    var townCode: String? = null,
    var villageCode: String? = null,
    var idcard: String? = null,
    var password: String? = null,
    var username: String ?= "",
    var accountLevel: Int = 0,
    var accountStatus: Int = 0,
    var isAdmin: String? = null,
    var deptName: String? = null,
    var job: String? = null,
    var phone: String? = null,
    var headImgPath: String? = null,
    var remark: String? = null,
    var createDate: String? = null,
    var token: String? = null,
    var province:String?="",
    var city:String?="",
    var county:String?="",
    var town:String?="",
    var village:String?=""
):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
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
        parcel.writeString(code)
        parcel.writeString(provinceCode)
        parcel.writeString(cityCode)
        parcel.writeString(countyCode)
        parcel.writeString(townCode)
        parcel.writeString(villageCode)
        parcel.writeString(idcard)
        parcel.writeString(password)
        parcel.writeString(username)
        parcel.writeInt(accountLevel)
        parcel.writeInt(accountStatus)
        parcel.writeString(isAdmin)
        parcel.writeString(deptName)
        parcel.writeString(job)
        parcel.writeString(phone)
        parcel.writeString(headImgPath)
        parcel.writeString(remark)
        parcel.writeString(createDate)
        parcel.writeString(token)
        parcel.writeString(province)
        parcel.writeString(city)
        parcel.writeString(county)
        parcel.writeString(town)
        parcel.writeString(village)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Userinfo> {
        override fun createFromParcel(parcel: Parcel): Userinfo {
            return Userinfo(parcel)
        }

        override fun newArray(size: Int): Array<Userinfo?> {
            return arrayOfNulls(size)
        }
    }


}


