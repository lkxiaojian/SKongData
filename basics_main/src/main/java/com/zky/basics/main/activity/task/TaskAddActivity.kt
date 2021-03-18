package com.zky.basics.main.activity.task


import com.alibaba.android.arouter.facade.annotation.Route
import com.zky.basics.api.common.entity.task.TaskBean
import com.zky.basics.common.mvvm.BaseMvvmActivity
import com.zky.basics.main.BR
import com.zky.basics.main.R
import com.zky.basics.main.databinding.ActivityTaskAddBinding
import com.zky.basics.main.fragment.CustomDialogFragment
import com.zky.basics.main.mvvm.factory.MainViewModelFactory
import com.zky.basics.main.mvvm.viewmodel.TaskViewModel

@Route(path = ARouterPath.ADDTASK)
class TaskAddActivity : BaseMvvmActivity<ActivityTaskAddBinding, TaskViewModel>() {
    lateinit var taskData: TaskBean
    override fun onBindViewModel() = TaskViewModel::class.java
    override fun onBindViewModelFactory() = MainViewModelFactory.getInstance(application)

    override fun initViewObservable() {
        try {
            taskData = intent.getParcelableExtra("taskData") as TaskBean
            mViewModel?.taskBean = taskData
            mViewModel?.getmVoidSingleLiveEvent()
                ?.observe(this, { aVoid: String? ->
                    when (aVoid) {

                        "dialogShow" -> {
                            val customDialogFragment = CustomDialogFragment()
                            customDialogFragment.show(
                                supportFragmentManager.beginTransaction(),
                                "dialogShow"
                            )
                        }
                        "miss" -> {
                            showTransLoadingView(false)
                        }
                    }
                })
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onBindVariableId() = BR.taskViewModel
    override fun onBindLayout() = R.layout.activity_task_add

    override val tootBarTitle = "创建新的采集"

    override fun onBindToolbarLayout() = R.layout.white_common_toolbar
}