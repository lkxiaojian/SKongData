package com.zky.basics.main.fragment

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.zky.basics.api.common.entity.task.DepartmentDataList

import com.zky.basics.common.adapter.BaseBindAdapter
import com.zky.basics.common.mvvm.BaseMvvmRefreshFragment
import com.zky.basics.common.util.ObservableListUtil
import com.zky.basics.common.util.OptionsPickerBuilder
import com.zky.basics.main.BR
import com.zky.basics.main.R
import com.zky.basics.main.adapter.DepartemntDataAdapter
import com.zky.basics.main.databinding.DepartmentDataFragmentBinding
import com.zky.basics.main.mvvm.factory.MainViewModelFactory
import com.zky.basics.main.mvvm.viewmodel.DepartmentDataViewModel

/**
 *create_time : 2021/5/18 下午1:37
 *author: lk
 *description： DepartmentDataFragment
 */
class DepartmentDataFragment(dataCode: String) :
    BaseMvvmRefreshFragment<DepartmentDataList, DepartmentDataFragmentBinding, DepartmentDataViewModel>(),
    BaseBindAdapter.OnItemClickListener<Any> {
    private val dataCode = dataCode
    override fun refreshLayout() = mBinding?.drlDd
    override fun onBindViewModel() = DepartmentDataViewModel::class.java
    override fun onBindViewModelFactory() = MainViewModelFactory.getInstance(mActivity.application)
private lateinit var adapter:DepartemntDataAdapter
    override fun initViewObservable() {
        mViewModel?.getmVoidSingleLiveEvent()?.observe(this, {
            if(it=="notify"){
                adapter.notifyDataSetChanged()
            }

        })

        mViewModel?.dataCode?.set(this.dataCode)
         adapter = DepartemntDataAdapter(mActivity, mViewModel?.mList)
        mViewModel?.mList?.addOnListChangedCallback(
            ObservableListUtil.getListChangedCallback(
                adapter
            )
        )
        adapter.setItemClickListener(this)
        mBinding?.recview?.adapter = adapter
    }

    override fun onBindVariableId() = BR.departmentDataViewModel

    override fun onBindLayout() = R.layout.department_data_fragment

    override fun initView(view: View?) {
    }

    override fun initData() {
        val optionsPickerBuilder = OptionsPickerBuilder()
        val pickerBuilder = mActivity.let { optionsPickerBuilder.pickerBuilder(it) }
        mViewModel?.pickerBuilder = pickerBuilder
        mViewModel?.pickerView = pickerBuilder?.build()
        mViewModel?.getData()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onItemClick(e: Any, position: Int) {
        if (e is DepartmentDataList) {
            if(e.tableRows.isNullOrEmpty()||"0"==e.tableRows){
                return
            }
            if (e.showRv) {
                mViewModel?.mList?.get(position)?.showRv = !e.showRv
            } else {
                mViewModel?.getDataItem(position, "")
            }
        } else {
            //显示下拉框
            val importDateList = mViewModel?.mList?.get(position)?.importDateList
            if(!importDateList.isNullOrEmpty()&&importDateList.size>1){
                mViewModel?.showPicker(importDateList,position)
            }
        }
    }

    override fun enableLazyData()=true
}