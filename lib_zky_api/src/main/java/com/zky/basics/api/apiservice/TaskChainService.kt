package com.zky.basics.api.apiservice

import com.zky.basics.api.common.entity.chine.SelectPeople
import com.zky.basics.api.common.entity.chine.TaskChineBean
import com.zky.basics.api.common.entity.chine.TaskChineItemBean
import com.zky.basics.api.dto.RespDTO
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

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
        @Query("accountLevel") accountLevel: String?,
        @Query("regionLevel") regionLevel: String?,
        @Query("town") town: String?,
        @Query("userName") userName: String?,
        @Query("type") type: String?,
        @Query("pageIndex") pageIndex: Int?,
        @Query("pageSize") pageSize: Int?
    ): RespDTO<ArrayList<SelectPeople>>

    @GET()
    suspend fun getUserList(
        @Url url: String?,
    ): RespDTO<ArrayList<SelectPeople>>

    @POST("tasklink/insertTaskLink.do")
    suspend fun insertTaskLink(
        @Query("parentCode") parentCode: String?,
        @Query("userCode") userCode: String?,
        @Query("userName") userName: String?,
        @Query("type") type: String?,
        @Query("content") content: String?,
        @Query("receiverArr") receiverArr: String?,
        @Query("longitude") longitude: String?,
        @Query("latitude") latitude: String?,
        @Query("address") address: String?
    ): RespDTO<Any>

    @GET("tasklink/getItemList.do")
    suspend fun getItemList(
        @Query("queryType") queryType: String?,
        @Query("taskCode") taskCode: String?,
        @Query("userCode") userCode: String?
    ): RespDTO<ArrayList<TaskChineItemBean>>

}