package com.zky.basics.common.provider

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.template.IProvider

interface ISupportProvider : IProvider {
    val supportFragment: Fragment?
}