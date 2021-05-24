package com.zky.basics.main.mvvm.viewmodel

import android.app.Application

import com.zky.basics.api.common.entity.task.TaskBean
import com.zky.basics.api.common.entity.task.TaskWjBean
import com.zky.basics.common.constant.Constants
import com.zky.basics.common.event.SingleLiveEvent
import com.zky.basics.common.mvvm.viewmodel.BaseRefreshViewModel

import com.zky.basics.main.mvvm.model.MainModel


class QStateViewModel(application: Application, model: MainModel) :
    BaseRefreshViewModel<TaskWjBean, MainModel>(application, model) {
    var taskBean: TaskBean? = null
    private var mVoidSingleLiveEvent: SingleLiveEvent<String>? = null


    override fun refreshData() {
        getData()
    }

    override fun loadMore() {

    }

    fun getData() {
        launchUI({
            mList.clear()
            val taskWjList = mModel.getTaskWjList(Constants.taskCode)
            taskWjList?.let {
                mList.addAll(it)
            }
            postStopRefreshEvent()
        }, object : NetError {
            override fun getError(e: Exception) {
                postStopRefreshEvent()
            }

        })
    }


    override fun enableLoadMore() = false
//    override fun enableRefresh() = false


    fun getmVoidSingleLiveEvent(): SingleLiveEvent<String> {
        return createLiveData(mVoidSingleLiveEvent).also {
            mVoidSingleLiveEvent = it
        }
    }
}