package com.zky.support_salons.provider

import ARouterPath.SUPPORT_SALONS
import android.content.Context
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.zky.basics.common.provider.ISupportProvider
import com.zky.support_salons.fragement.SupportFragment.Companion.getSupportInstance

@Route(path = SUPPORT_SALONS, name = "support_salons")
class SupportSalonsProvider : ISupportProvider {

    override val supportFragment: Fragment?
        get() =getSupportInstance()


    override fun init(context: Context) {}

}