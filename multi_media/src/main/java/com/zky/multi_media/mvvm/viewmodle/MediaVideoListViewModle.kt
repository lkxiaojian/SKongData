package com.zky.multi_media.mvvm.viewmodle

import android.app.Application
import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider
import com.zky.basics.api.config.API
import com.zky.basics.api.room.bean.MediaBean
import com.zky.basics.api.splash.entity.Userinfo
import com.zky.basics.common.constant.Constants
import com.zky.basics.common.mvvm.viewmodel.BaseRefreshViewModel
import com.zky.basics.common.util.*
import com.zky.basics.common.util.reflec.instanceOf
import com.zky.basics.common.util.spread.decodeParcelable
import com.zky.basics.common.util.uploadFile.OSSAuthCredentialsProvider
import com.zky.basics.common.util.uploadFile.OssUploadingFileUtil
import com.zky.basics.common.util.uploadFile.UploadingFile
import com.zky.basics.common.util.view.CustomDialog
import com.zky.multi_media.R
import com.zky.multi_media.mvvm.model.MediaModel
import java.util.ArrayList

/**
 * Created by lk
 * Date 2020/2/27
 * Time 17:07
 * Detail:
 */
class MediaVideoListViewModle(application: Application, mediaModel: MediaModel) :
    BaseRefreshViewModel<MediaBean, MediaModel>(application, mediaModel) {
    override fun refreshData() {
    }
    override fun loadMore() {
    }

    override fun enableLoadMore() = false
    override fun enableRefresh() = false





}