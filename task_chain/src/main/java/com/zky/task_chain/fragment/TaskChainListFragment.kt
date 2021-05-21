package com.zky.task_chain.fragment

import android.view.View
import com.zky.basics.common.adapter.BaseBindAdapter

import com.zky.basics.common.mvvm.BaseMvvmRefreshFragment
import com.zky.basics.common.util.ObservableListUtil
import com.zky.task_chain.BR
import com.zky.task_chain.R
import com.zky.task_chain.adapter.TaskChainListAdapter
import com.zky.task_chain.databinding.TaskChainListFragmentBinding
import com.zky.task_chain.mvvm.factory.TaskChineViewModelFactory
import com.zky.task_chain.mvvm.viewmodle.ChainViewModle

/**
 *create_time : 2021/5/18 上午9:49
 *author: lk
 *description： TaskChainListFragment
 */
class TaskChainListFragment(type: String) :
    BaseMvvmRefreshFragment<Any, TaskChainListFragmentBinding, ChainViewModle>(),
    BaseBindAdapter.OnItemClickListener<Any> {
    private val fromType = type
    override fun refreshLayout() = mBinding?.drlChain

    override fun onBindViewModel() = ChainViewModle::class.java
    override fun onBindViewModelFactory() =
        TaskChineViewModelFactory.getInstance(mActivity.application)

    override fun initViewObservable() {
        val adapter = TaskChainListAdapter(mActivity, mViewModel?.mList)
        mViewModel?.mList?.addOnListChangedCallback(
            ObservableListUtil.getListChangedCallback(
                adapter
            )
        )
        adapter.setItemClickListener(this)
        mBinding?.recview?.adapter = adapter
        mViewModel?.getData()
    }

    override fun onBindVariableId() = BR.chainViewModle

    override fun onBindLayout() = R.layout.task_chain_list_fragment

    override fun initView(view: View?) {
    }

    override fun initData() {

    }

    override fun onItemClick(e: Any, position: Int) {

    }
}