package com.zky.sk.zjcp

import androidx.multidex.MultiDex
import com.zky.basics.api.RetrofitManager.Companion.init
import com.zky.basics.common.BaseApplication
import com.zky.basics.main.activity.LoginActivity
import com.zxy.recovery.core.Recovery

/**
 * Created by lk
 * Date 2020/8/26
 * Time 17:30
 * Detail:
 */
class Application : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        init(this)
        MultiDex.install(this)
        Recovery.getInstance()
            .debug(false)
            .recoverInBackground(false)
            .recoverStack(true)
            .mainPage(LoginActivity::class.java)
            .recoverEnabled(true)
            .callback(MyCrashCallback())
            .silent(true, Recovery.SilentMode.RESTART)
            .skip(LoginActivity::class.java)
            .init(this)
    }
}