package com.zky.basics.main.activity.task

import androidx.lifecycle.Observer
import com.zky.basics.common.mvvm.BaseMvvmRefreshActivity
import com.zky.basics.common.util.ObservableListUtil
import com.zky.basics.main.BR
import com.zky.basics.main.R
import com.zky.basics.main.adapter.TaskListAdapter
import com.zky.basics.main.adapter.TaskStatisticListAdapter
import com.zky.basics.main.databinding.ActivityStatisticsBinding
import com.zky.basics.main.mvvm.factory.MainViewModelFactory
import com.zky.basics.main.mvvm.viewmodel.StatisticsModel


class StatisticsActivity : BaseMvvmRefreshActivity<ActivityStatisticsBinding, StatisticsModel>() {
    override fun refreshLayout() = mBinding?.drlStatistics

    override fun onBindViewModel() = StatisticsModel::class.java
    override fun onBindViewModelFactory() = MainViewModelFactory.getInstance(application)
    private  var adapter:TaskStatisticListAdapter?=null

    override fun initViewObservable() {
        mViewModel?.getmVoidSingleLiveEvent()?.observe(this, Observer { it: String? ->
            if (it == "dismiss") {
                showTransLoadingView(false)
                adapter?.notifyDataSetChanged()
            }
        })
//        showTransLoadingView(true)
        val stringExtra = intent.getStringExtra("taskCode")
        mViewModel?.taskCode?.set(stringExtra)
         adapter = TaskStatisticListAdapter(this, mViewModel?.mList)
        mViewModel?.mList?.addOnListChangedCallback(
            ObservableListUtil.getListChangedCallback(
                adapter!!
            )
        )
        mBinding?.recview?.adapter = adapter
        mViewModel?.getData()
    }

    override val tootBarTitle = "数据统计"
    override fun onBindToolbarLayout() = R.layout.white_common_toolbar

    override fun onBindVariableId() = BR.statisticsModel

    override fun onBindLayout() = R.layout.activity_statistics

}