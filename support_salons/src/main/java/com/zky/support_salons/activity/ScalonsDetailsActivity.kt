package com.zky.support_salons.activity

import com.refresh.lib.DaisyRefreshLayout
import com.zky.basics.common.adapter.BaseBindAdapter
import com.zky.basics.common.mvvm.BaseMvvmRefreshActivity
import com.zky.basics.common.util.ObservableListUtil
import com.zky.support_salons.BR
import com.zky.support_salons.R
import com.zky.support_salons.adapter.MyNewsListAdapter
import com.zky.support_salons.adapter.ScalonsDealisListAdapter
import com.zky.support_salons.databinding.ActivityScalonsDetailsBinding
import com.zky.support_salons.mvvm.factory.SupportSalonsViewModelFactory
import com.zky.support_salons.mvvm.viewmodle.SupportModelViewModle

class ScalonsDetailsActivity :
    BaseMvvmRefreshActivity<ActivityScalonsDetailsBinding, SupportModelViewModle>(),
    BaseBindAdapter.OnItemClickListener<Any> {
    override fun refreshLayout() = mBinding?.drlQ

    override fun onBindViewModel() = SupportModelViewModle::class.java

    override fun onBindViewModelFactory() = SupportSalonsViewModelFactory.getInstance(application)

    override fun initViewObservable() {

        val adapter = ScalonsDealisListAdapter(this, mViewModel?.mList)
        mViewModel?.mList?.addOnListChangedCallback(
            ObservableListUtil.getListChangedCallback(
                adapter
            )
        )
        adapter.setItemClickListener(this)
        mBinding?.recview?.adapter = adapter
        mViewModel?.getData()
    }

    override fun onBindVariableId() = BR.supportModelViewModle

    override fun onBindLayout() = R.layout.activity_scalons_details

    override val tootBarTitle = "详情"
    override fun onItemClick(e: Any, position: Int) {

    }

}