package com.zky.basics.api.splash.service

import com.zky.basics.api.dto.RespDTO
import com.zky.basics.api.room.bean.Areas
import com.zky.basics.api.splash.entity.AccountLevel
import com.zky.basics.api.splash.entity.ImageUrl
import com.zky.basics.api.splash.entity.RegionOrSchoolBean
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Created by lk
 * Date 2019-11-06
 * Time 17:49
 * Detail:
 */
interface SplashService {
    //图形验证
    @GET("getCaptcha.do")
    suspend fun captcha(): RespDTO<ImageUrl>

    // 验证码
    @GET("sendSms.do")
    suspend fun sendSms(
        @Query("token") token: String?,
        @Query("imgCode") verCode: String?,
        @Query("phone") phone: String?,
        @Query("smsType") smsType: String?
    ): RespDTO<Any>

    //组册
    @POST()
    suspend fun regist(
        @Url url: String?,
    ): RespDTO<Any>

    @GET("getAccountLevel.do")
    suspend fun getAccountLevel(
        @Query("accountLevel") accountLevel: Int?,
    ): RespDTO<List<AccountLevel>>

    @GET("getRegion.do")
    suspend fun getRegion(
        @Query("regionLevel") regionLevel: Int?,
        @Query("regionCode") regionCode: String?,
    ): RespDTO<List<RegionOrSchoolBean>>



    @GET("getAddrAll.do")
    suspend fun getAddrAll(): RespDTO<Areas>


}