package com.zky.zky_mine.provider

import android.content.Context
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.zky.basics.common.provider.IMineProvider
import com.zky.zky_mine.fragment.MainMineFragment.Companion.newInstance

@Route(path = "/mine/mineFragment", name = "mine")
class MineProvider : IMineProvider {
    override fun init(context: Context) {}
    override val mainMineFragment: Fragment
        get() = newInstance()
}