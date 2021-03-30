package com.zky.zky_mine.activity

import ARouterPath
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.launcher.ARouter
import com.zky.basics.common.mvvm.BaseMvvmActivity
import com.zky.zky_mine.BR

import com.zky.zky_mine.R
import com.zky.zky_mine.databinding.ActivityModificationPasswadBinding
import com.zky.zky_mine.mvvm.factory.MineViewModelFactory
import com.zky.zky_mine.mvvm.viewmodle.MineViewModle

class ModificationPasswadActivity :
    BaseMvvmActivity<ActivityModificationPasswadBinding, MineViewModle>() {
    override fun onBindViewModelFactory() = MineViewModelFactory.getInstance(application)
    override fun onBindViewModel() = MineViewModle::class.java
    override fun initViewObservable() {
        val safe = intent.extras["safe"]
        if (safe != "safe") {
            finish()
            return
        }
        mViewModel?.getmVoidSingleLiveEvent()?.observe(this,
            Observer {
                when (it) {
                    "success" -> {
                        showTransLoadingView(false)
                        ARouter.getInstance().build(ARouterPath.LOGIN).navigation()
                        finishActivity()
                    }
                    "show" -> {
                        showTransLoadingView(true)
                    }
                    else -> showTransLoadingView(false)
                }
            }
        )
    }

    override fun onBindVariableId() = BR.modifyViewModel

    override fun onBindLayout() = R.layout.activity_modification_passwad
    override val tootBarTitle = "修改密码"
    override val isFullScreen = false
    override fun onBindToolbarLayout() = R.layout.blue_common_toolbar
}
