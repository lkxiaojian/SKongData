package com.zky.basics.main.fragment

import android.view.View

import com.zky.basics.common.adapter.BaseBindAdapter
import com.zky.basics.common.mvvm.BaseMvvmRefreshFragment
import com.zky.basics.common.util.ObservableListUtil
import com.zky.basics.main.BR
import com.zky.basics.main.R
import com.zky.basics.main.adapter.DepartemntDataAdapter
import com.zky.basics.main.databinding.DepartmentDataFragmentBinding
import com.zky.basics.main.mvvm.factory.MainViewModelFactory
import com.zky.basics.main.mvvm.viewmodel.DepartmentDataViewModel

/**
 *create_time : 2021/5/18 下午1:37
 *author: lk
 *description： DepartmentDataFragment
 */
class DepartmentDataFragment :
    BaseMvvmRefreshFragment<Any, DepartmentDataFragmentBinding, DepartmentDataViewModel>(),
    BaseBindAdapter.OnItemClickListener<Any>{
    override fun refreshLayout() = mBinding?.drlDd
    override fun onBindViewModel() = DepartmentDataViewModel::class.java
    override fun onBindViewModelFactory() = MainViewModelFactory.getInstance(mActivity.application)

    override fun initViewObservable() {
        val adapter = DepartemntDataAdapter(mActivity, mViewModel?.mList)
        mViewModel?.mList?.addOnListChangedCallback(
            ObservableListUtil.getListChangedCallback(
                adapter
            )
        )
        adapter.setItemClickListener(this)
        mBinding?.recview?.adapter = adapter
        mViewModel?.getData()
    }

    override fun onBindVariableId() = BR.departmentDataViewModel

    override fun onBindLayout() = R.layout.department_data_fragment

    override fun initView(view: View?) {
    }

    override fun initData() {

    }

    override fun onItemClick(e: Any, position: Int) {

    }
}