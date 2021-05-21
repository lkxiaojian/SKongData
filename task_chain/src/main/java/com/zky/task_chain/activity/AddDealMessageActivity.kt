package com.zky.task_chain.activity

import com.zky.basics.common.mvvm.BaseMvvmActivity
import com.zky.task_chain.BR
import com.zky.task_chain.R
import com.zky.task_chain.databinding.ActivityAddDealMessageBinding
import com.zky.task_chain.mvvm.factory.TaskChineViewModelFactory
import com.zky.task_chain.mvvm.viewmodle.AddDealMessageViewModle

class AddDealMessageActivity :
    BaseMvvmActivity<ActivityAddDealMessageBinding, AddDealMessageViewModle>() {
    override fun onBindViewModel() = AddDealMessageViewModle::class.java

    override fun onBindViewModelFactory() = TaskChineViewModelFactory.getInstance(application)

    override fun initViewObservable() {
    }

    override fun onBindVariableId() = BR.addDealMessageViewModle
    override fun onBindLayout() = R.layout.activity_add_deal_message

    override val tootBarTitle = "新增处理"

}