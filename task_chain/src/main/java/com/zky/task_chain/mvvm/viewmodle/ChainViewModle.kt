package com.zky.task_chain.mvvm.viewmodle

import android.app.Application
import com.zky.basics.common.mvvm.viewmodel.BaseRefreshViewModel
import com.zky.task_chain.mvvm.model.ChainModel



/**
 * Created by lk
 * Date 2019-11-08
 * Time 11:06
 * Detail:
 */
class ChainViewModle(application: Application, model: ChainModel) :
    BaseRefreshViewModel<Any,ChainModel>(application, model) {
    override fun refreshData() {
    }

    override fun loadMore() {
    }


}