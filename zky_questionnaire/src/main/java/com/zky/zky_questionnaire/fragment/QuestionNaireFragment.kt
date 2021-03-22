package com.zky.zky_questionnaire.fragment

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.ViewModelProvider
import com.refresh.lib.DaisyRefreshLayout
import com.zky.basics.common.mvvm.BaseMvvmRefreshFragment
import com.zky.basics.common.util.ObservableListUtil
import com.zky.basics.common.util.spread.showToast
import com.zky.zky_questionnaire.BR
import com.zky.zky_questionnaire.R
import com.zky.zky_questionnaire.TestData
import com.zky.zky_questionnaire.adapter.QnListAdapter
import com.zky.zky_questionnaire.databinding.QuetionnairefFragmentBinding
import com.zky.zky_questionnaire.mvvm.factory.QNViewModelFactory
import com.zky.zky_questionnaire.mvvm.viewmodle.QNViewModle

/**
 *create_time : 21-3-17 下午1:40
 *author: lk
 *description： QuestionNaireFragment
 */
class QuestionNaireFragment :
    BaseMvvmRefreshFragment<TestData, QuetionnairefFragmentBinding, QNViewModle>() {
    override fun refreshLayout() = mBinding?.drlQn
    override fun onBindViewModel() = QNViewModle::class.java
    override fun onBindViewModelFactory() = QNViewModelFactory.getInstance(mActivity.application)

    override fun initViewObservable() {
        val adapter = QnListAdapter(mActivity, mViewModel?.mList)
        mViewModel?.mList?.addOnListChangedCallback(
            ObservableListUtil.getListChangedCallback(
                adapter
            )
        )
        mBinding?.recview?.adapter = adapter
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


}