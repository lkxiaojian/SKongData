package com.zky.zky_mine.activity

import android.content.Intent
import com.alibaba.android.arouter.facade.annotation.Route
import com.zky.basics.common.mvvm.BaseActivity
import com.zky.zky_mine.R
import com.zky.zky_mine.fragment.MainMineFragment


@Route(path = ARouterPath.MINE_MAIN, group = ARouterPath.GROUP_MINE)
class ZkyJybMainActivity : BaseActivity() {

    override fun onBindLayout() = R.layout.activity_zky_jyb_main
    override fun initView() {
//        NotchScreenManager.getInstance().getNotchInfo()
        supportFragmentManager.beginTransaction().add(R.id.fl_mine, MainMineFragment()).commit()
    }

    override fun initData() {
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (supportFragmentManager.fragments.size > 0) {
            val fragments = supportFragmentManager.fragments
            fragments.forEach {
                it.onActivityResult(requestCode, resultCode, data)
            }
        }
    }
    override val tootBarTitle = "我的"
    override val isFullScreen = false
    override fun onBindToolbarLayout() = R.layout.blue_common_toolbar


}
