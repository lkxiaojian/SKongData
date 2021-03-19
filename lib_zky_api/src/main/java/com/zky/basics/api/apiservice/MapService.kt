package com.zky.basics.api.apiservice

import com.zky.basics.api.common.entity.AddressListBean
import com.zky.basics.api.dto.RespDTO
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by lk
 * Date 2019-11-11
 * Time 09:36
 * Detail:
 */
interface MapService {

    @POST("insertOrUpdateSpaceData.do")
    suspend fun insertOrUpdateSpaceData(
        @Query("itemCode") itemCode: String?,
        @Query("spaceType") spaceType: String?,
        @Query("spaceDataList") spaceDataList: String?
    ): RespDTO<Any>


    @GET("getSpaceDataAll.do")
    suspend fun getSpaceDataAll(
        @Query("itemCode") itemCode: String?
    ): RespDTO<AddressListBean>




}