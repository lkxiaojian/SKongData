package com.zky.zky_map.mvvm.viewmodle

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.zky.basics.common.event.SingleLiveEvent
import com.zky.basics.common.mvvm.viewmodel.BaseViewModel
import com.zky.basics.common.util.showCustomDialog
import com.zky.basics.common.util.spread.showToast
import com.zky.basics.common.util.view.CustomDialog
import com.zky.zky_map.bean.MapViewBean
import com.zky.zky_map.R
import com.zky.basics.api.common.entity.UploadAdressBean
import com.zky.zky_map.mvvm.model.MapModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by lk
 * Date 2020-02-24
 * Time 15:43
 * Detail:
 */
class MapViewModle(application: Application, model: MapModel) :
    BaseViewModel<MapModel>(application, model) {

    private var mVoidSingleLiveEvent: SingleLiveEvent<String>? = null
    private val tipList = arrayListOf<String>("单机地图添加定位点", "单机地图添加多个点位以连线", "单机地图添加多个点位以连面")
    var mapViewBean = ObservableField<MapViewBean>()
    var netPoint = ObservableField<UploadAdressBean>()
    var netLine = ObservableField<List<UploadAdressBean>>()
    var netPlane = ObservableField<List<UploadAdressBean>>()

    init {
        val data = MapViewBean()
        data.lineTYpe = 0
        data.tipText = tipList[0]
        data.showTip = true
        data.dianShowWT = true
        data.lineOrSurfaceTipText = "修改点位"
        data.mapSelect = R.drawable.wx_xia
        mapViewBean.set(data)
    }

    fun startClick(view: View) {
        when (view.id) {
            R.id.aiv_s_t -> {
                //点击选择地图
                val data = mapViewBean.get()
                data?.let {
                    mapViewBean.get()?.mapSelectShow = !it.mapSelectShow
                    if (it.mapSelectShow) {
                        if (it.wxOrLx) {
                            mapViewBean.get()?.mapSelect = R.drawable.wx_shang
                        } else {
                            mapViewBean.get()?.mapSelect = R.drawable.lx_shang
                        }
                    }
                }
            }


            R.id.aiv_map_w -> {
                if (mapViewBean.get()?.wxOrLx == true) {
                    mapViewBean.get()?.mapSelectShow = false
                    return
                }

                mapViewBean.get()?.mapSelectShow = false
                mapViewBean.get()?.wxOrLx = true
                mapViewBean.get()?.mapSelect = R.drawable.wx_xia
            }

            R.id.aiv_map_lx -> {
                if (mapViewBean.get()?.wxOrLx == false) {
                    mapViewBean.get()?.mapSelectShow = false
                    return
                }
                mapViewBean.get()?.mapSelectShow = false
                mapViewBean.get()?.wxOrLx = false
                mapViewBean.get()?.mapSelect = R.drawable.lx_xia
            }


            R.id.cl_dian -> {
                mapViewBean.get()?.tipText = tipList[0]
                mapViewBean.get()?.lineTYpe = 0
                if (mapViewBean.get()?.dianData == null) {
                    mapViewBean.get()?.showTip = true
                    mapViewBean.get()?.dianShowWT = true
                    mapViewBean.get()?.showLineOrSurfaceModify = false
                } else {
                    mapViewBean.get()?.showLineOrSurfaceModify = true

                }
                mapViewBean.get()?.lineOrSurfaceTipText = "修改点位"
            }
            R.id.cl_xian -> {
                mapViewBean.get()?.tipText = tipList[1]
                mapViewBean.get()?.lineTYpe = 1
                if (mapViewBean.get()?.lineData == null) {
                    mapViewBean.get()?.showTip = true
                    mapViewBean.get()?.dianShowWT = false
                    mapViewBean.get()?.showLineOrSurfaceModify = false
                } else {
                    mapViewBean.get()?.showLineOrSurfaceModify = true

                }

                mapViewBean.get()?.lineOrSurfaceTipText = "修改线信息"

            }
            R.id.cl_mian -> {
                mapViewBean.get()?.tipText = tipList[2]
                mapViewBean.get()?.lineTYpe = 2
                if (mapViewBean.get()?.surfaceData == null) {
                    mapViewBean.get()?.showTip = true
                    mapViewBean.get()?.dianShowWT = false
                    mapViewBean.get()?.showLineOrSurfaceModify = false
                } else {
                    mapViewBean.get()?.showLineOrSurfaceModify = true
                }
                mapViewBean.get()?.lineOrSurfaceTipText = "修改面信息"
            }
            R.id.aiv_dw -> {
                getmVoidSingleLiveEvent().value = "argisdingwei"
            }
            R.id.atv_dian_sure -> {
                //微调 确定
                getmVoidSingleLiveEvent().value = "atv_dian_sure"
                mapViewBean.get()?.dianShowWT = false
                mapViewBean.get()?.showLineOrSurfaceModify = true
            }

            R.id.atv_dian_cancle -> {
                //微调 取消
                getmVoidSingleLiveEvent().value = "atv_dian_cancle"
                mapViewBean.get()?.dianShowWT = false

            }
            R.id.atv_cancle -> {
                // 线面 取消
                getmVoidSingleLiveEvent().value = "atv_cancle"
                mapViewBean.get()?.dianShowWT = false
            }

            R.id.atv_sure -> {
                // 线面 确定
                getmVoidSingleLiveEvent().value = "atv_sure"
                mapViewBean.get()?.showSureModify = false
                mapViewBean.get()?.showTip = false
                mapViewBean.get()?.showLineOrSurfaceModify = true
            }
            R.id.atv_repal -> {
                // 线面 撤销
                getmVoidSingleLiveEvent().value = "repal"
            }
            R.id.abt_modify -> {
                showCustomDialog(
                    view.context,
                    "确认修改",
                    "",
                    "修改改点位会清空当前${mapViewBean.get()?.getLineType()}数据信息",
                    "取消", "确认"
                ).setOnItemClickListener(object : CustomDialog.OnItemClickListener {
                    override fun onSure() {
                        //修改
                        mapViewBean.get()?.showSureModify = false
                        mapViewBean.get()?.showLineOrSurfaceModify = false
                        mapViewBean.get()?.showTip = true
                        getmVoidSingleLiveEvent().value = "atv_cancle"
                    }

                    override fun onDismiss() {

                    }

                })

            }

        }
    }


    fun insertOrUpdateSpaceData(type: String, spaceDataList: List<UploadAdressBean>) {
        launchUI({
            val toJson = Gson().toJson(spaceDataList)
            mModel.insertOrUpdateSpaceData(type, toJson)
        }, object : NetError {
            override fun getError(e: Exception) {
                "上传位置失败".showToast()
            }
        })
    }


    fun getSpaceDataAll() {
        launchUI({
            val bean = mModel.getSpaceDataAll()
            bean?.let {
                if (!bean.point.isNullOrEmpty()) {
                    netPoint.set(it.point[0])
                    mapViewBean.get()?.dianShowWT = false
                    mapViewBean.get()?.showLineOrSurfaceModify = true
                    getmVoidSingleLiveEvent().value = "netPoint"
                }
                if (!bean.line.isNullOrEmpty()) {
                    netLine.set(bean.line)
                    getmVoidSingleLiveEvent().value = "netLine"


                    mapViewBean.get()?.showSureModify = false
                    mapViewBean.get()?.showTip = false
                    mapViewBean.get()?.showLineOrSurfaceModify = true
                }

                if (!bean.plane.isNullOrEmpty()) {
                    netPlane.set(bean.plane)
                    mapViewBean.get()?.showSureModify = false
                    mapViewBean.get()?.showTip = false
                    mapViewBean.get()?.showLineOrSurfaceModify = true
                    getmVoidSingleLiveEvent().value = "netPlane"

                }
            }
        })
    }


    fun getmVoidSingleLiveEvent(): SingleLiveEvent<String> {
        return createLiveData(mVoidSingleLiveEvent).also {
            mVoidSingleLiveEvent = it
        }
    }

    fun delayTwo(value: String, timeMillis: Long) {
        viewModelScope.launch {
            delay(timeMillis)
            getmVoidSingleLiveEvent().value = value
        }
    }

}