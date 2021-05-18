package com.zky.task_chain.fragment

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.Fragment
import com.zky.basics.common.mvvm.BaseFragment
import com.zky.task_chain.R

/**
 *create_time : 2021/5/18 上午9:13
 *author: lk
 *description： TaskChainFragment
 */
class TaskChainFragment : BaseFragment() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private val fragment = TaskChainFragment()
        fun getChainInstance(): Fragment {
            return fragment
        }
    }

    override fun onBindLayout() = R.layout.task_chain_fragment
    override fun initView(view: View?) {

    }

    override fun initData() {
    }

}