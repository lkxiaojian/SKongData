package com.zky.basics.main.mvvm.viewmodel

import android.app.Application
import android.graphics.Color

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
import com.zky.basics.common.util.SoftInputUtil
import com.zky.basics.common.util.spread.showToast
import com.zky.basics.main.R
import com.zky.basics.main.mvvm.model.MainModel
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
    var app = application
    var att2Show = ObservableField<Boolean>() //true 日期  false edit
    var pageIndex = 1

    override fun refreshData() {
        pageIndex = 1
        setData()
    }

    override fun loadMore() {
        pageIndex += 1
        setData()
    }

    init {
        att2Show.set(false)
        itemCount.set("当前共有0条数据")
        sfMessage.set("身份证")
        sfMessageDigits.set("0123456789xX")
    }

    override fun enableLoadMore() = true
    override fun enableRefresh() = true

    fun setData() {
        launchUI({

            val itemList = mModel.getItemList(taskBean?.taskCode, searchMessage.get(), pageIndex)
            itemList?.let { it ->
                if (it.totalNum > mList.size) {
                    it.pageList.forEach {
                        it.fData = taskBean
                    }
                    if(pageIndex==1){
                        mList.clear()
                    }
                    mList.addAll( it.pageList)
                    itemCount.set("当前共有${it.totalNum}条数据")
                }
                postStopRefreshEvent()
                postStopLoadMoreEvent()
            }
        }, object : NetError {
            override fun getError(e: Exception) {
                postStopRefreshEvent()
                postStopLoadMoreEvent()
            }

        })

    }

    fun delItem(itemCode: String?, positon: Int) {
        launchUI({
            mModel.delItem(taskBean?.taskCode, itemCode)
            mList.removeAt(positon)
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
                SoftInputUtil.hideSoftInput(app, v)
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

            R.id.atv_se_time -> {
                TimePv(v)
                pickerView?.show()
                SoftInputUtil.hideSoftInput(app, v)
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
                OnTimeSelectListener { date, _ ->
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