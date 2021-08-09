package com.zky.basics.main.activity.location


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import android.location.LocationManager
import android.view.LayoutInflater
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment
import com.esri.arcgisruntime.geometry.GeometryEngine
import com.esri.arcgisruntime.geometry.Point
import com.esri.arcgisruntime.geometry.SpatialReference
import com.esri.arcgisruntime.layers.ArcGISTiledLayer
import com.esri.arcgisruntime.layers.Layer
import com.esri.arcgisruntime.mapping.ArcGISMap
import com.esri.arcgisruntime.mapping.Basemap
import com.esri.arcgisruntime.mapping.view.Callout
import com.esri.arcgisruntime.mapping.view.Graphic
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay
import com.esri.arcgisruntime.mapping.view.LocationDisplay
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol
import com.hjq.permissions.OnPermission
import com.hjq.permissions.XXPermissions
import com.zky.basics.api.common.entity.LocationPoint
import com.zky.basics.common.mvvm.BaseMvvmActivity
import com.zky.basics.common.util.GpsUtil
import com.zky.basics.common.util.NetUtil
import com.zky.basics.common.util.PermissionToSetting
import com.zky.basics.common.util.ToastUtil
import com.zky.basics.common.util.spread.showToast
import com.zky.basics.main.BR
import com.zky.basics.main.R
import com.zky.basics.main.databinding.ActivitySchoolLocationBinding
import com.zky.basics.main.mvvm.factory.MainViewModelFactory
import com.zky.basics.main.mvvm.viewmodel.LocationMapViewModle
import org.greenrobot.eventbus.EventBus
import java.text.DecimalFormat

