package com.zky.zky_map.mvvm.viewmodle

import android.app.Application
import android.view.View
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.zky.basics.common.event.SingleLiveEvent
import com.zky.basics.common.mvvm.viewmodel.BaseViewModel
import com.zky.basics.common.util.showCustomDialog
import com.zky.basics.common.util.view.CustomDialog
import com.zky.zky_map.MapViewBean
import com.zky.zky_map.R
import com.zky.zky_map.mvvm.model.MapModel
import kotlinx.android.synthetic.main.map_fragment.view.*
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

    init {
        val data = MapViewBean()
        data.lineTYpe = 0
        data.tipText = tipList[0]
        data.showTip = true
        data.dianShowWT = true
        data.lineOrSurfaceTipText = "修改点位"
        mapViewBean.set(data)
    }

    fun startClick(view: View) {
        when (view.id) {
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
                mapViewBean.get()?.dianShowWT=false
                mapViewBean.get()?.showLineOrSurfaceModify = true
            }

            R.id.atv_dian_cancle -> {
                //微调 取消
                getmVoidSingleLiveEvent().value = "atv_dian_cancle"
                mapViewBean.get()?.dianShowWT=false

            }
            R.id.atv_cancle -> {
                // 线面 取消
                getmVoidSingleLiveEvent().value = "atv_cancle"
                mapViewBean.get()?.dianShowWT=false
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