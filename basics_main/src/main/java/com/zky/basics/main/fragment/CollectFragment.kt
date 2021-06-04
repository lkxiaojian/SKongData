package com.zky.basics.main.fragment

import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import com.zky.basics.api.common.entity.task.TaskBean
import com.zky.basics.api.room.bean.MediaBean
import com.zky.basics.common.adapter.BaseBindAdapter
import com.zky.basics.common.constant.Constants
import com.zky.basics.common.mvvm.BaseMvvmRefreshFragment
import com.zky.basics.common.util.ObservableListUtil
import com.zky.basics.main.BR
import com.zky.basics.main.R
import com.zky.basics.main.activity.task.TaskActivity
import com.zky.basics.main.adapter.MainListAdapter
import com.zky.basics.main.databinding.ActivityOneMainBinding
import com.zky.basics.main.mvvm.factory.MainViewModelFactory
import com.zky.basics.main.mvvm.viewmodel.MainViewModel

/**
 *create_time : 2021/5/17 下午5:44
 *author: lk
 *description： CollectFragment
 */
class CollectFragment : BaseMvvmRefreshFragment<MediaBean, ActivityOneMainBinding, MainViewModel>(),
    BaseBindAdapter.OnItemClickListener<Any> {
    override fun refreshLayout() = mBinding?.drlMain
    override fun onBindViewModel() = MainViewModel::class.java
    override fun onBindViewModelFactory() = MainViewModelFactory.getInstance(mActivity.application)

    override fun initViewObservable() {
        showInitLoadView(true)
        mViewModel?.getmVoidSingleLiveEvent()
            ?.observe(this, Observer { a: String? ->
                if (a == "disMiss") {
                    showInitLoadView(false)
                }

            })
        val adapter = MainListAdapter(mActivity, mViewModel?.mList)
        mViewModel?.mList?.addOnListChangedCallback(
            ObservableListUtil.getListChangedCallback(
                adapter
            )
        )
        adapter.setItemClickListener(this)
        mBinding?.recview?.adapter = adapter
        mViewModel?.getData()
    }

    override fun onBindVariableId() = BR.mainListViewModel
    override fun onBindLayout() = R.layout.activity_one_main

    override fun initView(view: View?) {
    }

    override fun initData() {
    }

    override fun onItemClick(e: Any, position: Int) {
        val intent = Intent(mActivity, TaskActivity::class.java)
        val taskBean = e as TaskBean
        Constants.mediaDataTypeAudio = taskBean.mediaDataTypeAudio
        Constants.mediaDataTypePhoto = taskBean.mediaDataTypePhoto
        Constants.mediaDataTypeVideo = taskBean.mediaDataTypeVideo
        Constants.dataList = taskBean.datasetList
        intent.putExtra("data", taskBean)
        startActivity(intent)
    }
}