package com.zky.basics.main.activity.task


import android.content.Intent
import com.zky.basics.api.common.entity.task.TaskBean
import com.zky.basics.api.common.entity.task.TaskItem
import com.zky.basics.common.adapter.BaseBindAdapter
import com.zky.basics.common.constant.Constants
import com.zky.basics.common.mvvm.BaseMvvmRefreshActivity
import com.zky.basics.common.util.ObservableListUtil
import com.zky.basics.common.util.showCustomDialog
import com.zky.basics.common.util.view.CustomDialog
import com.zky.basics.main.BR
import com.zky.basics.main.R
import com.zky.basics.main.adapter.TaskListAdapter
import com.zky.basics.main.databinding.ActivityTaskctivityBinding
import com.zky.basics.main.mvvm.factory.MainViewModelFactory
import com.zky.basics.main.mvvm.viewmodel.TaskViewModel

class TaskActivity : BaseMvvmRefreshActivity<ActivityTaskctivityBinding, TaskViewModel>(),
    BaseBindAdapter.OnItemClickListener<Any>, BaseBindAdapter.OnItemLongClickListener<Any> {


    override fun onBindLayout() = R.layout.activity_taskctivity

    override fun refreshLayout() = mBinding?.drlTask

    override fun onBindViewModel() = TaskViewModel::class.java

    override fun onBindViewModelFactory() = MainViewModelFactory.getInstance(application)
    override fun onBindToolbarRightClick() {
        val intent = Intent(this, StatisticsActivity::class.java)
        intent.putExtra("taskCode", mViewModel?.taskBean?.taskCode)
        startActivity(intent)
    }

    override fun initViewObservable() {
        try {

            val bean = intent.extras["data"] as TaskBean
            val adapter = TaskListAdapter(this, mViewModel?.mList)
            mViewModel?.mList?.addOnListChangedCallback(
                ObservableListUtil.getListChangedCallback(
                    adapter
                )
            )
            adapter.setItemClickListener(this)
            adapter.setOnItemLongClickListener(this)
            mBinding?.recview?.adapter = adapter
            mViewModel?.taskBean = bean

            mViewModel?.setData()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onBindToolbarLayout()= R.layout.blue_common_right_image_toolbar

    override fun onResume() {
        super.onResume()
        if (TaskAddActivity.uploadSuc) {
            TaskAddActivity.uploadSuc = false
            mViewModel?.setData()
        }
    }

    override fun onBindVariableId() = BR.taskViewModel
    override val tootBarTitle: String
        get() = "任务"

    override fun onItemClick(e: Any, position: Int) {
        val taskItem = e as TaskItem
        val intent = Intent(this, TaskMessageActivity::class.java)
        intent.putExtra("itemCode", taskItem.itemCode)
        intent.putExtra("dataTask", mViewModel?.taskBean)
        Constants.dataAttr2 = taskItem.dataAttr1
        startActivity(intent)

    }

    override fun onItemLongClick(e: Any, postion: Int): Boolean {
        showCustomDialog(
            this,
            "要删除此数据吗",
            "删除后上传的数据将不可恢复",
            "",
            "取消",
            "确定"
        ).setOnItemClickListener(object :
            CustomDialog.OnItemClickListener {
            override fun onSure() {
                val taskItem = e as TaskItem
                mViewModel?.delItem(taskItem.itemCode, postion)

            }

            override fun onDismiss() {
            }

        })
        return true
    }

}