@Route(path = ARouterPath.LOCATION, group = ARouterPath.GROUP_APP)
class SchoolLocationActivity :
    BaseMvvmActivity<ActivitySchoolLocationBinding, LocationMapViewModle>() {

    private lateinit var meLocation: GraphicsOverlay
    private var mePoint: Point? = null
    private var callout: Callout? = null
    private val wgs = SpatialReference.create(4326)
    private val df = DecimalFormat("#.00000")
    private var schooL_NAME = ""
    private var mapCenterPoint: Point? = null
    private var meCampsiteSymbol: PictureMarkerSymbol? = null
    private var farmerSymbol: PictureMarkerSymbol? = null
    private lateinit var farmerOverlays: GraphicsOverlay
    private var locMag: LocationManager? = null
    private var net = true
    override fun onBindViewModel() = LocationMapViewModle::class.java


    override fun onBindViewModelFactory() = MainViewModelFactory.getInstance(application)

    override fun initViewObservable() {
        if (NetUtil.checkNet()) {
            mViewModel?.netShow?.set(true)

        }else{
            net = false
            mViewModel?.netShow?.set(false)
        }
        mViewModel?.getmVoidSingleLiveEvent()?.observe(this, Observer {
            when (it) {
                "dingwei" -> {
                    if (mePoint == null) {
                        dingwei()
                        return@Observer
                    }
                    mBinding?.mapSchoolView?.setViewpointCenterAsync(mePoint)
                }
                "sure_paint" -> {
                    dian()
                }
                "school_location" -> {
                    if (mapCenterPoint == null) {
                        return@Observer
                    }
                    mBinding?.mapSchoolView?.setViewpointCenterAsync(mapCenterPoint)
                }

                "location_get" -> {
                    if (!GpsUtil.isOPen(this)) {
                        "定位失败，请确定gps是否打开".showToast()
                        return@Observer
                    }
                    ToastUtil.showToast("定位中，请稍后。。。")
                    dingwei()
                }
            }
        })

        if (net) {
            ArcGISRuntimeEnvironment.setLicense("runtimelite,1000,rud4770359459,none,RP5X0H4AH769H1MAH229")
            val gisTiledLayer =
                ArcGISTiledLayer("http://services.arcgisonline.com/arcgis/rest/services/World_Imagery/MapServer")
            val map = ArcGISMap(Basemap.Type.IMAGERY, 32.80243, 114.02644, 16)
            map.operationalLayers.add(gisTiledLayer as Layer?)
            mBinding?.mapSchoolView?.map = map
            mBinding?.mapSchoolView?.isAttributionTextVisible = false
            meLocation = GraphicsOverlay()
            farmerOverlays = GraphicsOverlay()
            mBinding?.mapSchoolView?.graphicsOverlays?.add(farmerOverlays)
            mBinding?.mapSchoolView?.graphicsOverlays?.add(meLocation)
            val pinmeDrawable =
                this.let {
                    ContextCompat.getDrawable(
                        it,
                        R.drawable.my_location
                    )
                } as BitmapDrawable?

            meCampsiteSymbol = PictureMarkerSymbol.createAsync(pinmeDrawable).get()

            val farmerDrawable =
                this.let {
                    ContextCompat.getDrawable(
                        it,
                        R.drawable.move_poiat
                    )
                } as BitmapDrawable?

            farmerSymbol = PictureMarkerSymbol.createAsync(farmerDrawable).get()

            dingwei()
        }
        val longitude = intent.getStringExtra("longitude")

        val latitude = intent.getStringExtra("latitude")
        if (!longitude.isNullOrEmpty() && !latitude.isNullOrEmpty()) {
            mapCenterPoint = Point(longitude.toDouble(), latitude.toDouble(), wgs)
            if(mePoint!=null){
                mViewModel?.dingweiMessage?.set("经度：${df.format(mePoint!!.x)}  纬度：${df.format(mePoint!!.y)}")
                mViewModel?.dingweiIng?.set("已定位")
            }

            if (net) {
                initCallout()
            }
        }

        locMag = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    }

    private fun dian() {
        mapCenterPoint = getMapCenterPoint()
        initCallout()
    }

    private fun getMapCenterPoint(): Point? {
        val center = mBinding?.mapSchoolView?.visibleArea?.extent?.center
        if (center != null) {
            return GeometryEngine.project(center, wgs) as Point
        }
        return null
    }


    private fun initCallout() {
        try {
            val inflater = LayoutInflater.from(this)
            val ly = inflater.inflate(R.layout.callout, null, false)
            callout = mBinding?.mapSchoolView?.callout

            farmerOverlays.graphics.clear()
            val graphic = Graphic(mapCenterPoint, farmerSymbol)
            graphic.isVisible = true
            farmerOverlays.graphics.add(graphic)
            val textView = ly.findViewById<TextView>(R.id.tv_calloutInfo)
            textView.text = "位置"
            callout?.setGeoElement(graphic, mapCenterPoint)
            callout?.content = ly
            callout?.style = Callout.Style(this, R.xml.callout)
//            callout?.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    @SuppressLint("CheckResult", "MissingPermission")
    private fun dingwei() {
        try {
            XXPermissions.with(this).permission(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ).request(object :
                OnPermission {
                override fun hasPermission(granted: MutableList<String>?, all: Boolean) {
                    if (all) {
                        if (net) {
                            val mLocationDisplay = mBinding?.mapSchoolView?.locationDisplay
                            mLocationDisplay?.autoPanMode = LocationDisplay.AutoPanMode.OFF
                            mLocationDisplay?.startAsync()
                            mLocationDisplay?.addLocationChangedListener { event ->
                                // 查看返回的定位信息
                                mLocationDisplay.defaultSymbol = meCampsiteSymbol
                                mePoint = event.location.position
                            }
                        } else {
                            var providers = locMag?.allProviders
                            var bestLocation: Location? = null
                            for (provider in providers!!) {
                                if (provider != "passive" && provider != "local_database") {
                                    continue
                                }
                                var l: Location? =
                                    locMag?.getLastKnownLocation(provider) ?: continue
                                if (bestLocation == null || l!!.accuracy < bestLocation.accuracy) {
                                    bestLocation = l
                                }

                            }
                            if (bestLocation == null || (bestLocation?.longitude == 0.0 && bestLocation?.latitude == 0.0)) {
                                "定位失败，请确定gps是否打开".showToast()
                                return
                            }
                            mePoint = Point(bestLocation.longitude, bestLocation.latitude)
                            mapCenterPoint = mePoint
                            mViewModel?.dingweiMessage?.set("经度：${df.format(mePoint!!.x)}  纬度：${df.format(mePoint!!.y)}")
                            mViewModel?.dingweiIng?.set("已定位")
                        }


                    }
                }

                override fun noPermission(denied: MutableList<String>?, never: Boolean) {
                    PermissionToSetting(this@SchoolLocationActivity, denied!!, never, "获取存储权限失败")
                }

            })
        } catch (e: Exception) {

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (mapCenterPoint != null) {
            EventBus.getDefault().postSticky(
                LocationPoint(
                    df.format(mapCenterPoint?.x).toDouble(),
                    df.format(mapCenterPoint?.y).toDouble()
                )
            )

        }
    }

    override fun onBindVariableId() = BR.slViewModel

    override fun onBindLayout() = R.layout.activity_school_location

    override val tootBarTitle = "位置"


}
