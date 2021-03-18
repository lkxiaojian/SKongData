package com.zky.multi_media.provider

import android.content.Context
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.zky.basics.common.provider.IMediaProvider
import com.zky.multi_media.fragment.MediaImageFragment.Companion.mediaImageInstance

@Route(path = ARouterPath.MEDIA)
class MediaProvider : IMediaProvider {
    override fun mediaFragment(code: String): Fragment? {
       return mediaImageInstance(code)
    }


    override fun init(context: Context) {

    }
}