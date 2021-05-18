package com.zky.basics.main.mvvm.viewmodel

import android.app.Application

import com.zky.basics.api.common.entity.task.TaskBean
import com.zky.basics.api.common.entity.task.TaskItem

import com.zky.basics.common.event.SingleLiveEvent
import com.zky.basics.common.mvvm.viewmodel.BaseRefreshViewModel

import com.zky.basics.main.mvvm.model.MainModel


class QStateViewModel(application: Application, model: MainModel) :
    BaseRefreshViewModel<String, MainModel>(application, model) {
    var taskBean: TaskBean? = null
    private var mVoidSingleLiveEvent: SingleLiveEvent<String>? = null


    override fun refreshData() {

    }

    override fun loadMore() {

    }

    fun getData(){
        val list= arrayListOf<String>("1","2","3")
        mList.addAll(list)
    }


    override fun enableLoadMore() = false
    override fun enableRefresh() = true


    fun getmVoidSingleLiveEvent(): SingleLiveEvent<String> {
        return createLiveData(mVoidSingleLiveEvent).also {
            mVoidSingleLiveEvent = it
        }
    }
}