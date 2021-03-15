package com.zky.zky_map.provider

import android.content.Context
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.zky.basics.common.provider.IMapProvider
import com.zky.zky_map.fragment.MapFragment.Companion.newInstace

/**
 *create_time : 21-3-15 上午9:15
 *author: lk
 *description： MapProvider
 */
@Route(path = ARouterPath.MAP_SHOW)
class MapProvider:IMapProvider {
    override val mapFragment: Fragment?
        get() = newInstace()

    override fun init(context: Context?) {
    }
}