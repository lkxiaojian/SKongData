package com.zky.multi_media.mvvm.model

import android.app.Application
import com.zky.basics.api.RetrofitManager.Companion.instance
import com.zky.basics.api.apiservice.CommonService
import com.zky.basics.api.common.entity.OssToken
import com.zky.basics.api.file.ShowFileBean
import com.zky.basics.common.mvvm.model.BaseModel

/**
 * Created by lk
 * Date 2019-11-08
 * Time 10:55
 * Detail:
 */
class MediaModel(application: Application?) : BaseModel(application) {
    private val mCommonService: CommonService = instance.commonService


    suspend fun getAppToken(phone: String?, passward: String?): OssToken? = request {
        mCommonService.getAppToken(phone, passward)
    }

    suspend fun getFileList(itemCode: String?, mediaType: String?): List<ShowFileBean>? = request {
        mCommonService.getFileList(itemCode, mediaType)
    }
    suspend fun deleteFileInfo(code: String?): Any? = request {
        mCommonService.deleteFileInfo(code)
    }


}