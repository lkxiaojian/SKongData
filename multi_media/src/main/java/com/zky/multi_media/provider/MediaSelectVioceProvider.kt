package com.zky.multi_media.provider

import android.content.Context
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.zky.basics.common.provider.IMediaSelectVoiceProvider
import com.zky.multi_media.fragment.MediaVoiceFragment.Companion.mediaSelctVoiceInstance

/**
 * TODO 音频选择
 *
 */
@Route(path = ARouterPath.MEDIA_SELECT_SHOW_VOICE)
class MediaSelectVioceProvider : IMediaSelectVoiceProvider {
    override fun mediaVoiceFragment(type: String): Fragment? {
      return  mediaSelctVoiceInstance()
    }


    override fun init(context: Context) {

    }
}