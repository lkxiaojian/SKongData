package com.zky.basics.main.mvvm.viewmodel

import android.app.Application
import android.os.Message
import android.view.View
import androidx.databinding.ObservableField
import com.alibaba.android.arouter.launcher.ARouter
import com.zky.basics.api.common.entity.task.TaskBean
import com.zky.basics.api.config.API
import com.zky.basics.common.constant.Constants.appCode
import com.zky.basics.common.event.SingleLiveEvent
import com.zky.basics.common.mvvm.viewmodel.BaseRefreshViewModel
import com.zky.basics.common.util.spread.decode
import com.zky.basics.main.R
import com.zky.basics.main.mvvm.model.MainModel


class MainViewModel(application: Application, model: MainModel) :
    BaseRefreshViewModel<TaskBean, MainModel>(application, model) {
    private var mVoidSingleLiveEvent: SingleLiveEvent<String>? = null
    var nameTop = ObservableField<String>()
    var errorHeard = ObservableField<Int>()
    var imageHeard = ObservableField<String>()

    init {

        nameTop.set(application.getString(R.string.app_title))
        errorHeard.set(R.drawable.mine_tmp)
        imageHeard.set(API.ImageFolderPath + "".decode("headImgPath"))
    }

    override fun refreshData() {
        getData()
    }

    override fun loadMore() {
    }

    override fun enableLoadMore() = false
    override fun enableRefresh() = true
    fun getData() {
        launchUI({
            mList.clear()
            val taskList = mModel.getTaskList(appCode)
            taskList?.let {
                mList.addAll(it)
            }
            postStopRefreshEvent()
        }, object : NetError {
            override fun getError(e: Exception) {
                postStopRefreshEvent()
            }
        })
    }

    fun startClick(v: View) {
        when (v.id) {
            R.id.iv_mine -> {
                ARouter.getInstance().build(ARouterPath.MINE_MAIN).navigation()
            }
        }

    }


}