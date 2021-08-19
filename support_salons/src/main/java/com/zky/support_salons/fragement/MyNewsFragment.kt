package com.zky.support_salons.fragement

import android.view.View
import com.zky.basics.common.adapter.BaseBindAdapter
import com.zky.basics.common.mvvm.BaseMvvmRefreshFragment
import com.zky.basics.common.util.ObservableListUtil
import com.zky.support_salons.BR
import com.zky.support_salons.R
import com.zky.support_salons.adapter.MyNewsListAdapter
import com.zky.support_salons.databinding.MyNewsFragmentBinding
import com.zky.support_salons.mvvm.factory.SupportSalonsViewModelFactory
import com.zky.support_salons.mvvm.viewmodle.NewsViewModle
import com.zky.support_salons.mvvm.viewmodle.RemarkViewModle

/**
 * @Description:     我的消息
 * @Author:         lk
 * @CreateDate:     2021/8/19 13:41
 */
class MyNewsFragment :
    BaseMvvmRefreshFragment<String, MyNewsFragmentBinding, NewsViewModle>(),
    BaseBindAdapter.OnItemClickListener<Any> {
    override fun refreshLayout() = mBinding?.drlQ

    override fun onBindViewModel() = NewsViewModle::class.java

    override fun onBindViewModelFactory() =
        SupportSalonsViewModelFactory.getInstance(mActivity.application)

    override fun initViewObservable() {

        val adapter = MyNewsListAdapter(mActivity, mViewModel?.mList)
        mViewModel?.mList?.addOnListChangedCallback(
            ObservableListUtil.getListChangedCallback(
                adapter
            )
        )
        adapter.setItemClickListener(this)
        mBinding?.recview?.adapter = adapter
    }

    override fun onBindVariableId() = BR.newsViewModle
    override fun onBindLayout() = R.layout.my_news_fragment

    override fun initView(view: View?) {
    }

    override fun initData() {
    }

    override fun onItemClick(e: Any, position: Int) {

    }
}