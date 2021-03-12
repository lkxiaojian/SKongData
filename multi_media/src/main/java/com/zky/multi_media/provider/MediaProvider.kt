package com.zky.multi_media.provider

import android.content.Context
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.zky.basics.common.provider.IMediaProvider
import com.zky.multi_media.fragment.MediaImageFragment.Companion.mediaImageInstance

@Route(path = ARouterPath.MEDIA, name = "多媒体")
class MediaProvider : IMediaProvider {
    override val mediaFragment: Fragment?
        get() = mediaImageInstance()

    override fun init(context: Context) {

    }


}