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
    private var timer: Timer? = null
    private var task: tTask? = null
    private var length = 0

    fun setTime(path: String, _length: Int) {
        length = _length
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

        if (timer != null) {
            task?.cancel()
            task = null
            timer?.cancel()
            timer = null
        }
        if (_length == 0) {
            mList[position].startIng = 1
            return
        }

        timer = Timer()
        task = tTask(position)
        timer?.schedule(task, 0, 1000)

    }

    fun vioceStop() {
        if (timer != null) {
            task?.cancel()
            task = null
            timer?.cancel()
            timer = null
        }
    }


    override fun onStop() {
        super.onStop()
        vioceStop()
    }


    inner class tTask(index: Int) : TimerTask() {
        private val position = index
        override fun run() {
            if (position != -1) {
                mList[position].lastTime = "${--length} 秒"
            }


        }

    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
        timer = null
    }


}