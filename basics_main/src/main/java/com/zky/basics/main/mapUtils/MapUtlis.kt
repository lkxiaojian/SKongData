package com.zky.basics.common.util.mapUtils

import android.content.Context
import com.amap.api.maps.CoordinateConverter
import com.amap.api.maps.model.LatLng
import com.esri.arcgisruntime.geometry.Point
import com.esri.arcgisruntime.geometry.SpatialReferences
import com.zky.basics.main.mapUtils.LocationUtils

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
//        val toGPSPoint =  gcJo2toWGS84Point(t.latitude,t.longitude)
//        val point = Point(toGPSPoint.x, toGPSPoint.y, wgs)



        return point
    }

    fun TransfromGCJ(t: LatLng, context: Context): LatLng {
        val converter = CoordinateConverter(context)
        // CoordType.GPS 待转换坐标类型 WGS-84坐标系
        converter.from(CoordinateConverter.CoordType.GPS)
        // sourceLatLng待转换坐标点 LatLng类型
        converter.coord(t)
        // 执行转换操作
        val convert = converter.convert()
        // 116.38976 39.99216
//        val wsG84toGCJ02Point = wsG84toGCJ02Point(t.latitude, t.longitude)
//        val latLng = LatLng(wsG84toGCJ02Point.x, wsG84toGCJ02Point.y)
        return convert
    }
}