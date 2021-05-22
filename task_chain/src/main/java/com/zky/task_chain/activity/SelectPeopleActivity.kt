package com.zky.task_chain.activity


import BangUtli.setCJViewPading
import androidx.lifecycle.Observer
import com.zky.basics.common.adapter.BaseBindAdapter
import com.zky.basics.common.mvvm.BaseMvvmRefreshActivity
import com.zky.basics.common.util.ObservableListUtil
import com.zky.task_chain.BR
import com.zky.task_chain.R
import com.zky.task_chain.adapter.SelectPeopleListAdapter
import com.zky.task_chain.databinding.ActivitySelectPeopleBinding
import com.zky.task_chain.mvvm.factory.TaskChineViewModelFactory
import com.zky.task_chain.mvvm.viewmodle.SelectPeopleViewModle

class SelectPeopleActivity : BaseMvvmRefreshActivity<ActivitySelectPeopleBinding, SelectPeopleViewModle>(),
    BaseBindAdapter.OnItemClickListener<Any>{
    override fun refreshLayout()=mBinding?.drlPeople

    override fun onBindViewModel()=SelectPeopleViewModle::class.java

    override fun onBindViewModelFactory()=TaskChineViewModelFactory.getInstance(application)

    override fun initViewObservable() {
        mViewModel?.getmVoidSingleLiveEvent()?.observe(this,Observer{

        })

        val adapter = SelectPeopleListAdapter(this, mViewModel?.mList)
        mViewModel?.mList?.addOnListChangedCallback(
            ObservableListUtil.getListChangedCallback(
                adapter
            )
        )
        adapter.setItemClickListener(this)
        mBinding?.recview?.adapter = adapter
        mViewModel?.getData()


    }

    override fun onBindVariableId()=BR.selectPeopleViewModle

    override fun onBindLayout()= R.layout.activity_select_people

    override fun enableToolbar() = false
    override val isFullScreen = false
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setCJViewPading(mBinding?.clTest)
    }

    override fun onItemClick(e: Any, position: Int) {

    }
}