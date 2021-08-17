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
class SupportModelViewModle(application: Application, model: SupportModel) :
    BaseRefreshViewModel<TaskChineItemBean, SupportModel>(application, model) {
    private var mVoidSingleLiveEvent: SingleLiveEvent<String>? = null

    var queryType = ObservableField<String>()
    var taskCode = ObservableField<String>()
    var userCode = ObservableField<String>()
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
        launchUI({
            mList.clear()
            val itemList = mModel.getItemList(queryType.get(), taskCode.get(), userCode.get())
            itemList?.let {
                mList.addAll(it)
            }

        })

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