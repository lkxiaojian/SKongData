package com.zky.zky_mine.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.smarx.notchlib.NotchScreenManager
import com.zky.basics.common.mvvm.BaseActivity
import com.zky.basics.common.util.BangUtli
import com.zky.zky_mine.R
import com.zky.zky_mine.fragment.MainMineFragment
import kotlinx.android.synthetic.main.activity_zky_jyb_main.*
import kotlinx.coroutines.delay

@Route(path = ARouterPath.MINE_MAIN, group = ARouterPath.GROUP_MINE)
class ZkyJybMainActivity : BaseActivity() {

    override fun onBindLayout() = R.layout.activity_zky_jyb_main
    override fun initView() {
//        NotchScreenManager.getInstance().getNotchInfo()
        supportFragmentManager.beginTransaction().add(R.id.fl_mine, MainMineFragment()).commit()
    }

    override fun initData() {
    }

    override val tootBarTitle = "我的"
    override val isFullScreen = true
    override fun onBindToolbarLayout() = R.layout.blue_common_toolbar


}
