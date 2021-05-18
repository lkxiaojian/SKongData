package com.zky.task_chain.mvvm.model

import android.app.Application
import com.zky.basics.api.RetrofitManager
import com.zky.basics.api.RetrofitManager.Companion.instance
import com.zky.basics.api.apiservice.CommonService
import com.zky.basics.api.apiservice.MineService
import com.zky.basics.api.common.entity.OssToken
import com.zky.basics.api.dto.RespDTO
import com.zky.basics.api.splash.entity.Userinfo
import com.zky.basics.common.mvvm.model.BaseModel
import okhttp3.MultipartBody

/**
 * Created by lk
 * Date 2019-11-08
 * Time 10:55
 * Detail:
 */
class ChainModel(application: Application?) : BaseModel(application) {
    private val mCommonService = instance.commonService
    private val mineService: MineService = instance.mineService
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

    suspend fun getUser(phone: String?): Userinfo? = request {
        mCommonService.getUser(phone)

    }

    suspend fun updateUserByCode(
        userName: String?,
        headImg: String?,
        code: String?
    ): Any? = request {
        mineService.updateUserByCode(userName ,headImg, code)
    }

    suspend fun getAppToken(phone: String?, passward: String?): OssToken? = request {
        mCommonService.getAppToken(phone, passward)
    }

}