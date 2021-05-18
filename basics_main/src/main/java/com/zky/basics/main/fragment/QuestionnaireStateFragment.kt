package com.zky.basics.main.fragment

import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.zky.basics.api.common.entity.task.TaskWjBean
import com.zky.basics.common.adapter.BaseBindAdapter
import com.zky.basics.common.constant.Constants.taskName
import com.zky.basics.common.constant.Constants.wjCode
import com.zky.basics.common.mvvm.BaseMvvmRefreshFragment
import com.zky.basics.common.util.ObservableListUtil
import com.zky.basics.main.BR
import com.zky.basics.main.R
import com.zky.basics.main.adapter.QStateListAdapter
import com.zky.basics.main.databinding.QuestionnaireStateFragmentBinding
import com.zky.basics.main.mvvm.factory.MainViewModelFactory
import com.zky.basics.main.mvvm.viewmodel.QStateViewModel

/**
 *create_time : 2021/5/18 上午11:10
 *author: lk
 *description： QuestionnaireStateFragment
 */
class QuestionnaireStateFragment :
    BaseMvvmRefreshFragment<TaskWjBean, QuestionnaireStateFragmentBinding, QStateViewModel>(),
    BaseBindAdapter.OnItemClickListener<Any> {
    override fun refreshLayout() = mBinding?.drlQ

    override fun onBindViewModel() = QStateViewModel::class.java

    override fun onBindViewModelFactory() = MainViewModelFactory.getInstance(mActivity.application)

    override fun initViewObservable() {

        val adapter = QStateListAdapter(mActivity, mViewModel?.mList)
        mViewModel?.mList?.addOnListChangedCallback(
            ObservableListUtil.getListChangedCallback(
                adapter
            )
        )
        adapter.setItemClickListener(this)
        mBinding?.recview?.adapter = adapter
        mViewModel?.getData()

    }

    override fun onBindVariableId() = BR.qStateViewModel

    override fun onBindLayout() = R.layout.questionnaire_state_fragment

    override fun initView(view: View?) {

    }

    override fun initData() {

    }

    override fun onItemClick(e: Any, position: Int) {
        val data = e as TaskWjBean
        taskName = data.wjName
        wjCode=data.wjCode
        ARouter.getInstance().build(ARouterPath.QUESTION_ACTIVITY).navigation()
    }

}