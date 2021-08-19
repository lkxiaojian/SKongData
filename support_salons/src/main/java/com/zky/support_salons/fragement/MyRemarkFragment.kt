package com.zky.support_salons.fragement

import android.view.View
import com.zky.basics.common.adapter.BaseBindAdapter
import com.zky.basics.common.mvvm.BaseMvvmRefreshFragment
import com.zky.basics.common.util.ObservableListUtil
import com.zky.support_salons.BR
import com.zky.support_salons.R
import com.zky.support_salons.adapter.MyRemarkListAdapter
import com.zky.support_salons.databinding.MyRemarkFragmentBinding
import com.zky.support_salons.mvvm.factory.SupportSalonsViewModelFactory
import com.zky.support_salons.mvvm.viewmodle.RemarkViewModle

/**
 * @Description:     我的评论
 * @Author:         lk
 * @CreateDate:     2021/8/19 13:41
 */
class MyRemarkFragment :
    BaseMvvmRefreshFragment<String, MyRemarkFragmentBinding, RemarkViewModle>(),
    BaseBindAdapter.OnItemClickListener<Any> {
    override fun refreshLayout() = mBinding?.drlQ

    override fun onBindViewModel() = RemarkViewModle::class.java

    override fun onBindViewModelFactory() =
        SupportSalonsViewModelFactory.getInstance(mActivity.application)

    override fun initViewObservable() {

        val adapter = MyRemarkListAdapter(mActivity, mViewModel?.mList)
        mViewModel?.mList?.addOnListChangedCallback(
            ObservableListUtil.getListChangedCallback(
                adapter
            )
        )
        adapter.setItemClickListener(this)
        mBinding?.recview?.adapter = adapter
    }

    override fun onBindVariableId() = BR.remarkViewModle
    override fun onBindLayout() = R.layout.my_remark_fragment

    override fun initView(view: View?) {
    }

    override fun initData() {
    }

    override fun onItemClick(e: Any, position: Int) {

    }
}