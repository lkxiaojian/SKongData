package com.zky.zky_questionnaire.mvvm.viewmodle

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import com.bigkoo.pickerview.view.OptionsPickerView
import com.google.gson.Gson
import com.zky.basics.api.common.entity.task.TaskQuestion
import com.zky.basics.api.common.entity.task.TaskResult
import com.zky.basics.common.constant.Constants
import com.zky.basics.common.event.SingleLiveEvent
import com.zky.basics.common.mvvm.viewmodel.BaseRefreshViewModel
import com.zky.basics.common.util.spread.showToast
import com.zky.zky_questionnaire.R
import com.zky.zky_questionnaire.inter.itemChangeListener
import com.zky.zky_questionnaire.mvvm.model.qnModel
import kotlinx.coroutines.delay
import views.ViewOption.OptionsPickerBuilder

/**
 * Created by lk
 * Date 2019-11-08
 * Time 11:06
 * Detail:
 */
class QNViewModle(application: Application, model: qnModel) :
    BaseRefreshViewModel<TaskQuestion, qnModel>(application, model) {
    var itemChange = ObservableField<itemChangeListener>()
    var pickerBuilder: OptionsPickerBuilder? = null
    var pickerView: OptionsPickerView<Any>? = null
    var needWrite = ObservableField<String>()
    var q_index: Int? = 0
    private var mVoidSingleLiveEvent: SingleLiveEvent<String>? = null

    private var needCout = 0

    init {
        needWrite.set("")
    }

    override fun refreshData() {

    }

    override fun loadMore() {
    }

    override fun enableLoadMore() = false
    override fun enableRefresh() = false
    fun startClick(view: View) {
        when (view.id) {
            R.id.abt_upload_wj -> {
                var list= arrayListOf<TaskResult>()
                mList.forEach {
                    if (it.answer.isNullOrEmpty() && it.nullable == 1) {
                        "第${it.q_index}题未填写".showToast()
                        q_index = it.q_index?.minus(1)
                        getmVoidSingleLiveEvent().value = "scroll"
                        return
                    }
                    list.add(TaskResult(it.q_code,it.answer))
                }

                val toJson = Gson().toJson(list)
                launchUI({
                    mModel.insertOrUpdateWjInfo(toJson)
                    "上传成功".showToast()
                })
            }
        }

    }

    fun getWjTemplate() {
        launchUI({
            val wjTemplate = mModel.getWjTemplate(Constants.taskCode)
            val wjInfo = mModel.getWjInfo()
            if (wjInfo != null && wjTemplate != null) {
                for ((indexwj, valuewj) in wjInfo.withIndex()) {
                    for ((indexa, valuea) in wjInfo.withIndex()) {
                        if(valuea.q_code==valuewj.q_code){
                            wjTemplate[indexwj].answer=valuea.answer
                        }
                    }
                }
            }
            wjTemplate?.let { mList.addAll(it) }
            needWrite.set("$needCout/${mList.size}")
        })
    }

    fun setSelect(position: Int) {
        var list = mList[position].q_option
        list?.let {
            showPicker(it, position)
        }

    }


    fun showPicker(list: List<String>, position: Int) {
        pickerView?.setPicker(list)
//        pickerView?.setSelectOptions(data.get()!!.levelIndel)
        pickerView?.show()
        pickerBuilder?.setOnOptionsSelectListener { options1, _, _, _ ->
            mList[position].answer = list[options1]
            valueChangeWithIndex(position)
        }
    }

    fun valueChangeWithIndex(position: Int?) {
        try {
            val taskQuestion = mList[(position!! - 1)]
            needCout = 0
            mList.forEach {
                if (!it.answer.isNullOrEmpty()) {
                    needCout += 1
                }
            }
            needWrite.set("$needCout/${mList.size}")
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun getmVoidSingleLiveEvent(): SingleLiveEvent<String> {
        return createLiveData(mVoidSingleLiveEvent).also {
            mVoidSingleLiveEvent = it
        }
    }

}