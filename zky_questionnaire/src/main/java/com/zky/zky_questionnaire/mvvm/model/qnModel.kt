package com.zky.zky_questionnaire.mvvm.model

import android.app.Application
import com.zky.basics.api.RetrofitManager.Companion.instance

import com.zky.basics.common.mvvm.model.BaseModel

/**
 * Created by lk
 * Date 2019-11-08
 * Time 10:55
 * Detail:
 */
class qnModel(application: Application?) : BaseModel(application) {
    private val mCommonService = instance.taskService






//    suspend fun getAppToken(phone: String?, passward: String?): OssToken? = request {
//        mCommonService.getAppToken(phone, passward)
//    }

}