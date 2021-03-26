package com.zky.zky_mine.fragment


import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.zky.basics.common.mvvm.BaseMvvmFragment
import com.zky.zky_mine.BR
import com.zky.zky_mine.R
import com.zky.zky_mine.mvvm.factory.MineViewModelFactory
import com.zky.zky_mine.mvvm.viewmodle.MineViewModle

/**
 * Created by lk
 * Date 2019-10-28
 * Time 16:45
 * Detail:
 */
class MainMineFragment : BaseMvvmFragment<ViewDataBinding, MineViewModle>() {
    override fun initView(view: View?) {
    }

    override fun initData() {
        mViewModel?.getAppToken()
    }

    override fun onBindViewModel() = MineViewModle::class.java

    override fun onBindViewModelFactory() = MineViewModelFactory.getInstance(activity!!.application)

    override fun initViewObservable() {

    }

    override fun onBindVariableId(): Int {
        return BR.mineViewModle
    }

    companion object {
        @JvmStatic
        fun newInstance(): Fragment {
            return MainMineFragment()
        }
    }

    override fun onBindLayout() = R.layout.main_mine_fragment

    override fun getToolbarTitle() = ""

    override fun enableToolbar() = false

}