package com.zky.task_chain.activity


import BangUtli.setCJViewPading
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.zky.basics.api.common.entity.chine.SelectPeople
import com.zky.basics.api.splash.entity.AccountLevel
import com.zky.basics.common.adapter.BaseBindAdapter
import com.zky.basics.common.mvvm.BaseMvvmRefreshActivity
import com.zky.basics.common.util.ObservableListUtil
import com.zky.basics.common.util.OptionsPickerBuilder
import com.zky.task_chain.BR
import com.zky.task_chain.R
import com.zky.task_chain.adapter.LevelListedAdapter
import com.zky.task_chain.adapter.SelectPeopleListAdapter
import com.zky.task_chain.bean.SelectPeopleLevelBean
import com.zky.task_chain.databinding.ActivitySelectPeopleBinding
import com.zky.task_chain.mvvm.factory.TaskChineViewModelFactory
import com.zky.task_chain.mvvm.viewmodle.SelectPeopleViewModle


class SelectPeopleActivity :
    BaseMvvmRefreshActivity<ActivitySelectPeopleBinding, SelectPeopleViewModle>(),
    BaseBindAdapter.OnItemClickListener<Any> {
    override fun refreshLayout() = mBinding?.drlPeople

    override fun onBindViewModel() = SelectPeopleViewModle::class.java

    override fun onBindViewModelFactory() = TaskChineViewModelFactory.getInstance(application)

    companion object {
        var selects = arrayListOf<SelectPeople>()
    }

    override fun initViewObservable() {
        try {
            mViewModel?.type?.set(intent.getStringExtra("type"))
            mViewModel?.selectType?.set(intent.getStringExtra("selectType"))
            mViewModel?.setLevelData()
            mViewModel?.getmVoidSingleLiveEvent()?.observe(this, Observer { it ->
                when (it) {
                    "back" -> {
                        finishActivity()
                    }
                    "sure" -> {
                        selects.clear()
                        mViewModel?.mList?.forEach {
                            if (it.check) {
                                selects.add(it)
                            }
                        }
                        finishActivity()
                    }
                }

            })

            val adapter = SelectPeopleListAdapter(this, mViewModel?.mList)
            mViewModel?.mList?.addOnListChangedCallback(
                ObservableListUtil.getListChangedCallback(
                    adapter
                )
            )
            adapter.setItemClickListener(this)
            mBinding?.recview?.adapter = adapter
            mViewModel?.firstShow?.set(true)
            mViewModel?.getData()

            val levelListedAdapter = LevelListedAdapter(this, mViewModel?.showList)
            mViewModel?.showList?.addOnListChangedCallback(
                ObservableListUtil.getListChangedCallback(
                    levelListedAdapter
                )
            )
            val ms = LinearLayoutManager(this)
            ms.orientation = LinearLayoutManager.HORIZONTAL
            mBinding?.rvLevel?.layoutManager = ms
            mBinding?.rvLevel?.adapter = levelListedAdapter
            levelListedAdapter.setItemClickListener(this)

            val optionsPickerBuilder = OptionsPickerBuilder()
            val pickerBuilder = this.let { optionsPickerBuilder.pickerBuilder(it) }
            mViewModel?.pickerBuilder = pickerBuilder
            mViewModel?.pickerView = pickerBuilder?.build()
        } catch (e: Exception) {

        }
    }

    override fun onBindVariableId() = BR.selectPeopleViewModle

    override fun onBindLayout() = R.layout.activity_select_people

    override fun enableToolbar() = false
    override val isFullScreen = false
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setCJViewPading(mBinding?.clTest)
    }


    override fun onItemClick(e: Any, position: Int) {
        if (e is SelectPeople) {
            val check = e.check
            e.check = !check
            mViewModel?.mList?.set(position, e)
        } else if (e is SelectPeopleLevelBean) {
            if (position == 0) {
                mViewModel?.getLevel()
            } else {
                val accountLevel = e.data as AccountLevel
                mViewModel?.getRegion(accountLevel.attr_idx,position)
            }
        }

    }
}