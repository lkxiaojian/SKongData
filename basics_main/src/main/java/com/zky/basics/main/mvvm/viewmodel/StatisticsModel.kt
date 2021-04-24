package com.zky.basics.main.mvvm.viewmodel


import android.app.Application
import android.util.Log
import android.view.View
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import com.zky.basics.api.common.entity.task.TaskChartAdBean
import com.zky.basics.api.common.entity.task.TaskItem
import com.zky.basics.common.event.SingleLiveEvent
import com.zky.basics.common.mvvm.viewmodel.BaseRefreshViewModel
import com.zky.basics.common.util.reflec.instanceOf
import com.zky.basics.main.R
import com.zky.basics.main.mvvm.model.MainModel

class StatisticsModel(application: Application, model: MainModel) :
    BaseRefreshViewModel<TaskChartAdBean, MainModel>(application, model) {

    private var mVoidSingleLiveEvent: SingleLiveEvent<String>? = null
    var searchMessage = ObservableField<String>()
    var taskCode = ObservableField<String>()
    var paisu = ObservableField<Boolean>()
    private var mListTmp = ObservableArrayList<TaskChartAdBean>()

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
            val taskChart = mModel.getTaskChart(taskCode.get(), searchMessage.get())
            taskChart?.let {

                val tmpList = arrayListOf<TaskChartAdBean>()
                taskChart.forEach {
                    val taskChartAdBean = instanceOf<TaskChartAdBean>()
                    var avgData = 0.0
                    var title = ""
                    val list = arrayListOf<String>()
                    val listDouble = arrayListOf<Double>()
                    it.forEach { bean ->
                        avgData += bean.score
                        title = "沟壑名称:${bean.data_attr_1}"
                        list.add(bean.uname)
                        listDouble.add(bean.score)
                    }
                    avgData /= it.size
                    taskChartAdBean.avgData = String.format("%.2f", (avgData)).toDouble()
                    taskChartAdBean.mDatas = listDouble
                    taskChartAdBean.mDesciption = list
                    taskChartAdBean.isAnimation = true
                    taskChartAdBean.title = title
                    tmpList.add(taskChartAdBean)
                }
                mList.clear()
                mList.addAll(tmpList)
            }
            mListTmp.clear()
            mListTmp.addAll(mList)
//            getmVoidSingleLiveEvent().value="dismiss"


        })


    }

    fun startClick(v: View) {
        when (v.id) {
            R.id.aiv_rank -> {
                val f = paisu.get()
                f?.let {
                    paisu.set(!it)
                }
                val get = paisu.get()!!
                if (get) {
//                    getData()
                    mList.clear()
                    mList.addAll(mListTmp)
                } else {
                    mList.sortBy { bean -> bean.avgData }

                }
                getmVoidSingleLiveEvent().value = "dismiss"
            }
            R.id.acb_search -> {
                getData()
            }
        }

    }


    fun getmVoidSingleLiveEvent(): SingleLiveEvent<String> {
        return createLiveData(mVoidSingleLiveEvent).also {
            mVoidSingleLiveEvent = it
        }
    }
}