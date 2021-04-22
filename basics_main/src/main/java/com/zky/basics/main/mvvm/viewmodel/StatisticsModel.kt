package com.zky.basics.main.mvvm.viewmodel


import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import com.bigkoo.pickerview.view.TimePickerView
import com.zky.basics.api.common.entity.task.TaskBean
import com.zky.basics.api.common.entity.task.TaskItem
import com.zky.basics.common.event.SingleLiveEvent
import com.zky.basics.common.mvvm.viewmodel.BaseRefreshViewModel
import com.zky.basics.main.R
import com.zky.basics.main.mvvm.model.MainModel

class StatisticsModel(application: Application, model: MainModel) :
    BaseRefreshViewModel<TaskItem, MainModel>(application, model) {

    private var mVoidSingleLiveEvent: SingleLiveEvent<String>? = null
    var searchMessage = ObservableField<String>()
    var taskCode = ObservableField<String>()
    var paisu = ObservableField<Boolean>()

    init {
        paisu.set(true)
    }

    override fun refreshData() {
    }

    override fun loadMore() {

    }


    override fun enableLoadMore() = false
    override fun enableRefresh() = false

    fun getData() {
        launchUI({
            mModel.getTaskChart(taskCode.get())
        })


    }

    fun startClick(v: View) {
        when (v.id) {
            R.id.aiv_rank -> {
                val f = paisu.get()
                f?.let {
                    paisu.set(!it)
                }

            }
        }

    }


    fun getmVoidSingleLiveEvent(): SingleLiveEvent<String> {
        return createLiveData(mVoidSingleLiveEvent).also {
            mVoidSingleLiveEvent = it
        }
    }
}