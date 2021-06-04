package com.zky.basics.main.activity.task


import androidx.databinding.ObservableArrayList
import com.zky.basics.api.common.entity.task.KeyAndValue
import com.zky.basics.common.mvvm.BaseMvvmRefreshActivity
import com.zky.basics.common.util.ObservableListUtil
import com.zky.basics.main.BR
import com.zky.basics.main.R
import com.zky.basics.main.adapter.DepartemntDataActivityAdapter
import com.zky.basics.main.databinding.ActivityDepartemntDataBinding
import com.zky.basics.main.mvvm.factory.MainViewModelFactory
import com.zky.basics.main.mvvm.viewmodel.DepartmentDataItemViewModel

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class DepartemntDataActivity :
    BaseMvvmRefreshActivity<ActivityDepartemntDataBinding, DepartmentDataItemViewModel>() {
    override fun refreshLayout() = mBinding?.drlDepartmentDataItemViewModel

    override fun onBindViewModel() = DepartmentDataItemViewModel::class.java

    override fun onBindViewModelFactory() = MainViewModelFactory.getInstance(application)

    override fun initViewObservable() {
        try {
            var list = intent.extras["data"] as ArrayList<KeyAndValue>
            mViewModel?.mList?.addAll(list)
            val adapter = DepartemntDataActivityAdapter(this, mViewModel?.mList)
            mViewModel?.mList?.addOnListChangedCallback(
                ObservableListUtil.getListChangedCallback(
                    adapter
                )
            )
            mBinding?.recview?.adapter = adapter
            mViewModel?.getData()
        } catch (e: Exception) {

        }

    }

    override fun onBindVariableId() = BR.departmentDataItemViewModel

    override fun onBindLayout() = R.layout.activity_departemnt_data
    override val tootBarTitle: String = "详情"

    override fun onBindToolbarLayout() = R.layout.white_common_toolbar
    override val isFullScreen= false

}