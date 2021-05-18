package com.zky.task_chain.provider

import ARouterPath.TASK_CHAIN
import android.content.Context
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.zky.basics.common.provider.ITaskChainProvider
import com.zky.task_chain.fragment.TaskChainFragment.Companion.getChainInstance

@Route(path = TASK_CHAIN, name = "task_chain")
class TaskChainProvider : ITaskChainProvider {
    override val taskChainFragment: Fragment?
        get() =getChainInstance()


    override fun init(context: Context) {}

}