package com.zky.basics.common.provider

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.template.IProvider

/**
 * TODO 视频
 *
 */
interface IMediaSelectVideoProvider : IProvider {
    val mediaVideoFragment: Fragment?
}