package com.zky.basics.main.mvvm.viewmodel

import android.app.Application
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.databinding.ObservableField
import com.alibaba.android.arouter.launcher.ARouter
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.TimePickerView
import com.zky.basics.api.common.entity.task.TaskBean
import com.zky.basics.api.common.entity.task.TaskItem
import com.zky.basics.common.event.SingleLiveEvent
import com.zky.basics.common.mvvm.viewmodel.BaseRefreshViewModel
import com.zky.basics.common.util.DateUtil
import com.zky.basics.common.util.spread.showToast
import com.zky.basics.main.R
import com.zky.basics.main.mvvm.model.MainModel
import views.ViewOption.OptionsPickerBuilder
import java.util.*


class TaskViewModel(application: Application, model: MainModel) :
    BaseRefreshViewModel<TaskItem, MainModel>(application, model) {
    var taskBean: TaskBean? = null
    private var pickerView: TimePickerView? = null
    private var mVoidSingleLiveEvent: SingleLiveEvent<String>? = null
    var searchMessage = ObservableField<String>()
    var sfMessage = ObservableField<String>()
    var sfMessageDigits = ObservableField<String>()
    var attr1 = ObservableField<String>()
    var attr2 = ObservableField<String>()
    var attr3 = ObservableField<String>()
    var itemCount = ObservableField<String>()

    var att2Show=ObservableField<Boolean>() //true 日期  false edit

    override fun refreshData() {
        setData()
    }

    override fun loadMore() {

    }

    init {
        itemCount.set("当前共有0条数据")
        sfMessage.set("身份证")
        sfMessageDigits.set("0123456789xX")
    }

    override fun enableLoadMore() = false
    override fun enableRefresh() = true

    fun setData() {
        launchUI({
            mList.clear()
            val itemList = mModel.getItemList(taskBean?.taskCode, searchMessage.get())
            itemList?.let { it ->
                itemList.forEach {
                    it.fData = taskBean
                }
                mList.addAll(it)
                itemCount.set("当前共有${mList.size}条数据")
            }
            postStopRefreshEvent()
        },object :NetError{
            override fun getError(e: Exception) {
                postStopRefreshEvent()
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
                ARouter.getInstance().build(ARouterPath.ADDTASK)
                    .withParcelable("taskData", taskBean)
                    .navigation()
            }
            R.id.atv_se_ad -> {
                getmVoidSingleLiveEvent().value = "dialogShow"
            }
            R.id.abt_add -> {
                if (attr1.get().isNullOrEmpty()) {
                    "${taskBean?.dataAttr1} 未填写".showToast()
                    return
                }
                if (attr2.get().isNullOrEmpty()) {
                    "${taskBean?.dataAttr2} 未填写".showToast()
                    return
                }
                getmVoidSingleLiveEvent().value = "abt_add"
            }

            R.id.atv_se_time->{
                TimePv(v)
                pickerView?.show()
            }
        }

    }

    fun insertOrUpdateItem(url: String) {
        launchUI({
            mModel.insertOrUpdateItem(url)
            "添加成功".showToast()
            getmVoidSingleLiveEvent().value = "finsh"
        })

    }


    fun TimePv(view: View) {
        val selectedDate = Calendar.getInstance()
        val startDate = Calendar.getInstance()
        startDate[1900, 1] = 1
        val endDate = Calendar.getInstance()
        endDate[2050, 1] = 1
        val booleans = booleanArrayOf(true, true, true, false, false, false)
        //时间选择器
        pickerView =
            TimePickerBuilder(view.context,
                OnTimeSelectListener { date, v ->
                    attr2.set(DateUtil.formatDate(date, DateUtil.FormatType.yyyyMMdd))
                })
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setType(booleans)
                .setLabel("年", "月", "日", "", "", "")
                .isCenterLabel(true)
                .setDividerColor(Color.DKGRAY)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setDecorView(null)
                .build()
    }


    fun getmVoidSingleLiveEvent(): SingleLiveEvent<String> {
        return createLiveData(mVoidSingleLiveEvent).also {
            mVoidSingleLiveEvent = it
        }
    }
}