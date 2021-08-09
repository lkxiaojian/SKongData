package com.zky.task_chain.activity


import android.content.Intent
import androidx.lifecycle.Observer
import com.zky.basics.common.adapter.BaseBindAdapter
import com.zky.basics.common.mvvm.BaseMvvmRefreshActivity
import com.zky.basics.common.util.ObservableListUtil
import com.zky.task_chain.BR
import com.zky.task_chain.R
import com.zky.task_chain.adapter.DealMeassgeListAdapter
import com.zky.task_chain.databinding.ActivityDealMessageBinding
import com.zky.task_chain.mvvm.factory.TaskChineViewModelFactory
import com.zky.task_chain.mvvm.viewmodle.DealMessageViewModle

class DealMessageActivity :
    BaseMvvmRefreshActivity<ActivityDealMessageBinding, DealMessageViewModle>(),
    BaseBindAdapter.OnItemClickListener<Any> {
    override fun refreshLayout() = mBinding?.drlDel

    override fun onBindViewModel() = DealMessageViewModle::class.java

    override fun onBindViewModelFactory() = TaskChineViewModelFactory.getInstance(application)

    override fun initViewObservable() {
        mViewModel?.getmVoidSingleLiveEvent()?.observe(this, Observer {
            when(it){
                "add"->{
                    val intent = Intent(this, AddDealMessageActivity::class.java)
                    intent.putExtra("type", mViewModel?.queryType?.get())
                    intent.putExtra("parentCode", mViewModel?.parentCode?.get())
                    intent.putExtra("taskCode",   mViewModel?.taskCode?.get())
                    startActivity(intent)
                }
            }
        })

        val queryType = intent.getStringExtra("queryType")
        mViewModel?.queryType?.set(queryType)
        mViewModel?.showAdd?.set(queryType!="send")
        mViewModel?.taskCode?.set(intent.getStringExtra("taskCode"))
        mViewModel?.userCode?.set(intent.getStringExtra("userCode"))
        mViewModel?.parentCode?.set(intent.getStringExtra("parentCode"))

        val adapter = DealMeassgeListAdapter(this, mViewModel?.mList,queryType)
        mViewModel?.mList?.addOnListChangedCallback(
            ObservableListUtil.getListChangedCallback(
                adapter
            )
        )
        adapter.setItemClickListener(this)
        mBinding?.recview?.adapter = adapter
        mViewModel?.getData()
    }

    override fun onBindVariableId() = BR.dealMessageViewModle

    override fun onBindLayout() = R.layout.activity_deal_message
    override fun onItemClick(e: Any, position: Int) {

    }

    override val tootBarTitle = "处理详情"

}