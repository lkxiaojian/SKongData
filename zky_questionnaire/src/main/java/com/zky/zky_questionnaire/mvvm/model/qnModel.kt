package com.zky.zky_questionnaire.mvvm.model

import android.app.Application
import com.zky.basics.api.RetrofitManager.Companion.instance
import com.zky.basics.api.common.entity.task.TaskQuestion
import com.zky.basics.api.common.entity.task.TaskResult
import com.zky.basics.common.constant.Constants.itemCode
import com.zky.basics.common.constant.Constants.wjCode

import com.zky.basics.common.mvvm.model.BaseModel

/**
 * Created by lk
 * Date 2019-11-08
 * Time 10:55
 * Detail:
 */
class qnModel(application: Application?) : BaseModel(application) {
    private val mtaskService = instance.taskService


    suspend fun getWjTemplate(taskCode: String?,wjCode:String?): List<TaskQuestion>? = request {
        mtaskService.getWjTemplate(taskCode,wjCode)
    }
    suspend fun getWjInfo(): List<TaskResult>? = request {
        mtaskService.getWjInfo(itemCode, wjCode)
    }
    suspend fun insertOrUpdateWjInfo(wjInfoString:String):Any? = request {
        mtaskService.insertOrUpdateWjInfo(itemCode,wjInfoString, wjCode)
    }
}