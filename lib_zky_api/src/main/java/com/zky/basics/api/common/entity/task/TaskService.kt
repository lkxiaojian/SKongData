package com.zky.basics.api.common.entity.task


import com.zky.basics.api.dto.RespDTO
import com.zky.basics.api.splash.entity.AccountLevel
import com.zky.basics.api.splash.entity.RegionOrSchoolBean
import retrofit2.http.GET

import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface TaskService {


    @GET("getTaskList.do")
    suspend fun getTaskList(@Query("appCode") code: String?): RespDTO<List<TaskBean>>
    @GET("getItemList.do")
    suspend fun getItemList(@Query("taskCode") taskCode: String?,@Query("dataAttr1") message: String?): RespDTO<List<TaskItem>>



    @GET("getAddrLevel.do")
    suspend fun getAddrLevel(): RespDTO<List<AccountLevel>>

    @GET("getAddr.do")
    suspend fun getAddr(@Query("regionLevel") regionLevel: Int?): RespDTO<List<RegionOrSchoolBean>>



    @POST()
    suspend fun insertOrUpdateItem(
        @Url url: String?,
    ): RespDTO<Any>


    @GET("getWjTemplate.do")
    suspend fun getWjTemplate(@Query("taskCode") taskCode: String?): RespDTO<List<TaskQuestion>>

    @GET("getWjInfo.do")
    suspend fun getWjInfo(@Query("itemCode") taskCode: String?): RespDTO<List<TaskResult>>

    @POST("insertOrUpdateWjInfo.do")
    suspend fun insertOrUpdateWjInfo(@Query("itemCode") taskCode: String?,@Query("wjInfoString") wjInfoString: String?): RespDTO<Any>
}