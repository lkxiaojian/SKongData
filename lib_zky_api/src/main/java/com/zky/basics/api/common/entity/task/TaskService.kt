package com.zky.basics.api.common.entity.task


import com.zky.basics.api.dto.RespDTO
import retrofit2.http.GET

import retrofit2.http.POST
import retrofit2.http.Query

interface TaskService {


    @GET("getTaskList.do")
    suspend fun getTaskList(@Query("appCode") code: String?): RespDTO<List<TaskBean>>
    @GET("getItemList.do")
    suspend fun getItemList(@Query("taskCode") taskCode: String?,@Query("message") message: String?): RespDTO<List<TaskItem>>


}