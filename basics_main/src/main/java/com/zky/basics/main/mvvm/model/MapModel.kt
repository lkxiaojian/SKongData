package com.zky.basics.main.mvvm.model

import android.app.Application
import com.zky.basics.api.RetrofitManager.Companion.instance
import com.zky.basics.api.apiservice.MapService
import com.zky.basics.api.common.entity.AddressListBean
import com.zky.basics.common.constant.Constants.itemCode
import com.zky.basics.common.mvvm.model.BaseModel

/**
 * Created by lk
 * Date 2019-11-08
 * Time 10:55
 * Detail:
 */
class MapModel(application: Application?) : BaseModel(application) {
    private val mapService: MapService = instance.mapService


    suspend fun insertOrUpdateSpaceData(
        spaceType: String,
        spaceDataList: String
    ): Any? = request {
        mapService.insertOrUpdateSpaceData(itemCode, spaceType, spaceDataList)
    }

    suspend fun getSpaceDataAll(): AddressListBean? = request {
        mapService.getSpaceDataAll(itemCode)
    }


}