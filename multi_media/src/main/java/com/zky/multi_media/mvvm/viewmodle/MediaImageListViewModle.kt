package com.zky.multi_media.mvvm.viewmodle

import android.app.Application
import com.zky.basics.api.room.bean.MediaBean
import com.zky.basics.common.mvvm.viewmodel.BaseRefreshViewModel
import com.zky.multi_media.mvvm.model.MediaModel

/**
 * Created by lk
 * Date 2020/2/27
 * Time 17:07
 * Detail:
 */
class MediaImageListViewModle(application: Application, mediaModel: MediaModel) :
    BaseRefreshViewModel<MediaBean, MediaModel>(application, mediaModel) {
    override fun refreshData() {
    }


    override fun loadMore() {
    }

    override fun enableLoadMore() = false
    override fun enableRefresh()=false





}