package com.zky.task_chain.mvvm.viewmodle

import android.app.Application
import android.view.View
import com.zky.basics.common.event.SingleLiveEvent
import com.zky.basics.common.mvvm.viewmodel.BaseViewModel
import com.zky.task_chain.R
import com.zky.task_chain.mvvm.model.ChainModel


/**
 * Created by lk
 * Date 2019-11-08
 * Time 11:06
 * Detail:
 */
class AddDealMessageViewModle(application: Application, model: ChainModel) :
    BaseViewModel<ChainModel>(application, model) {

    private var mVoidSingleLiveEvent: SingleLiveEvent<String>? = null

    fun startClick(view: View) {
        when (view.id) {
            R.id.aiv_del_people -> {
                getmVoidSingleLiveEvent().value = "startSelectPeople"
            }
        }
    }

    fun getmVoidSingleLiveEvent(): SingleLiveEvent<String> {
        return createLiveData(mVoidSingleLiveEvent).also {
            mVoidSingleLiveEvent = it
        }
    }
}