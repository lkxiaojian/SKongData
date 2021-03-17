package com.zky.zky_map.utils

import android.content.Context
import com.amap.api.maps.CoordinateConverter
import com.amap.api.maps.model.LatLng
import com.esri.arcgisruntime.geometry.Point
import com.esri.arcgisruntime.geometry.SpatialReferences

/**
 *create_time : 21-3-17 上午8:34
 *author: lk
 *description： MapUtlis
 */
object MapUtlis {
    private val wgs = SpatialReferences.getWgs84()
    fun TransfromGps(t: LatLng): Point {
        val toGPSPoint = LocationUtils.toGPSPoint(t.latitude, t.longitude)
        val point = Point(toGPSPoint.longitude, toGPSPoint.latitude, wgs)
        return point
    }

    fun TransfromGCJ(t: LatLng,context: Context): LatLng {
        val converter = CoordinateConverter(context)
        // CoordType.GPS 待转换坐标类型 WGS-84坐标系
        converter.from(CoordinateConverter.CoordType.GPS)
        // sourceLatLng待转换坐标点 LatLng类型
        converter.coord(t)
        // 执行转换操作
        val convert = converter.convert()
        return convert
    }
}