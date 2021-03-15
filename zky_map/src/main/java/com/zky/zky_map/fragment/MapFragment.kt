package com.zky.zky_map.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment
import com.esri.arcgisruntime.geometry.*
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
import com.zky.basics.common.mvvm.BaseMvvmFragment
import com.zky.basics.common.util.PermissionToSetting
import com.zky.zky_map.BR
import com.zky.zky_map.R
import com.zky.zky_map.databinding.MapFragmentBinding
import com.zky.zky_map.mvvm.factory.MapViewModelFactory
import com.zky.zky_map.mvvm.viewmodle.MapViewModle
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

    //    val blueOutlineSymbol = SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, -0xff9c01, 2f)
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
                        map_view.setViewpointCenterAsync(mePoint)
                        mViewModel?.delayTwo("movePoint", 500)
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
                }
            })
    }

    private fun dianSure() {
        mapCenterPoint = getMapCenterPoint()
        mViewModel?.mapViewBean?.get()?.dianData = mapCenterPoint
        //设置最大的长宽
        initCallout()
    }

    override fun onBindVariableId() = BR.mapViewModle
    override fun onBindLayout() = R.layout.map_fragment
    override fun initView(view: View?) {


    }

    override fun initSaveView(savedInstanceState: Bundle?) {
        mBinding?.gdMV?.onCreate(savedInstanceState)
    }

    override fun initData() {
        initArgis()
        initGD()
        listener()
    }

    private fun initGD() {
//        val myLocationStyle: MyLocationStyle
//        myLocationStyle =
//            MyLocationStyle() //初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
//
//        myLocationStyle.interval(2000) //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式。
//
//        mBinding?.gdMV?.map?.myLocationStyle = myLocationStyle //设置定位蓝点的Style
////设置默认定位按钮是否显示，非必需设置。
////        mBinding?.gdMV?.map?.uiSettings?.isMyLocationButtonEnabled = true
//        mBinding?.gdMV?.map?.isMyLocationEnabled =
//            true // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
//缩放按钮是否显示
        mBinding?.gdMV?.map?.uiSettings?.isZoomControlsEnabled = false


        var mLocationOption: AMapLocationClientOption? = null
        var mlocationClient = AMapLocationClient(activity)
//初始化定位参数
//初始化定位参数
        mLocationOption = AMapLocationClientOption()
//设置返回地址信息，默认为true
//设置返回地址信息，默认为true
        mLocationOption.setNeedAddress(true)
//设置定位监听
//设置定位监听
//        mlocationClient.setLocationListener(this)
//设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
//设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy)
//设置定位间隔,单位毫秒,默认为2000ms
//设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000)
//设置定位参数
//设置定位参数
        mlocationClient.setLocationOption(mLocationOption)
// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
// 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
// 在定位结束后，在合适的生命周期调用onDestroy()方法
// 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
//启动定位
// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
// 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
// 在定位结束后，在合适的生命周期调用onDestroy()方法
// 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
//启动定位
        mlocationClient.startLocation()


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
                drawLineOrPolygon(e)
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
        if (lineTYpe == 1) {
            mViewModel?.mapViewBean?.get()?.lineData = pointCollection
        } else if (lineTYpe == 2) {
            mViewModel?.mapViewBean?.get()?.lineData = polygonPoints
        }
    }

    /**
     * TODO 取消线面
     *
     */
    private fun cancleLineOrPolygon() {
        val lineTYpe = mViewModel?.mapViewBean?.get()?.lineTYpe
        dianLocation.graphics.clear()
        when (lineTYpe) {
            0 -> {
                mViewModel?.mapViewBean?.get()?.dianData = null
                farmerOverlays.graphics.clear()
                callout?.dismiss()
            }
            1 -> {
                pointCollection.clear()
                lineGraphicsOverlay.graphics.clear()
                mViewModel?.mapViewBean?.get()?.lineData = null
            }
            2 -> {
                polygonPoints.clear()
                polygonGraphicsOverlay.graphics.clear()
                mViewModel?.mapViewBean?.get()?.surfaceData = null
            }
        }
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
            } else if (lineTYpe == 2) {
                polygonPoints.removeAt(polygonPoints.size - 1)
                polygonGraphicsOverlay.graphics.clear()
                if (polygonPoints.size > 1) {
                    val polygon = Polygon(polygonPoints)
                    val polygonGraphic = Graphic(polygon, polygonFillSymbol)
                    polygonGraphicsOverlay.graphics.add(polygonGraphic)
                }
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
    private fun drawLineOrPolygon(e: MotionEvent) {
        mViewModel?.mapViewBean?.get()?.showSureModify = true

        val mapPoint = android.graphics.Point(e.x.toInt(), e.y.toInt())
        val screenToLocation = map_view.screenToLocation(mapPoint)
        val pointGraphic = Graphic(screenToLocation, dianCampsiteSymbol)
        dianLocation.graphics.add(pointGraphic)
        graphicList.add(pointGraphic)
        val point = GeometryEngine.project(screenToLocation, wgs) as Point
        val type = mViewModel?.mapViewBean?.get()?.lineTYpe
        type?.let {
            when (it) {
                1 -> {
                    //线
                    pointCollection.add(point)
                    if (pointCollection.size > 1) {
                        val polyline = Polyline(pointCollection)
                        val polylineGraphic = Graphic(polyline, lineSymbol)
                        lineGraphicsOverlay.graphics.clear()
                        lineGraphicsOverlay.graphics.add(polylineGraphic)
                    }
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
                }
            }

        }
    }

    @SuppressLint("SetTextI18n")
    private fun setLngAndLong() {
        try {
            mapCenterPoint = getMapCenterPoint()
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
    }

    override fun onResume() {
        super.onResume()
        map_view?.resume()
    }

    companion object {
        fun newInstace(): Fragment {
            return MapFragment()
        }
    }
}