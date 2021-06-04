package com.zky.basics.main.mvvm.viewmodel

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.databinding.ObservableField
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.view.OptionsPickerView
import com.google.gson.internal.LinkedTreeMap
import com.zky.basics.api.common.entity.task.DepartmentDataList
import com.zky.basics.api.common.entity.task.KeyAndValue

import com.zky.basics.api.common.entity.task.TaskBean
import com.zky.basics.api.common.entity.task.TaskItem
import com.zky.basics.common.constant.Constants

import com.zky.basics.common.event.SingleLiveEvent
import com.zky.basics.common.mvvm.viewmodel.BaseRefreshViewModel

import com.zky.basics.main.mvvm.model.MainModel
import views.ViewOption.OptionsPickerBuilder
import java.text.FieldPosition


class DepartmentDataViewModel(application: Application, model: MainModel) :
    BaseRefreshViewModel<DepartmentDataList, MainModel>(application, model) {
    val dataCode = ObservableField<String>()
    var taskBean: TaskBean? = null
    var pickerBuilder: OptionsPickerBuilder? = null
    var pickerView: OptionsPickerView<Any>? = null
    private var mVoidSingleLiveEvent: SingleLiveEvent<String>? = null


    override fun refreshData() {

    }

    override fun loadMore() {

    }

    fun getData() {
        launchUI({
            val datasetTableList = mModel.getDatasetTableList(dataCode.get())
            datasetTableList?.let {
                mList.addAll(it)
            }
        })
    }


    @RequiresApi(Build.VERSION_CODES.N)
    fun getDataItem(position: Int, importDate: String?) {
        launchUI({
            val departmentDataList = mList[position]

            val datasetTableInfo =
                mModel.getDatasetTableInfo(
                    dataCode.get(),
                    departmentDataList.tableName,
                    Constants.id_card,
                    importDate
                )
            val dataS = arrayListOf<ArrayList<KeyAndValue>>()

            datasetTableInfo?.dataList?.let { it ->
                it.forEach {
                    if (it is LinkedTreeMap<*, *>) {
                        val maps = arrayListOf<KeyAndValue>()
                        it.forEach { any, any2 ->
                            maps.add(KeyAndValue("$any", "$any2"))
                        }
                        dataS.add(maps)
                    }
                }
            }

            mList[position].valueList=dataS
            mList[position].importDateList=datasetTableInfo?.importDateList
            if (importDate.isNullOrEmpty() && !datasetTableInfo?.importDateList.isNullOrEmpty()) {
                mList[position]?.dataTime = datasetTableInfo?.importDateList?.get(0).toString()
            }else{
                mList[position]?.dataTime =importDate.toString()
            }
            if(importDate.isNullOrEmpty()){
                mList[position]?.showRv = !departmentDataList.showRv
            }

            getmVoidSingleLiveEvent().value="notify"
        })

    }


    @RequiresApi(Build.VERSION_CODES.N)
    fun showPicker(list: List<String>, position: Int) {
        if (pickerView?.isShowing == true) {
            pickerView?.dismiss()
        }
        pickerView?.setPicker(list)
        pickerView?.show()
        pickerBuilder?.setOnOptionsSelectListener { options1, _, _, _ ->
            mList[position].dataTime = list[options1]
            getDataItem(position, list[options1])
        }
    }


    override fun enableLoadMore() = false
    override fun enableRefresh() = false


    fun getmVoidSingleLiveEvent(): SingleLiveEvent<String> {
        return createLiveData(mVoidSingleLiveEvent).also {
            mVoidSingleLiveEvent = it
        }
    }
}