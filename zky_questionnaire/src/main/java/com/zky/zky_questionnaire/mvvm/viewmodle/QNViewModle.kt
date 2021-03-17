package com.zky.zky_questionnaire.mvvm.viewmodle

import android.app.Application
import com.zky.basics.common.mvvm.viewmodel.BaseRefreshViewModel
import com.zky.zky_questionnaire.mvvm.model.qnModel

/**
 * Created by lk
 * Date 2019-11-08
 * Time 11:06
 * Detail:
 */
class QNViewModle(application: Application, model: qnModel) :
    BaseRefreshViewModel<String,qnModel>(application, model) {
    override fun refreshData() {

    }

    override fun loadMore() {
    }

    override fun enableLoadMore()=false
    override fun enableRefresh()=false

}