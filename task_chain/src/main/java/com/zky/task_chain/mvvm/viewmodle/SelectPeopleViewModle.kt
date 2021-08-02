package com.zky.task_chain.mvvm.viewmodle

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import com.zky.basics.api.common.entity.chine.SelectPeople
import com.zky.basics.common.event.SingleLiveEvent
import com.zky.basics.common.mvvm.viewmodel.BaseRefreshViewModel
import com.zky.task_chain.R
import com.zky.task_chain.mvvm.model.ChainModel


/**
 * Created by lk
 * Date 2019-11-08
 * Time 11:06
 * Detail:
 */
class SelectPeopleViewModle(application: Application, model: ChainModel) :
    BaseRefreshViewModel<SelectPeople, ChainModel>(application, model) {
    private var mVoidSingleLiveEvent: SingleLiveEvent<String>? = null
    var accountLevel = ObservableField<String>()
    var regionLevel = ObservableField<String>()
    var town = ObservableField<String>()
    var village = ObservableField<String>()
    var type = ObservableField<String>()
    var searchMessage = ObservableField<String>()
    var index = 1
    override fun refreshData() {
        index = 1
        getData()
    }

    override fun loadMore() {
        index += 1
        postStopLoadMoreEvent()
        postStopRefreshEvent()
//        getData()
    }

    fun getData() {
        launchUI({
            val userList = mModel.getUserList(
                accountLevel.get(),
                town.get(),
                village.get(),
                searchMessage.get(),
                type.get(),
                index
            )
            userList?.let {
                if (index == 1) {
                    mList.clear()
                }
                mList.addAll(it)
            }
            postStopLoadMoreEvent()
            postStopRefreshEvent()
        }, object : NetError {
            override fun getError(e: Exception) {
                postStopLoadMoreEvent()
                postStopRefreshEvent()
            }
        })
    }

    fun startClick(view: View) {
        when (view.id) {
            R.id.aiv_back -> {
                getmVoidSingleLiveEvent().value="back"

            }
            R.id.search_people -> {

            }
        }

    }


    fun getmVoidSingleLiveEvent(): SingleLiveEvent<String> {
        return createLiveData(mVoidSingleLiveEvent).also {
            mVoidSingleLiveEvent = it
        }
    }
}