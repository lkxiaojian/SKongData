package com.zky.basics.main.mvvm.viewmodel

import android.app.Application
import android.os.Message
import android.view.View
import androidx.databinding.ObservableField
import com.alibaba.android.arouter.launcher.ARouter
import com.zky.basics.common.event.SingleLiveEvent
import com.zky.basics.common.mvvm.viewmodel.BaseRefreshViewModel
import com.zky.basics.main.R
import com.zky.basics.main.mvvm.model.MainModel


class MainViewModel(application: Application, model: MainModel) :
    BaseRefreshViewModel<String, MainModel>(application, model) {
    private var mVoidSingleLiveEvent: SingleLiveEvent<String>? = null
    var nameTop = ObservableField<String>()
    var nameBom = ObservableField<String>()

    init {
        nameTop.set("某某县时空大数据采集")
        nameBom.set("与查询系统")
    }

    override fun refreshData() {
        mHandler.sendEmptyMessageDelayed(1, 1000)

    }

    override fun loadMore() {
        mHandler.sendEmptyMessageDelayed(2, 2000)
    }

    private fun setData() {
        for (i in 1..5) {
            mList.add("$i")
        }

    }

    fun startClick(v: View) {
        when (v.id) {
            R.id.iv_mine -> {
                ARouter.getInstance().build(ARouterPath.MINE_MAIN).navigation()
            }
        }

    }

    // 创建一个Handler
    private val mHandler: android.os.Handler = object : android.os.Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            setData()
            postStopRefreshEvent()
            postStopLoadMoreEvent()
        }
    }


}