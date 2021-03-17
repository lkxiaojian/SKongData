package com.zky.basics.main.mvvm.viewmodel

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import com.alibaba.android.arouter.launcher.ARouter
import com.zky.basics.api.common.entity.task.TaskBean
import com.zky.basics.api.common.entity.task.TaskItem
import com.zky.basics.common.event.SingleLiveEvent
import com.zky.basics.common.mvvm.viewmodel.BaseRefreshViewModel
import com.zky.basics.common.util.spread.showToast
import com.zky.basics.main.R
import com.zky.basics.main.mvvm.model.MainModel


class TaskViewModel(application: Application, model: MainModel) :
    BaseRefreshViewModel<TaskItem, MainModel>(application, model) {
    var taskBean: TaskBean? = null
    private var mVoidSingleLiveEvent: SingleLiveEvent<String>? = null
    var searchMessage = ObservableField<String>()

    override fun refreshData() {

    }

    override fun loadMore() {

    }

    fun setData() {
        launchUI({
            val itemList = mModel.getItemList(taskBean?.taskCode, searchMessage.get())
            itemList?.let { it ->
                itemList.forEach {
                    it.fData = taskBean
                }
                mList.addAll(it)
            }

        })

    }

    fun startClick(v: View) {
        when (v.id) {
            R.id.iv_mine -> {
                ARouter.getInstance().build(ARouterPath.MINE_MAIN).navigation()
            }
            R.id.acb_search -> {
                mList.clear()
                setData()
            }
            R.id.aiv_add_task -> {
                ARouter.getInstance().build(ARouterPath.ADDTASK).navigation()
            }
            R.id.atv_se_ad -> {
                getmVoidSingleLiveEvent().value = "dialogShow"
            }
        }

    }

    fun getmVoidSingleLiveEvent(): SingleLiveEvent<String> {
        return createLiveData(mVoidSingleLiveEvent).also {
            mVoidSingleLiveEvent = it
        }
    }
}