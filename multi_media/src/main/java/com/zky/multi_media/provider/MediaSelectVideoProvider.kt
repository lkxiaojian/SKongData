package com.zky.multi_media.provider

import android.content.Context
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.zky.basics.common.provider.IMediaSelectVideoProvider
import com.zky.multi_media.fragment.MediaVideoFragment.Companion.mediaVoiceInstance

@Route(path = ARouterPath.MEDIA_SELECT_VIDEO)
class MediaSelectVideoProvider : IMediaSelectVideoProvider {
    override val mediaVideoFragment: Fragment?
        get() = mediaVoiceInstance()

    override fun init(context: Context) {

    }
}