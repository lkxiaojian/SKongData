package com.zky.zky_questionnaire.mvvm.viewmodle

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import com.bigkoo.pickerview.view.OptionsPickerView
import com.zky.basics.common.mvvm.viewmodel.BaseRefreshViewModel

import com.zky.basics.common.util.spread.showToast
import com.zky.zky_questionnaire.TestData
import com.zky.zky_questionnaire.inter.itemChangeListener
import com.zky.zky_questionnaire.mvvm.model.qnModel
import views.ViewOption.OptionsPickerBuilder

/**
 * Created by lk
 * Date 2019-11-08
 * Time 11:06
 * Detail:
 */
class QNViewModle(application: Application, model: qnModel) :
    BaseRefreshViewModel<TestData, qnModel>(application, model) {
    var itemChange = ObservableField<itemChangeListener>()

     var pickerBuilder: OptionsPickerBuilder? = null
    var  pickerView: OptionsPickerView<Any>? =null
    override fun refreshData() {

    }

    override fun loadMore() {
    }

    override fun enableLoadMore() = false
    override fun enableRefresh() = false
    fun startClick(view: View) {
        mList[0].selectValue.showToast()
    }


    fun setSelect(type: String,position:Int) {
        var list = arrayListOf<String>()
        list.add("123")
        list.add("12dddd")
        list.add("hahhah")
        list.add("hahhah")
        list.add("hahhah")
        showPicker(list,position)
    }


    fun showPicker(list: List<String>,position:Int) {
        pickerView?.setPicker(list)
//        pickerView?.setSelectOptions(data.get()!!.levelIndel)
        pickerView?.show()
        pickerBuilder?.setOnOptionsSelectListener { options1, _, _, _ ->
            mList[position].selectValue=list[options1]
        }


    }


}