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
    suspend fun getItemList(
        @Query("taskCode") taskCode: String?,
        @Query("dataAttr1") message: String?
    ): RespDTO<List<TaskItem>>


    @GET("getAddrLevel.do")
    suspend fun getAddrLevel(): RespDTO<List<AccountLevel>>

    @GET("getAddr.do")
    suspend fun getAddr(
        @Query("regionLevel") regionLevel: Int?,
        @Query("regionCode") regionCode: String?
    ): RespDTO<List<RegionOrSchoolBean>>


    @POST()
    suspend fun insertOrUpdateItem(
        @Url url: String?,
    ): RespDTO<Any>


    @GET("getWjTemplate.do")
    suspend fun getWjTemplate(
        @Query("taskCode") taskCode: String?,
        @Query("wjCode") wjCode: String?
    ): RespDTO<List<TaskQuestion>>

    @GET("getWjInfo.do")
    suspend fun getWjInfo(
        @Query("itemCode") taskCode: String?,
        @Query("wjCode") wjCode: String?
    ): RespDTO<List<TaskResult>>

    @POST("insertOrUpdateWjInfo.do")
    suspend fun insertOrUpdateWjInfo(
        @Query("itemCode") taskCode: String?,
        @Query("wjInfoString") wjInfoString: String?,
        @Query("wjCode") wjCode: String?
    ): RespDTO<Any>

    @GET("getTaskChart.do")
    suspend fun getTaskChart(
        @Query("taskCode") taskCode: String?,
        @Query("itemName") itemName: String?
    ): RespDTO<List<List<TaskChart>>>

    @POST("deleteItem.do")
    suspend fun delItem(
        @Query("taskCode") taskCode: String?,
        @Query("itemCode") itemCode: String?,
    ): RespDTO<Any>


    @GET("getTaskWjList.do")
    suspend fun getTaskWjList(@Query("taskCode") taskCode: String?): RespDTO<List<TaskWjBean>>


    @GET("getDatasetTableList.do")
    suspend fun getDatasetTableList(@Query("datasetCode") datasetCode: String?,  @Query("dataAttr2") dataAttr2: String?,): RespDTO<List<DepartmentDataList>>

    @GET("getDatasetTableInfo.do")
    suspend fun getDatasetTableInfo(
        @Query("datasetCode") datasetCode: String?,
        @Query("tableName") tableName: String?,
        @Query("dataAttr2") dataAttr2: String?,
        @Query("importDate") importDate: String?
    ): RespDTO<DepartmentDataList>


}