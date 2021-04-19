package com.zky.basics.main


import BangUtli.setCJViewPading
import BangUtli.setViewPading
import android.content.Intent
import androidx.lifecycle.Observer
import com.zky.basics.api.common.entity.task.TaskBean
import com.zky.basics.api.config.API
import com.zky.basics.common.adapter.BaseBindAdapter
import com.zky.basics.common.constant.Constants
import com.zky.basics.common.mvvm.BaseMvvmRefreshActivity
import com.zky.basics.common.util.ObservableListUtil
import com.zky.basics.common.util.spread.decode
import com.zky.basics.common.util.spread.encode
import com.zky.basics.common.util.spread.showToast
import com.zky.basics.main.activity.task.TaskActivity
import com.zky.basics.main.adapter.MainListAdapter
import com.zky.basics.main.databinding.ActivityOneMainBinding
import com.zky.basics.main.mvvm.factory.MainViewModelFactory
import com.zky.basics.main.mvvm.viewmodel.MainViewModel

class OneMainActivity : BaseMvvmRefreshActivity<ActivityOneMainBinding, MainViewModel>(),
    BaseBindAdapter.OnItemClickListener<Any> {
    private var mBackPressed: Long = 0
    override fun refreshLayout() = mBinding?.drlMain
    override fun onBindViewModel() = MainViewModel::class.java
    override fun onBindViewModelFactory() = MainViewModelFactory.getInstance(application)

    override fun initViewObservable() {
        showInitLoadView(true)
        mViewModel?.getmVoidSingleLiveEvent()
            ?.observe(this, Observer { a: String? ->
                if(a==  "disMiss"){
                    showInitLoadView(false)
                }

            })
        val adapter = MainListAdapter(this, mViewModel?.mList)
        mViewModel?.mList?.addOnListChangedCallback(
            ObservableListUtil.getListChangedCallback(
                adapter
            )
        )
        adapter.setItemClickListener(this)
        mBinding?.recview?.adapter = adapter
        mViewModel?.getData()
    }
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setCJViewPading(mBinding?.clTest)
    }

    override fun onBindVariableId() = BR.mainListViewModel

    override fun onBindLayout() = R.layout.activity_one_main

    override fun enableToolbar() = false
    override val isFullScreen = false
    override fun onItemClick(e: Any, position: Int) {
        val intent = Intent(this, TaskActivity::class.java)
        val taskBean = e as TaskBean
        Constants.mediaDataTypeAudio=taskBean.mediaDataTypeAudio
        Constants.mediaDataTypePhoto=taskBean.mediaDataTypePhoto
        Constants.mediaDataTypeVideo=taskBean.mediaDataTypeVideo
        intent.putExtra("data", taskBean)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
       mViewModel?.imageHeard?.set(API.ImageFolderPath + "".decode("headImgPath"))
    }


    override fun onBackPressed() {
        mBackPressed = if (mBackPressed + TIME_EXIT > System.currentTimeMillis()) {
            super.onBackPressed()
            return
        } else {
            "再点击一次返回退出程序".showToast()
            System.currentTimeMillis()
        }
    }

    companion object {
        private const val TIME_EXIT = 2000
    }
}