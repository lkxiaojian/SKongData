package com.zky.basics.main.activity.task


import com.zky.basics.common.adapter.BaseBindAdapter
import com.zky.basics.common.mvvm.BaseMvvmRefreshActivity
import com.zky.basics.common.util.ObservableListUtil
import com.zky.basics.common.util.spread.showToast
import com.zky.basics.main.BR
import com.zky.basics.main.R
import com.zky.basics.main.adapter.TaskListAdapter
import com.zky.basics.main.databinding.ActivityTaskctivityBinding
import com.zky.basics.main.mvvm.factory.MainViewModelFactory
import com.zky.basics.main.mvvm.viewmodel.TaskViewModel

class TaskActivity : BaseMvvmRefreshActivity<ActivityTaskctivityBinding, TaskViewModel>(),
    BaseBindAdapter.OnItemClickListener<Any> {


    override fun onBindLayout() = R.layout.activity_taskctivity

    override fun refreshLayout() = mBinding?.drlTask

    override fun onBindViewModel() = TaskViewModel::class.java

    override fun onBindViewModelFactory() = MainViewModelFactory.getInstance(application)

    override fun initViewObservable() {
        val adapter = TaskListAdapter(this, mViewModel?.mList)
        mViewModel?.mList?.addOnListChangedCallback(
            ObservableListUtil.getListChangedCallback(
                adapter
            )
        )
        adapter.setItemClickListener(this)
        mBinding?.recview?.adapter = adapter
    }

    override fun onBindVariableId() = BR.taskViewModel
    override val tootBarTitle: String
        get() = "任务"

    override fun onItemClick(e: Any, position: Int) {
        e.toString().showToast()
    }

}