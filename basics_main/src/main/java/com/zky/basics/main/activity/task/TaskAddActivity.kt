package com.zky.basics.main.activity.task


import ARouterPath
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.method.DigitsKeyListener
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.zky.basics.api.common.entity.task.TaskBean
import com.zky.basics.api.config.API
import com.zky.basics.api.splash.entity.AccountLevel
import com.zky.basics.api.splash.entity.Userinfo
import com.zky.basics.common.mvvm.BaseMvvmActivity
import com.zky.basics.common.util.InfoVerify.ID_CARD
import com.zky.basics.common.util.InfoVerify.NUMANDCHAR
import com.zky.basics.common.util.InfoVerify.ONLY_NUM
import com.zky.basics.common.util.spread.decodeParcelable
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
//            if (taskData.dataAttr2?.contains("日期") == true) {
//                mViewModel?.att2Show?.set(true)
//            } else {
//                mViewModel?.att2Show?.set(false)
//                mBinding?.aetIdcatd?.keyListener = DigitsKeyListener.getInstance("0123456789xX")
//            }

            when (taskData.dataAttr2Type) {

                1 -> {
                    mViewModel?.att2Show?.set(false)
                    mBinding?.aetIdcatd?.keyListener = DigitsKeyListener.getInstance(ID_CARD)
                    mBinding?.aetIdcatd?.filters = arrayOf<InputFilter>(LengthFilter(18))

                }
                2 -> {
                    mViewModel?.att2Show?.set(false)
                    mBinding?.aetIdcatd?.keyListener = DigitsKeyListener.getInstance(ONLY_NUM)
                    mBinding?.aetIdcatd?.filters = arrayOf<InputFilter>(LengthFilter(11))
                }
                3 -> {
                    mViewModel?.att2Show?.set(true)
                }
                4 -> {
                    mViewModel?.att2Show?.set(false)
                }
                5 -> {
                    mViewModel?.att2Show?.set(false)
                    mBinding?.aetIdcatd?.keyListener =
                        DigitsKeyListener.getInstance(NUMANDCHAR)
                }
            }


            mViewModel?.getmVoidSingleLiveEvent()
                ?.observe(this, Observer { aVoid: String? ->
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
        val userinfo = decodeParcelable<Userinfo>("user")
        var url = API.URL_HOST + "/insertOrUpdateItem.do?taskCode=${taskData.taskCode}" +
                "&dataAttr1=${mViewModel?.attr1?.get()}" +
                "&dataAttr2=${mViewModel?.attr2?.get()}"
        url += "&dataAttr3=$addres&uname=${userinfo?.username}&phone=${userinfo?.phone}"
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