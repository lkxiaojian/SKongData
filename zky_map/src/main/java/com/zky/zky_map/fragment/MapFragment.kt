package com.zky.zky_map.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.CoordinateConverter
import com.amap.api.maps.model.*
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment
import com.esri.arcgisruntime.geometry.*
import com.esri.arcgisruntime.geometry.Polygon
import com.esri.arcgisruntime.geometry.Polyline
import com.esri.arcgisruntime.layers.ArcGISTiledLayer
import com.esri.arcgisruntime.layers.Layer
import com.esri.arcgisruntime.mapping.ArcGISMap
import com.esri.arcgisruntime.mapping.Basemap
import com.esri.arcgisruntime.mapping.view.*
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol
import com.esri.arcgisruntime.symbology.SimpleFillSymbol
import com.esri.arcgisruntime.symbology.SimpleLineSymbol
import com.hjq.permissions.OnPermission
import com.hjq.permissions.XXPermissions
import com.zky.basics.api.splash.entity.Userinfo
import com.zky.basics.common.mvvm.BaseMvvmFragment
import com.zky.basics.common.util.PermissionToSetting
import com.zky.basics.common.util.spread.decodeParcelable
import com.zky.zky_map.BR
import com.zky.zky_map.R
import com.zky.basics.api.common.entity.UploadAdressBean
import com.zky.zky_map.databinding.MapFragmentBinding
import com.zky.zky_map.mvvm.factory.MapViewModelFactory
import com.zky.zky_map.mvvm.viewmodle.MapViewModle
import com.zky.zky_map.utils.LocationUtils
import com.zky.zky_map.utils.MapUtlis
import com.zky.zky_map.utils.MapUtlis.TransfromGCJ
import com.zky.zky_map.utils.MapUtlis.TransfromGps
import kotlinx.android.synthetic.main.map_fragment.*
import java.text.DecimalFormat

/**
 *create_time : 21-3-9 上午10:33
 *author: lk
 *description： MapFragment
 */
