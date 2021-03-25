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
    var nameBom = ObservableField<String>()
    var errorHeard=ObservableField<Int>()
    var imageHeard=ObservableField<String>()
    init {
        nameTop.set("某某县时空大数据采集")
        nameBom.set("与查询系统")
        errorHeard.set(R.drawable.mine_tmp)
        imageHeard.set(API.ImageFolderPath + "".decode("headImgPath"))
    }

    override fun refreshData() {

    }

    override fun loadMore() {
    }

    override fun enableLoadMore() = false
    override fun enableRefresh() = false
    fun getData() {
        launchUI({
            val taskList = mModel.getTaskList(appCode)
            taskList?.let {
                mList.addAll(it)
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