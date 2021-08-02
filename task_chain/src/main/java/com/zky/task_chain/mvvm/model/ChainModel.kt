package com.zky.task_chain.mvvm.model

import android.app.Application
import com.zky.basics.api.RetrofitManager
import com.zky.basics.api.RetrofitManager.Companion.instance
import com.zky.basics.api.apiservice.CommonService
import com.zky.basics.api.apiservice.MineService
import com.zky.basics.api.apiservice.TaskChainService
import com.zky.basics.api.common.entity.OssToken
import com.zky.basics.api.common.entity.chine.SelectPeople
import com.zky.basics.api.common.entity.chine.TaskChineBean
import com.zky.basics.api.config.API
import com.zky.basics.api.dto.RespDTO
import com.zky.basics.api.splash.entity.Userinfo
import com.zky.basics.common.constant.Constants
import com.zky.basics.common.mvvm.model.BaseModel
import okhttp3.MultipartBody

/**
 * Created by lk
 * Date 2019-11-08
 * Time 10:55
 * Detail:
 */
class ChainModel(application: Application?) : BaseModel(application) {
    private val mineService: TaskChainService = instance.taskChainService


    suspend fun getTaskPageList(userCode: String?, queryType: String?,status: String?,receiver: String?,type:String?,index:Int): TaskChineBean? = request {
        mineService.getTaskPageList(userCode,queryType,status,receiver,type,index,API.PAGE_SIZE)
    }

    suspend fun getUserList(accountLevel: String?, regionLevel: String?,town: String?,userName: String?,type:String?,index:Int): ArrayList<SelectPeople>? = request {
        mineService.getUserList(accountLevel,regionLevel,town,userName,type,index,API.PAGE_SIZE)
    }

}