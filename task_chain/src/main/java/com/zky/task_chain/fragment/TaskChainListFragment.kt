package com.zky.task_chain.fragment

import android.view.View

import com.zky.basics.common.mvvm.BaseMvvmRefreshFragment
import com.zky.task_chain.BR
import com.zky.task_chain.R
import com.zky.task_chain.databinding.TaskChainListFragmentBinding
import com.zky.task_chain.mvvm.factory.TaskChineViewModelFactory
import com.zky.task_chain.mvvm.viewmodle.ChainViewModle

/**
 *create_time : 2021/5/18 上午9:49
 *author: lk
 *description： TaskChainListFragment
 */
class TaskChainListFragment(type:String):BaseMvvmRefreshFragment<Any,TaskChainListFragmentBinding,ChainViewModle>() {
    private val fromType=type
    override fun refreshLayout()=mBinding?.drlChain

    override fun onBindViewModel()=ChainViewModle::class.java
    override fun onBindViewModelFactory()=TaskChineViewModelFactory.getInstance(mActivity.application)
    override fun initViewObservable() {
    }

    override fun onBindVariableId()=BR.chainViewModle

    override fun onBindLayout()= R.layout.task_chain_list_fragment

    override fun initView(view: View?) {
    }

    override fun initData() {

    }
}