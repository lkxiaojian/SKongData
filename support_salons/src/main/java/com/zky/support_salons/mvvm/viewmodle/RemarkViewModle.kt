package com.zky.support_salons.mvvm.viewmodle

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import com.zky.basics.api.common.entity.chine.TaskChineItemBean
import com.zky.basics.common.event.SingleLiveEvent
import com.zky.basics.common.mvvm.viewmodel.BaseRefreshViewModel

import com.zky.support_salons.mvvm.model.SupportModel


/**
 * Created by lk
 * Date 2019-11-08
 * Time 11:06
 * Detail:
 */
class RemarkViewModle(application: Application, model: SupportModel) :
    BaseRefreshViewModel<String, SupportModel>(application, model) {
    private var mVoidSingleLiveEvent: SingleLiveEvent<String>? = null

    var showAdd = ObservableField<Boolean>()
    init {
        showAdd.set(false)
    }
    override fun refreshData() {
        getData()
        postStopLoadMoreEvent()
        postStopRefreshEvent()
    }

    override fun loadMore() {
        postStopLoadMoreEvent()
        postStopRefreshEvent()
    }

    fun getData() {
        mList.add("")
        mList.add("")
        mList.add("")
        mList.add("")
        postStopLoadMoreEvent()
        postStopRefreshEvent()
//        launchUI({
//            mList.clear()
//
//
//        })

    }

    fun startClick(view:View){
        when(view.id){


        }

    }

    fun getmVoidSingleLiveEvent(): SingleLiveEvent<String> {
        return createLiveData(mVoidSingleLiveEvent).also {
            mVoidSingleLiveEvent = it
        }
    }
}