class MapFragment : BaseMvvmFragment<MapFragmentBinding, MapViewModle>() {
    private var mePoint: Point? = null
    private val wgs = SpatialReferences.getWgs84()
    private var meCampsiteSymbol: PictureMarkerSymbol? = null
    private var dianCampsiteSymbol: PictureMarkerSymbol? = null
    private var farmerSymbol: PictureMarkerSymbol? = null
    private var callout: Callout? = null
    private val graphicList = arrayListOf<Graphic>()
    private var pointCollection: PointCollection = PointCollection(SpatialReferences.getWgs84())
    val polygonPoints = PointCollection(SpatialReferences.getWgs84())
    private val lineSymbol =
        SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.parseColor("#FF6C0F"), 5f)
    private var mapCenterPoint: Point? = null

    //    # -0x7f00a8cd
    private val polygonFillSymbol =
        SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, -0x7f00a8cd, lineSymbol)
    private val df = DecimalFormat("#.00000")
    private lateinit var meLocation: GraphicsOverlay
    private lateinit var dianLocation: GraphicsOverlay
    private lateinit var lineGraphicsOverlay: GraphicsOverlay
    private lateinit var polygonGraphicsOverlay: GraphicsOverlay
    private lateinit var farmerOverlays: GraphicsOverlay
    private var dianMarker: Marker? = null
    private var gdPointLineMaker = arrayListOf<Marker>()
    private var gdPointLineLatLng = arrayListOf<LatLng>()

    private var gdPointSurfaceMaker = arrayListOf<Marker>()
    private var gdPointSurfaceLatLng = arrayListOf<LatLng>()
    private var addPolyline: com.amap.api.maps.model.Polyline? = null
    private var addPolygon: com.amap.api.maps.model.Polygon? = null
    private var userinfo: Userinfo? = null
    override fun onBindViewModel() = MapViewModle::class.java
    override fun onBindViewModelFactory() = MapViewModelFactory.getInstance(activity!!.application)


    override fun initViewObservable() {
        mViewModel?.getmVoidSingleLiveEvent()
            ?.observe(this, { a: String? ->
                when (a) {
                    "argisdingwei" -> {
                        if (mePoint == null) {
                            dingwei()
                            return@observe
                        }
                        if (mViewModel?.mapViewBean?.get()?.wxOrLx == true) {
                            map_view.setViewpointCenterAsync(mePoint)
                            mViewModel?.delayTwo("movePoint", 500)
                        } else if (mViewModel?.mapViewBean?.get()?.wxOrLx == false) {

                            val latLng = LatLng(mePoint!!.y, mePoint!!.x)
                            val transfromGCJ = MapUtlis.TransfromGCJ(latLng, mActivity)
                            mBinding?.gdMV?.map?.moveCamera(
                                CameraUpdateFactory
                                    .newLatLngZoom(transfromGCJ, 15f)
                            )

                        }

                    }
                    "movePoint" -> {
                        setLngAndLong()
                    }
                    "upLngAndLong" -> {
                        setLngAndLong()
                    }
                    "repal" -> {
                        repalLineOrPolygon()
                    }
                    "atv_sure" -> {
                        lineOrSufer()

                    }
                    "atv_cancle" -> {
                        cancleLineOrPolygon()
                    }
                    "atv_dian_sure" -> {
                        dianSure()
                    }
                    "netPoint"->{
                        netPoint()
                    }
                }
            })
    }

    private fun dianSure() {
        try {
            val wxOrLx = mViewModel?.mapViewBean?.get()?.wxOrLx
            wxOrLx?.let {
                if (it) {
                    mapCenterPoint = getMapCenterPoint()
                    mViewModel?.mapViewBean?.get()?.dianData = mapCenterPoint
                    initCallout()
                    drawGDPoint(LatLng(mapCenterPoint!!.y, mapCenterPoint!!.x))
                } else {
                    val target = mBinding?.gdMV?.map?.cameraPosition?.target
                    val toGPSPoint = LocationUtils.toGPSPoint(target!!.latitude, target.longitude)
                    mapCenterPoint = Point(toGPSPoint.longitude, toGPSPoint.latitude, wgs)
                    mViewModel?.mapViewBean?.get()?.dianData = mapCenterPoint
                    initCallout()
                    drawGDPoint(LatLng(target.latitude, target.longitude))
                }
                val list = arrayListOf<UploadAdressBean>()
                list.add(UploadAdressBean(mapCenterPoint!!.y, mapCenterPoint!!.x))
                mViewModel?.insertOrUpdateSpaceData("point", list)

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * TODO
     * 网络请求的点
     *
     */
    private fun netPoint(){
        val netPoint = mViewModel?.netPoint?.get()
        netPoint?.let {

            mapCenterPoint=Point(it.longitude, it.latitude, wgs)
            mViewModel?.mapViewBean?.get()?.dianData = mapCenterPoint
            initCallout()
            drawGDPoint(LatLng(mapCenterPoint!!.y, mapCenterPoint!!.x))

        }

    }

    override fun onBindVariableId() = BR.mapViewModle
    override fun onBindLayout() = R.layout.map_fragment
    override fun initView(view: View?) {


    }

    override fun initSaveView(savedInstanceState: Bundle?) {
        mBinding?.gdMV?.onCreate(savedInstanceState)
    }

    override fun initData() {
        userinfo = decodeParcelable<Userinfo>("user")
        showInitLoadView(true)
        initArgis()
        initGD()
        listener()
        showInitLoadView(false)
        mViewModel?.getSpaceDataAll()
    }

    private fun initGD() {
        //缩放按钮是否显示
        mBinding?.gdMV?.map?.uiSettings?.isZoomControlsEnabled = false


        var mLocationOption: AMapLocationClientOption? = null
        var mlocationClient = AMapLocationClient(activity)
//初始化定位参数
        mLocationOption = AMapLocationClientOption()
//设置返回地址信息，默认为true
        mLocationOption.isNeedAddress = true
//设置定位监听
//        mlocationClient.setLocationListener(this)
//设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.locationMode = AMapLocationClientOption.AMapLocationMode.Battery_Saving
//设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.interval = 2000
//设置定位参数
        mlocationClient.setLocationOption(mLocationOption)

//启动定位
        mlocationClient.startLocation()
        var firstLocation = true
        mlocationClient.setLocationListener {
            if (it != null && it.errorCode == 0 && firstLocation || mePoint == null) {
                val latitude = it.latitude
                val longitude = it.longitude
                if (longitude > 0) {
                    firstLocation = false
                }


                val markerOption = MarkerOptions()
                val latLng = LatLng(latitude, longitude)

                mePoint = MapUtlis.TransfromGps(latLng)

                markerOption.position(latLng)
                markerOption.draggable(true) //设置Marker可拖动
                markerOption.icon(
                    BitmapDescriptorFactory.fromBitmap(
                        BitmapFactory
                            .decodeResource(resources, R.drawable.my_location)
                    )
                )

                //  将Marker设置为贴地显示，可以双指下拉地图查看效果
                markerOption.isFlat = true //设置marker平贴地图效果
                mBinding?.gdMV?.map?.addMarker(markerOption)
                mBinding?.gdMV?.map?.moveCamera(
                    CameraUpdateFactory
                        .newLatLngZoom(latLng, 15f)
                )

            }

        }
// gd 地图移动监听
        mBinding?.gdMV?.map?.setOnCameraChangeListener(object : AMap.OnCameraChangeListener {
            override fun onCameraChange(p0: CameraPosition) {
                mapCenterPoint = MapUtlis.TransfromGps(p0.target)
                setLngAndLong()
            }

            override fun onCameraChangeFinish(p0: CameraPosition) {
                mapCenterPoint = MapUtlis.TransfromGps(p0.target)
                setLngAndLong()
                val bean = mViewModel?.mapViewBean?.get()
                if (bean?.lineTYpe == 0 && bean.dianData == null) {
                    mViewModel?.mapViewBean?.get()?.dianShowWT = true
                }

            }

        })

        mBinding?.gdMV?.map?.setOnMapClickListener {
            drawLineOrPolygon(null, it)
        }


    }


    private fun drawGDPoint(latLng: LatLng) {
        var la = latLng
        if (mViewModel?.mapViewBean?.get()?.wxOrLx == true) {
            val converter = CoordinateConverter(mActivity)
            // CoordType.GPS 待转换坐标类型 WGS-84坐标系
            converter.from(CoordinateConverter.CoordType.GPS)
            // sourceLatLng待转换坐标点 LatLng类型
            converter.coord(latLng)
            // 执行转换操作
            la = converter.convert()
        }
        val markerOption = MarkerOptions()
        markerOption.position(la)

        markerOption.title(userinfo?.username)
        markerOption.draggable(true) //设置Marker可拖动
        markerOption.icon(
            BitmapDescriptorFactory.fromBitmap(
                BitmapFactory
                    .decodeResource(resources, R.drawable.move_poiat)
            )
        )
        //  将Marker设置为贴地显示，可以双指下拉地图查看效果
        markerOption.isFlat = false //设置marker平贴地图效果
        if (dianMarker != null) {
            dianMarker?.remove()
        }
        dianMarker = mBinding?.gdMV?.map?.addMarker(markerOption)
        dianMarker?.`object` = "dian"
    }


    private fun listener() {
        map_view?.onTouchListener = object : DefaultMapViewOnTouchListener(activity, map_view) {
            override fun onTouch(view: View?, event: MotionEvent): Boolean {
                val bean = mViewModel?.mapViewBean?.get()
                val dianShowWT = bean?.dianShowWT
                dianShowWT?.let {
                    if (it) {
                        setLngAndLong()
                        if (event.action == MotionEvent.ACTION_UP) {
                            mViewModel?.delayTwo("upLngAndLong", 800)
                        }
                    }
                }
                if (bean?.lineTYpe == 0 && bean.dianData == null && callout != null && !callout!!.isShowing) {
                    mViewModel?.mapViewBean?.get()?.dianShowWT = true
                }
                return super.onTouch(view, event)
            }

            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                val type = mViewModel?.mapViewBean?.get()?.lineTYpe
                type?.let {
                    if (type == 0) {
                        return super.onSingleTapConfirmed(e)
                    }
                }
                drawLineOrPolygon(e, null)
                return super.onSingleTapConfirmed(e)
            }
        }
    }

    /**
     * TODO 线面确定
     *
     */
    private fun lineOrSufer() {
        val lineTYpe = mViewModel?.mapViewBean?.get()?.lineTYpe
        var type = "line"
        if (lineTYpe == 1) {
            mViewModel?.mapViewBean?.get()?.lineData = pointCollection
        } else if (lineTYpe == 2) {
            type = "plane"
            mViewModel?.mapViewBean?.get()?.lineData = polygonPoints
        }
        val list = arrayListOf<UploadAdressBean>()
        mViewModel?.mapViewBean?.get()?.lineData?.forEach {
            list.add(UploadAdressBean(it.x, it.y))
        }
        mViewModel?.insertOrUpdateSpaceData(type, list)
    }

    /**
     * TODO 取消线面
     *
     */
    private fun cancleLineOrPolygon() {
        val lineTYpe = mViewModel?.mapViewBean?.get()?.lineTYpe
        dianLocation.graphics.clear()
        var type = "point"
        when (lineTYpe) {
            0 -> {
                mViewModel?.mapViewBean?.get()?.dianData = null
                farmerOverlays.graphics.clear()
                callout?.dismiss()
                dianMarker?.remove()
            }
            1 -> {
                type = "line"
                pointCollection.clear()
                lineGraphicsOverlay.graphics.clear()
                mViewModel?.mapViewBean?.get()?.lineData = null

                addPolyline?.remove()
                gdPointLineMaker.forEach {
                    it.remove()
                }
                gdPointLineMaker.clear()
                gdPointLineLatLng.clear()

            }
            2 -> {
                type = "plane"
                polygonPoints.clear()
                polygonGraphicsOverlay.graphics.clear()
                mViewModel?.mapViewBean?.get()?.surfaceData = null

                addPolygon?.remove()
                gdPointSurfaceMaker.forEach {
                    it.remove()
                }
                gdPointSurfaceMaker.clear()
                gdPointSurfaceLatLng.clear()
            }
        }
        mViewModel?.insertOrUpdateSpaceData(type, arrayListOf())
    }

    /**
     * TODO 撤销 线面
     *
     */
    private fun repalLineOrPolygon() {
        try {
            val lineTYpe = mViewModel?.mapViewBean?.get()?.lineTYpe
            if (dianLocation.graphics.isNullOrEmpty()) {
                return
            }
            dianLocation.graphics.removeAt(dianLocation.graphics.size - 1)
            if (lineTYpe == 1) {
                pointCollection.removeAt(pointCollection.size - 1)
                lineGraphicsOverlay.graphics.clear()
                if (pointCollection.size > 1) {
                    val polyline = Polyline(pointCollection)
                    val polylineGraphic = Graphic(polyline, lineSymbol)
                    lineGraphicsOverlay.graphics.add(polylineGraphic)
                }
                val ma = gdPointLineMaker.last()
                ma.remove()
                gdPointLineMaker.remove(ma)
                gdPointLineLatLng.removeAt(gdPointLineLatLng.lastIndex)
                drawGdLine()
            } else if (lineTYpe == 2) {
                polygonPoints.removeAt(polygonPoints.size - 1)
                polygonGraphicsOverlay.graphics.clear()
                if (polygonPoints.size > 1) {
                    val polygon = Polygon(polygonPoints)
                    val polygonGraphic = Graphic(polygon, polygonFillSymbol)
                    polygonGraphicsOverlay.graphics.add(polygonGraphic)
                }


                val ma = gdPointSurfaceMaker.last()
                ma.remove()
                gdPointSurfaceMaker.remove(ma)
                gdPointSurfaceLatLng.removeAt(gdPointSurfaceLatLng.lastIndex)
                drawGdPylone()

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * TODO 画线 or 画面
     *
     * @param e
     */
    private fun drawLineOrPolygon(e: MotionEvent?, latLng: LatLng?) {
        try {


            mViewModel?.mapViewBean?.get()?.showSureModify = true

            val bean = mViewModel?.mapViewBean?.get() ?: return
            val lineTYpe = bean.lineTYpe
            var point: Point? = null
            var tmpLat: LatLng? = null
            if (bean.wxOrLx) {
                val mapPoint = android.graphics.Point(e!!.x.toInt(), e.y.toInt())
                val screenToLocation = map_view.screenToLocation(mapPoint)
                val pointGraphic = Graphic(screenToLocation, dianCampsiteSymbol)
                dianLocation.graphics.add(pointGraphic)
                graphicList.add(pointGraphic)
                point = GeometryEngine.project(screenToLocation, wgs) as Point
                tmpLat = TransfromGCJ(LatLng(point.y, point.x), mActivity)

            } else {
                tmpLat = latLng
                point = TransfromGps(tmpLat!!)
                val pointGraphic = Graphic(point, dianCampsiteSymbol)
                dianLocation.graphics.add(pointGraphic)
                graphicList.add(pointGraphic)
            }


            when (lineTYpe) {
                1 -> {
                    //线
                    pointCollection.add(point)
                    if (pointCollection.size > 1) {
                        val polyline = Polyline(pointCollection)
                        val polylineGraphic = Graphic(polyline, lineSymbol)
                        lineGraphicsOverlay.graphics.clear()
                        lineGraphicsOverlay.graphics.add(polylineGraphic)
                    }
                    gdPointLineLatLng.add(tmpLat)
                    val drawRemaker = drawRemaker(tmpLat, lineTYpe)
                    val marker = gdMV?.map?.addMarker(drawRemaker)
                    marker?.`object` = "line${gdPointSurfaceMaker.size}"
                    gdPointLineMaker.add(marker!!)
                    drawGdLine()

                }
                2 -> {
                    //面
                    polygonPoints.add(point)
                    if (polygonPoints.size > 1) {
                        val polygon = Polygon(polygonPoints)
                        val polygonGraphic = Graphic(polygon, polygonFillSymbol)
                        polygonGraphicsOverlay.graphics.clear()
                        polygonGraphicsOverlay.graphics.add(polygonGraphic)
                    }

                    gdPointSurfaceLatLng.add(tmpLat)

                    val drawRemaker = drawRemaker(tmpLat, lineTYpe)
                    val marker = gdMV?.map?.addMarker(drawRemaker)
                    marker?.`object` = "polygon${gdPointSurfaceMaker.size}"
                    gdPointSurfaceMaker.add(marker!!)
                    drawGdPylone()

                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun drawGdLine() {
        addPolyline?.remove()
        if (gdPointLineLatLng.size > 1) {
            addPolyline = gdMV?.map?.addPolyline(
                PolylineOptions().addAll(gdPointLineLatLng).width(10f)
                    .color(Color.argb(255, 108, 15, 1))
            )
        }
    }

    private fun drawGdPylone() {
        addPolygon?.remove()
        val polygonOptions = PolygonOptions()
        polygonOptions.addAll(gdPointSurfaceLatLng)
        polygonOptions.strokeWidth(15f) // 多边形的边框
//            .strokeColor(Color.argb(0, 255, 108, 15)) // 边框颜色
//            .fillColor(Color.argb(0, 250, 100, 0));   // 多边形的填充色
            .strokeColor(Color.argb(50, 1, 1, 1)) // 边框颜色
            .fillColor(Color.argb(1, 1, 1, 1));   // 多边形的填充色
        addPolygon = gdMV?.map?.addPolygon(polygonOptions)

    }


    private fun drawRemaker(latLng: LatLng, lineTYpe: Int): MarkerOptions {

        val markerOption = MarkerOptions()
        markerOption.position(latLng)
        if (lineTYpe == 1 && gdPointLineLatLng.size == 0) {
            markerOption.title("test")
        } else if (lineTYpe == 2 && gdPointSurfaceLatLng.size == 0) {
            markerOption.title("test")
        }

//
        markerOption.draggable(true) //设置Marker可拖动
        markerOption.icon(
            BitmapDescriptorFactory.fromBitmap(
                BitmapFactory
                    .decodeResource(resources, R.drawable.dian)
            )
        )
        markerOption.isFlat = true //设置marker平贴地图效果
        return markerOption

    }

    @SuppressLint("SetTextI18n")
    private fun setLngAndLong() {
        try {
            if (mViewModel?.mapViewBean?.get()?.wxOrLx == true) {
                mapCenterPoint = getMapCenterPoint()
            }
            atv_latitude.text = "经度:${df.format(mapCenterPoint?.x)} "
            atv_longitude.text = "维度:${df.format(mapCenterPoint?.y)}"
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun initArgis() {
        try {
            ArcGISRuntimeEnvironment.setLicense("runtimelite,1000,rud4770359459,none,RP5X0H4AH769H1MAH229")
            val gisTiledLayer =
                ArcGISTiledLayer("http://services.arcgisonline.com/arcgis/rest/services/World_Imagery/MapServer")
            val map = ArcGISMap(Basemap.Type.IMAGERY, 32.80243, 114.02644, 16)
            map.operationalLayers.add(gisTiledLayer as Layer?)
            map_view?.map = map
            map_view?.isAttributionTextVisible = false
            meLocation = GraphicsOverlay()
            dianLocation = GraphicsOverlay()
            lineGraphicsOverlay = GraphicsOverlay()
            farmerOverlays = GraphicsOverlay()
            polygonGraphicsOverlay = GraphicsOverlay()
            map_view?.graphicsOverlays?.add(meLocation)
            map_view?.graphicsOverlays?.add(dianLocation)
            map_view?.graphicsOverlays?.add(lineGraphicsOverlay)
            map_view?.graphicsOverlays?.add(polygonGraphicsOverlay)
            map_view?.graphicsOverlays?.add(farmerOverlays)
            val pinmeDrawable =
                activity?.let {
                    ContextCompat.getDrawable(
                        it,
                        R.drawable.my_location
                    )
                } as BitmapDrawable?

            meCampsiteSymbol = PictureMarkerSymbol.createAsync(pinmeDrawable).get()

            val dianDrawable =
                activity?.let {
                    ContextCompat.getDrawable(
                        it,
                        R.drawable.dian
                    )
                } as BitmapDrawable?
            dianCampsiteSymbol = PictureMarkerSymbol.createAsync(dianDrawable).get()

            val farmerDrawable =
                activity?.let {
                    ContextCompat.getDrawable(
                        it,
                        R.drawable.move_poiat
                    )
                } as BitmapDrawable?
            farmerSymbol = PictureMarkerSymbol.createAsync(farmerDrawable).get()
            dingwei()
            initCallout()
            mViewModel?.delayTwo("argisdingwei", 2000)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun initCallout() {
        try {
            farmerOverlays.graphics.clear()
            val graphic = Graphic(mapCenterPoint, farmerSymbol)
            farmerOverlays.graphics.add(graphic)
            val inflater = LayoutInflater.from(mActivity)
            val ly = inflater.inflate(R.layout.callout, null, false)
            callout = map_view.callout
            ly.findViewById<TextView>(R.id.tv_calloutInfo).text = userinfo?.username
            callout?.content = ly
            callout?.style = Callout.Style(mActivity, R.xml.callout)
            callout?.location = mapCenterPoint
            callout?.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun dingwei() {
        XXPermissions.with(activity).permission(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ).request(object :
            OnPermission {
            override fun hasPermission(granted: MutableList<String>?, all: Boolean) {
                if (all) {
                    val mLocationDisplay = map_view?.locationDisplay
                    mLocationDisplay?.autoPanMode = LocationDisplay.AutoPanMode.OFF
                    mLocationDisplay?.startAsync()
                    mLocationDisplay?.addLocationChangedListener { event ->
                        // 查看返回的定位信息
                        mLocationDisplay.defaultSymbol = meCampsiteSymbol
                        mePoint = event.location.position
                    }
                }
            }

            override fun noPermission(denied: MutableList<String>?, never: Boolean) {
                PermissionToSetting(activity!!, denied!!, never, "获取存储权限失败")
            }

        })
    }

    private fun getMapCenterPoint(): Point? {
        val center = map_view?.visibleArea?.extent?.center
        if (center != null) {
            return GeometryEngine.project(center, wgs) as Point
        }
        return null
    }


    override fun onPause() {
        super.onPause()
        map_view?.pause()
        gdMV?.onPause()
    }

    override fun onResume() {
        super.onResume()
        map_view?.resume()
        gdMV?.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        map_view?.resume()
        gdMV?.onDestroy()
    }

    companion object {
        fun newInstace(): Fragment {
            return MapFragment()
        }
    }
}