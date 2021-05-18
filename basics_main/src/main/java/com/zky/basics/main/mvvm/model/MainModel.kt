package com.zky.basics.main.mvvm.model

import android.app.Application
import com.zky.basics.api.RetrofitManager.Companion.instance
import com.zky.basics.api.common.entity.task.TaskBean
import com.zky.basics.api.common.entity.task.TaskChart
import com.zky.basics.api.common.entity.task.TaskItem
import com.zky.basics.api.common.entity.task.TaskWjBean
import com.zky.basics.api.room.bean.Areas
import com.zky.basics.api.splash.entity.AccountLevel
import com.zky.basics.api.splash.entity.ImageUrl
import com.zky.basics.api.splash.entity.RegionOrSchoolBean
import com.zky.basics.api.splash.entity.Userinfo
import com.zky.basics.common.mvvm.model.BaseModel

class MainModel(application: Application?) : BaseModel(application) {
    private val mCommonService = instance.commonService
    private val splashService = instance.splashService
    private val taskService = instance.taskService

    suspend fun login(username: String?, password: String?): Userinfo? = request {
        mCommonService.login(username, password)
    }
    suspend fun captcha(): ImageUrl? = request {
        splashService.captcha()
    }
    suspend fun sendSms(
        token: String?,
        code: String?,
        phone: String?,
        type: String?
    ): Any? = request {
        splashService.sendSms(token, code, phone, type)
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



    suspend fun getTaskList(code: String?):List<TaskBean>?=request {
        taskService.getTaskList(code)
    }
    suspend fun getTaskChart(code: String?,itemName:String?):List<List<TaskChart>>?=request {
        taskService.getTaskChart(code,itemName)
    }

    suspend fun getTaskWjList(code: String?):List<TaskWjBean>?=request {
        taskService.getTaskWjList(code)
    }


    suspend fun getItemList(taskCode: String?,message:String?):List<TaskItem>?=request {
        taskService.getItemList(taskCode,message)
    }

    suspend fun getAddrLevel():List<AccountLevel>?=request {
        taskService.getAddrLevel()
    }

    suspend fun getAddr(regionLevel: Int?,code: String?):List<RegionOrSchoolBean>?=request {
        taskService.getAddr(regionLevel,code)
    }

    suspend fun insertOrUpdateItem(url: String):Any?=request {
        taskService.insertOrUpdateItem(url)
    }


    suspend fun getAddrAll(): Areas? = request {
        splashService.getAddrAll()
    }


    suspend fun delItem(taskCode: String?,itemCode:String?):Any?=request {
        taskService.delItem(taskCode,itemCode)
    }

}