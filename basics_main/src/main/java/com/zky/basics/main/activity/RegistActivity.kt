package com.zky.basics.main.activity

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.zky.basics.api.splash.entity.AccountLevel
import com.zky.basics.common.BR
import com.zky.basics.common.mvvm.BaseMvvmActivity
import com.zky.basics.common.util.BangUtli
import com.zky.basics.main.R
import com.zky.basics.main.adapter.LevelAdapter
import com.zky.basics.main.databinding.ActivityRegistBinding
import com.zky.basics.main.mvvm.factory.MainViewModelFactory.Companion.getInstance
import com.zky.basics.main.mvvm.viewmodel.SplashViewModel

class RegistActivity :
    BaseMvvmActivity<ActivityRegistBinding?, SplashViewModel>(), LevelAdapter.ItemClick {
    private lateinit var levelAdapter: LevelAdapter

    override fun onBindViewModel() = SplashViewModel::class.java
    override fun onBindViewModelFactory() = getInstance(application)
    override fun initViewObservable() {
        mViewModel?.captcha()
        mViewModel?.getmVoidSingleLiveEvent()?.observe(
            this,
            Observer {
                when (it) {
                    "notify" -> {
                        levelAdapter.setList(mViewModel!!.levelListT)
                        mViewModel?.levelAdapter = levelAdapter
                    }
                    else -> {
                        finishActivity()
                    }
                }


            }
        )
        levelAdapter = LevelAdapter(mViewModel!!.levelListT, this, this)
        mBinding?.rvLevel?.layoutManager = LinearLayoutManager(this)
        mBinding?.rvLevel?.adapter = levelAdapter
    }

    override fun onBindVariableId() = BR.viewModel

    override fun onBindLayout() = R.layout.activity_regist

    override fun onBindToolbarLayout() = R.layout.white_common_toolbar

    override val tootBarTitle = "账号注册"

    override val isFullScreen: Boolean = true



    override fun itemClick(bean: AccountLevel, position: Int) {

        mViewModel?.getRegion(bean.attr_idx, position, levelAdapter)

    }
}