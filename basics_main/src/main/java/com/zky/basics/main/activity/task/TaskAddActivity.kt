package com.zky.basics.main.activity.task


import android.text.method.DigitsKeyListener
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.zky.basics.api.common.entity.task.TaskBean
import com.zky.basics.api.config.API
import com.zky.basics.api.splash.entity.AccountLevel
import com.zky.basics.common.mvvm.BaseMvvmActivity
import com.zky.basics.common.util.spread.showToast
import com.zky.basics.main.BR
import com.zky.basics.main.R
import com.zky.basics.main.databinding.ActivityTaskAddBinding
import com.zky.basics.main.fragment.CustomDialogFragment
import com.zky.basics.main.mvvm.factory.MainViewModelFactory
import com.zky.basics.main.mvvm.viewmodel.TaskViewModel

@Route(path = ARouterPath.ADDTASK)
class TaskAddActivity : BaseMvvmActivity<ActivityTaskAddBinding, TaskViewModel>(),
    CustomDialogFragment.AddressSelect {
    lateinit var taskData: TaskBean
    private var levelList: List<AccountLevel>? = null
    override fun onBindViewModel() = TaskViewModel::class.java
    override fun onBindViewModelFactory() = MainViewModelFactory.getInstance(application)

    companion object {
        var uploadSuc = false
    }

    override fun initViewObservable() {
        try {
            taskData = intent.getParcelableExtra("taskData") as TaskBean
            mViewModel?.taskBean = taskData
            if (taskData.dataAttr2?.contains("日期") == true) {
                mViewModel?.att2Show?.set(true)
            } else {
                mViewModel?.att2Show?.set(false)
                mBinding?.aetIdcatd?.keyListener= DigitsKeyListener.getInstance("0123456789xX")
            }
            mViewModel?.getmVoidSingleLiveEvent()
                ?.observe(this, Observer{ aVoid: String? ->
                    when (aVoid) {
                        "dialogShow" -> {
                            val customDialogFragment = CustomDialogFragment()
                            customDialogFragment.show(
                                supportFragmentManager.beginTransaction(),
                                "dialogShow"
                            )
                            customDialogFragment.setAddressSelect(this)
                        }

                        "finsh" -> {
                            uploadSuc = true
                            finish()
                        }
                        "miss" -> {
                            showTransLoadingView(false)
                        }
                        "abt_add" -> {
                            if (levelList.isNullOrEmpty()) {
                                "地址未选择".showToast()
                               return@Observer
                            }
                            addSure()
                        }
                    }
                })
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun addSure() {


        var url = API.URL_HOST + "/insertOrUpdateItem.do?taskCode=${taskData.taskCode}" +
                "&dataAttr1=${mViewModel?.attr1?.get()}" +
                "&dataAttr2=${mViewModel?.attr2?.get()}"
        url += "&dataAttr3=$addres"
        mViewModel?.insertOrUpdateItem(url)
    }

    override fun onBindVariableId() = BR.taskViewModel
    override fun onBindLayout() = R.layout.activity_task_add

    override val tootBarTitle = "创建新的采集"
    var addres = ""
    override fun onBindToolbarLayout() = R.layout.white_common_toolbar
    override fun getSelectAddress(levelListT: List<AccountLevel>) {
        levelList = levelListT
        levelList?.forEach {
            addres += it.value
        }
        mViewModel?.attr3?.set(addres)
    }
}