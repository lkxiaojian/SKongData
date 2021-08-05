package com.zky.basics.main.mvvm.viewmodel

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import com.zky.basics.common.event.SingleLiveEvent
import com.zky.basics.common.mvvm.viewmodel.BaseViewModel
import com.zky.basics.main.R
import com.zky.basics.main.entity.MapViewBean
import com.zky.basics.main.mvvm.model.MapModel


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
            R.id.location_get->{
                getmVoidSingleLiveEvent()?.value = "location_get"
            }
        }

    }


}