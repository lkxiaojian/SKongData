package com.zky.zky_mine.activity

import android.os.Build
import androidx.annotation.RequiresApi
import com.zky.basics.common.mvvm.BaseActivity
import com.zky.basics.common.util.getVersionCode
import com.zky.zky_mine.R
import kotlinx.android.synthetic.main.activity_about.*


/**
 * about activity
 */
class AboutActivity : BaseActivity() {
    override fun onBindLayout() = R.layout.activity_about

    @RequiresApi(Build.VERSION_CODES.P)
    override fun initView() {
        app_version.text = "资金监管  版本号：${getVersionCode(application)}"
    }

    override fun initData() {

    }

    override val tootBarTitle="关于"



}
