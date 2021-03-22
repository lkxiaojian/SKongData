package com.zky.zky_questionnaire.mvvm.viewmodle

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import com.zky.basics.common.mvvm.viewmodel.BaseRefreshViewModel
import com.zky.basics.common.util.spread.showToast
import com.zky.zky_questionnaire.TestData
import com.zky.zky_questionnaire.inter.itemChangeListener
import com.zky.zky_questionnaire.mvvm.model.qnModel

/**
 * Created by lk
 * Date 2019-11-08
 * Time 11:06
 * Detail:
 */
class QNViewModle(application: Application, model: qnModel) :
    BaseRefreshViewModel<TestData, qnModel>(application, model) {
    var itemChange=ObservableField<itemChangeListener>()
    override fun refreshData() {

    }

    override fun loadMore() {
    }

    override fun enableLoadMore() = false
    override fun enableRefresh() = false
    fun startClick(view: View) {
        mList[0].selectValue.showToast()
    }


}