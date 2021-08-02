package com.zky.basics.api.apiservice

import com.zky.basics.api.common.entity.chine.SelectPeople
import com.zky.basics.api.common.entity.chine.TaskChineBean
import com.zky.basics.api.dto.RespDTO
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TaskChainService {
    @GET("tasklink/getPageList.do")
    suspend fun getTaskPageList(
        @Query("userCode") userCode: String?,
        @Query("queryType") queryType: String?,
        @Query("status") status: String?,
        @Query("receiver") receiver: String?,
        @Query("type") type: String?,
        @Query("pageIndex") pageIndex: Int?,
        @Query("pageSize") pageSize: Int?
    ): RespDTO<TaskChineBean>


    @GET("tasklink/getUserList.do")
    suspend fun getUserList(
        @Query("accountLevel") accountLevel : String?,
        @Query("regionLevel") regionLevel : String?,
        @Query("town") town: String?,
        @Query("userName") userName: String?,
        @Query("type") type: String?,
        @Query("pageIndex") pageIndex: Int?,
        @Query("pageSize") pageSize: Int?
    ): RespDTO<ArrayList<SelectPeople>>
}