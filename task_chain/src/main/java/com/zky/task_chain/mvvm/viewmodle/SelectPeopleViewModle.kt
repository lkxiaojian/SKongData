package com.zky.task_chain.mvvm.viewmodle

import android.app.Application
import com.zky.basics.common.event.SingleLiveEvent
import com.zky.basics.common.mvvm.viewmodel.BaseRefreshViewModel
import com.zky.task_chain.mvvm.model.ChainModel



/**
 * Created by lk
 * Date 2019-11-08
 * Time 11:06
 * Detail:
 */
class SelectPeopleViewModle(application: Application, model: ChainModel) :
    BaseRefreshViewModel<String,ChainModel>(application, model) {
    private var mVoidSingleLiveEvent: SingleLiveEvent<String>? = null

    override fun refreshData() {
        postStopLoadMoreEvent()
        postStopRefreshEvent()
    }

    override fun loadMore() {
        postStopLoadMoreEvent()
        postStopRefreshEvent()
    }

    fun getData() {
        mList.add("1")
        mList.add("1")
        mList.add("1")
        mList.add("1")
        mList.add("1")
        mList.add("1")
        mList.add("1")
        mList.add("1")
    }

    fun getmVoidSingleLiveEvent(): SingleLiveEvent<String> {
        return createLiveData(mVoidSingleLiveEvent).also {
            mVoidSingleLiveEvent = it
        }
    }
}