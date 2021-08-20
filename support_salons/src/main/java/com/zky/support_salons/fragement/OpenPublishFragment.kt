package com.zky.support_salons.fragement

import android.content.Intent
import android.view.View
import com.refresh.lib.DaisyRefreshLayout
import com.zky.basics.common.adapter.BaseBindAdapter
import com.zky.basics.common.mvvm.BaseMvvmFragment
import com.zky.basics.common.mvvm.BaseMvvmRefreshFragment
import com.zky.basics.common.util.ObservableListUtil
import com.zky.support_salons.BR
import com.zky.support_salons.R
import com.zky.support_salons.activity.ScalonsDetailsActivity
import com.zky.support_salons.adapter.OpenPublishListAdapter
import com.zky.support_salons.databinding.OpenPublishFragmentBinding
import com.zky.support_salons.mvvm.factory.SupportSalonsViewModelFactory
import com.zky.support_salons.mvvm.viewmodle.OpenPublishViewModle

/**
 * @Description:     公开  我的发表
 * @Author:         lk
 * @CreateDate:     2021/8/19 8:38
 */
class OpenPublishFragment(type:String): BaseMvvmRefreshFragment<String,OpenPublishFragmentBinding, OpenPublishViewModle>(),
    BaseBindAdapter.OnItemClickListener<Any> {
    override fun onBindViewModel()=OpenPublishViewModle::class.java

    override fun onBindViewModelFactory()= SupportSalonsViewModelFactory.getInstance(mActivity.application)

    override fun initViewObservable() {
        val adapter = OpenPublishListAdapter(mActivity, mViewModel?.mList)
        mViewModel?.mList?.addOnListChangedCallback(
            ObservableListUtil.getListChangedCallback(
                adapter
            )
        )
        adapter.setItemClickListener(this)
        mBinding?.recview?.adapter = adapter

    }

    override fun onBindVariableId()=BR.openPublishViewModle
    override fun onBindLayout()= R.layout.open_publish_fragment

    override fun initView(view: View?) {
    }

    override fun initData() {
    }

    override fun onItemClick(e: Any, position: Int) {
        startActivity(Intent(mActivity, ScalonsDetailsActivity::class.java))

    }

    override fun refreshLayout()=mBinding?.drlQ
}