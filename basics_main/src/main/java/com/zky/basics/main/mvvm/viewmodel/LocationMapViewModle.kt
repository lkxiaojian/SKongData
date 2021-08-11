package com.zky.basics.main.mvvm.viewmodel

import android.app.Application
import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.view.OptionsPickerView
import com.esri.arcgisruntime.geometry.Point
import com.google.gson.Gson
import com.zhy.http.okhttp.OkHttpUtils
import com.zhy.http.okhttp.callback.StringCallback
import com.zky.basics.api.common.entity.GeocoderBean
import com.zky.basics.common.event.SingleLiveEvent
import com.zky.basics.common.mvvm.viewmodel.BaseViewModel
import com.zky.basics.common.util.spread.showToast
import com.zky.basics.main.R
import com.zky.basics.main.entity.MapViewBean
import com.zky.basics.main.mvvm.model.MapModel
import okhttp3.Call
import views.ViewOption.OptionsPickerBuilder


/**
 * Created by lk
 * Date 2019-11-12
 * Time 14:17
 * Detail:
 */
class LocationMapViewModle(application: Application, model: MapModel) :
    BaseViewModel<MapModel>(application, model) {
    private var mVoidSingleLiveEvent: SingleLiveEvent<String>? = null
    var data = ObservableField<MapViewBean>()
    var showSelectLocation = ObservableField<Boolean>()
    var dingweiIng = ObservableField<String>()
    var dingweiMessage = ObservableField<String>()
    var netShow = ObservableField<Boolean>()
    var pickerBuilder: OptionsPickerBuilder? = null
    var pickerView: OptionsPickerView<Any>? = null
    var locationMessage = ""

    init {
        dingweiIng.set("定位")
    }

    fun getmVoidSingleLiveEvent(): SingleLiveEvent<String>? {
        mVoidSingleLiveEvent = createLiveData(mVoidSingleLiveEvent) as SingleLiveEvent<String>?
        return mVoidSingleLiveEvent
    }


    fun startClick(view: View) {
        when (view.id) {
            R.id.location_me -> {
                getmVoidSingleLiveEvent()?.value = "dingwei"
            }
            R.id.location_blue -> {
                showSelectLocation.set(true)
            }
            R.id.okbtn -> {
                showSelectLocation.set(false)
                getmVoidSingleLiveEvent()?.value = "sure_paint"
            }
            R.id.canbtn -> {
                showSelectLocation.set(false)
            }
            R.id.school_location -> {
                getmVoidSingleLiveEvent()?.value = "school_location"

            }
            R.id.location_get -> {
                getmVoidSingleLiveEvent()?.value = "location_get"
            }
        }

    }


    fun getGeocoder(mapCenterPoint: Point?) {
        try {


            launchUI({
                val url =
//                    "https://apis.map.qq.com/ws/geocoder/v1/?location=${mapCenterPoint?.x},${mapCenterPoint?.y}" +
                    "https://apis.map.qq.com/ws/geocoder/v1/?location=39.984154,116.307490" +

                            "&key=5KKBZ-SDO6J-KXWFR-FCZZB-GW5ZE-TOBUZ&get_poi=1&poi_options=address_format=short;radius=100"
                OkHttpUtils.get().url(url).build().execute(object : StringCallback() {
                    override fun onResponse(response: String?, id: Int) {
                        val geocoder =
                            Gson().fromJson<GeocoderBean>(response, GeocoderBean::class.java)
                        val list = arrayListOf<String>()
                        geocoder?.let { it ->
                            if (it.status == 0) {
                                val pois = it.result.pois
                                pois.forEach {
                                    list.add(it.title)
                                }

                            } else {
                                it.message.showToast()
                            }
                        }

                        if (list.isNotEmpty()) {
                            showzpPicker(list)
                        }
                    }

                    override fun onError(call: Call?, e: java.lang.Exception?, id: Int) {
                        Log.e("", "")
                    }

                })


            })
        } catch (e: Exception) {
            Log.e("", "")
        }
    }


    fun showzpPicker(list: List<String>) {

        pickerView?.setPicker(list)
        pickerView?.show()
        pickerBuilder?.setOnOptionsSelectListener(OnOptionsSelectListener { options1, _, _, _ ->
            locationMessage = list[options1].toString()
            getmVoidSingleLiveEvent()?.value = "showCall"

        })
    }


}