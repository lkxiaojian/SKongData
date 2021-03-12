package com.zky.basics.api.apiservice

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
interface MineService {
    // 验证码
    @POST("uploadUserHeadImg.do")
    suspend fun updateUserByCode(
        @Query("userName") userName: String?,
        @Query("headImgPath") headImg: String?,
        @Query("code") code: String?
    ): RespDTO<Any>
}