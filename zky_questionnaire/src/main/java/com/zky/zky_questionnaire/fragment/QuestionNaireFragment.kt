package com.zky.zky_questionnaire.fragment

import android.util.Log
import android.view.View

import com.zky.basics.common.mvvm.BaseMvvmRefreshFragment
import com.zky.basics.common.util.ObservableListUtil
import com.zky.zky_questionnaire.BR
import com.zky.zky_questionnaire.R
import com.zky.zky_questionnaire.TestData
import com.zky.zky_questionnaire.adapter.QnListAdapter
import com.zky.zky_questionnaire.databinding.QuetionnairefFragmentBinding
import com.zky.zky_questionnaire.inter.itemChangeListener
import com.zky.zky_questionnaire.mvvm.factory.QNViewModelFactory
import com.zky.zky_questionnaire.mvvm.viewmodle.QNViewModle

/**
 *create_time : 21-3-17 下午1:40
 *author: lk
 *description： QuestionNaireFragment
 */
class QuestionNaireFragment :
    BaseMvvmRefreshFragment<TestData, QuetionnairefFragmentBinding, QNViewModle>(),
    itemChangeListener {
    override fun refreshLayout() = mBinding?.drlQn
    override fun onBindViewModel() = QNViewModle::class.java
    override fun onBindViewModelFactory() = QNViewModelFactory.getInstance(mActivity.application)

    override fun initViewObservable() {
        val adapter = QnListAdapter(mActivity, mViewModel?.mList, this)
        mViewModel?.mList?.addOnListChangedCallback(
            ObservableListUtil.getListChangedCallback(
                adapter
            )
        )
        mBinding?.recview?.adapter = adapter
        mViewModel?.itemChange?.set(this)
        TestData.itemChange = this
    }

    override fun onBindVariableId() = BR.qnViewModel

    override fun onBindLayout() = R.layout.quetionnairef_fragment

    override fun initView(view: View?) {
        val list = arrayListOf<TestData>()
        for (i in 0..50) {
            val testData = TestData()
            testData.message = "$i,2,3"
//            testData.selectValue = "0"
            list.add(testData)
        }

        mViewModel?.mList?.addAll(list)

    }

    override fun initData() {

    }

    override fun getItem(data: TestData?) {
        val indexOf = mViewModel?.mList?.indexOf(data)
        Log.e("","")
    }


}