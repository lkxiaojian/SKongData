package com.zky.zky_questionnaire.fragment

import android.util.Log
import android.view.View
import com.zky.basics.api.common.entity.task.TaskQuestion
import com.zky.basics.common.adapter.BaseBindAdapter

import com.zky.basics.common.mvvm.BaseMvvmRefreshFragment
import com.zky.basics.common.util.ObservableListUtil
import com.zky.basics.common.util.OptionsPickerBuilder
import com.zky.zky_questionnaire.BR
import com.zky.zky_questionnaire.R
import com.zky.zky_questionnaire.adapter.QnListAdapter
import com.zky.zky_questionnaire.databinding.QuetionnairefFragmentBinding
import com.zky.zky_questionnaire.inter.Co.itemChange
import com.zky.zky_questionnaire.inter.itemChangeListener
import com.zky.zky_questionnaire.mvvm.factory.QNViewModelFactory
import com.zky.zky_questionnaire.mvvm.viewmodle.QNViewModle

/**
 *create_time : 21-3-17 下午1:40
 *author: lk
 *description： QuestionNaireFragment
 */
class QuestionNaireFragment :
    BaseMvvmRefreshFragment<TaskQuestion, QuetionnairefFragmentBinding, QNViewModle>(),
    itemChangeListener, BaseBindAdapter.OnItemClickListener<Any> {
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
        adapter.setItemClickListener(this)
    }

    override fun onBindVariableId() = BR.qnViewModel

    override fun onBindLayout() = R.layout.quetionnairef_fragment

    override fun initView(view: View?) {

    }

    override fun initData() {
        mViewModel?.itemChange?.set(this)
        itemChange = this
        val optionsPickerBuilder = OptionsPickerBuilder()
        var pickerBuilder = context?.let { optionsPickerBuilder.pickerBuilder(it) }
        mViewModel?.pickerBuilder = pickerBuilder
        mViewModel?.pickerView = pickerBuilder?.build()
        mViewModel?.getWjTemplate()
    }

    override fun getIndex(position: Int?) {
        mViewModel?.valueChangeWithIndex(position)
    }

    override fun onItemClick(e: Any, position: Int) {
        mViewModel?.setSelect(position)
    }

    companion object {
        fun QuestionNewInstance(): QuestionNaireFragment {
            return QuestionNaireFragment()
        }
    }
}