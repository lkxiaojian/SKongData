package com.zky.basics.main.mvvm.model

import android.app.Application
import com.zky.basics.api.RetrofitManager.Companion.instance
import com.zky.basics.api.splash.entity.AccountLevel
import com.zky.basics.api.splash.entity.ImageUrl
import com.zky.basics.api.splash.entity.RegionOrSchoolBean
import com.zky.basics.api.splash.entity.Userinfo
import com.zky.basics.common.mvvm.model.BaseModel

class SplashModel(application: Application?) : BaseModel(application) {
    private val mCommonService = instance.commonService
    private val splashService = instance.splashService
    suspend fun login(username: String?, password: String?): Userinfo? = request {
        mCommonService.login(username, password)
    }

    suspend fun captcha(): ImageUrl? = request {
        splashService.captcha()
    }

    suspend fun getRegionOrSchool(
        regLevel: String?,
        regCode: String?
    ): List<RegionOrSchoolBean>? = request {
        mCommonService.getRegionOrSchool(regLevel, regCode)
    }

    suspend fun sendSms(
        token: String?,
        code: String?,
        phone: String?,
        type: String?
    ): Any? = request {
        splashService.sendSms(token, code, phone, type)
    }

    suspend fun regist(
        url: String,

    ): Any? = request {
        splashService.regist(
            url
        )
    }

    suspend fun updateUserPassword(
        oprationType: String?,
        phone: String?,
        oldPassword: String?,
        password: String?,
        smsCode: String?
    ): Any? = request {
        mCommonService.updateUserPassword(
            oprationType,
            phone,
            oldPassword,
            password,
            smsCode
        )
    }


    suspend fun getAccountLevel(
        accountLevel: Int?,
    ): List<AccountLevel>? = request {
        splashService.getAccountLevel(
            accountLevel
        )
    }

    suspend fun getRegion(
        regionLevel: Int?,
        regionCode:String?
    ): List<RegionOrSchoolBean>? = request {
        splashService.getRegion(
            regionLevel,
            regionCode
        )
    }



}