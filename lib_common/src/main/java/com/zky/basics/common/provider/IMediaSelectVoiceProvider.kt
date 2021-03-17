package com.zky.basics.common.provider

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.template.IProvider

/**
 * TODO 音频选择
 *
 */
interface IMediaSelectVoiceProvider : IProvider {
    fun mediaVoiceFragment(type:String): Fragment?
}