package com.zky.multi_media.mvvm.viewmodle

import android.app.Application
import android.util.Log
import com.zky.basics.api.room.bean.MediaBean
import com.zky.basics.common.mvvm.viewmodel.BaseRefreshViewModel
import com.zky.multi_media.mvvm.model.MediaModel
import java.util.*

/**
 * Created by lk
 * Date 2020/2/27
 * Time 17:07
 * Detail:
 */
class MediaVoiceListViewModle(application: Application, mediaModel: MediaModel) :
    BaseRefreshViewModel<MediaBean, MediaModel>(application, mediaModel) {
    override fun refreshData() {
    }


    override fun loadMore() {
    }

    override fun enableLoadMore() = false
    override fun enableRefresh() = false
    private val timer = Timer()

    fun setTime(path: String, length: Int) {
        var position = -1
        for ((index, bean) in mList.withIndex()) {
            if (bean.create_data.isNullOrEmpty()) {
                continue
            }
            if (bean.file_path == path) {
                position = index
                mList[index].startIng = 2
            } else {
                mList[index].startIng = 1
            }
        }
//        timer.cancel()
        timer.schedule(tTask(position, length), 0, 1000)

    }


    inner class tTask(index: Int, length: Int) : TimerTask() {
        private val position = index
        private val length = length
        override fun run() {
            if (position != -1) {
                mList[position].lastTime = "$length 秒"
            }


        }

    }


}