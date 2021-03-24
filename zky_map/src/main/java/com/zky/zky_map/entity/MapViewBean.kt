package com.zky.zky_map.bean

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.esri.arcgisruntime.geometry.Point
import com.esri.arcgisruntime.geometry.PointCollection
import com.zky.zky_map.BR

/**
 *create_time : 21-3-10 下午2:15
 *author: lk
 *description： MapViewBean
 */

class MapViewBean() : BaseObservable() {
    /**
     * 0 点 1线 2面
     */
    @get:Bindable
    @set:Bindable
    var lineTYpe: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.lineTYpe)
        }


    @get:Bindable
    @set:Bindable
    var dianShow: Boolean = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.dianShow)
        }

    @get:Bindable
    @set:Bindable
    var lineShow: Boolean = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.lineShow)
        }

    @get:Bindable
    @set:Bindable
    var mianShow: Boolean = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.mianShow)
        }


    @get:Bindable
    @set:Bindable
    var allShow: Boolean = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.allShow)
        }


    /**
     * 提示内容
     */
    @get:Bindable
    @set:Bindable
    var tipText: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.tipText)
        }

    /**
     * 提示修改内容
     */
    @get:Bindable
    @set:Bindable
    var lineOrSurfaceTipText: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.lineOrSurfaceTipText)
        }

    @get:Bindable
    @set:Bindable
    var showLineOrSurfaceModify: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.showLineOrSurfaceModify)
        }


    /**
     * 显示撤销 确定 取消
     */
    @get:Bindable
    @set:Bindable
    var showSureModify: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.showSureModify)
        }


    @get:Bindable
    @set:Bindable
    var dianData: Point? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.dianData)
        }

    @get:Bindable
    @set:Bindable
    var lineData: PointCollection? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.lineData)
        }

    @get:Bindable
    @set:Bindable
    var surfaceData: PointCollection? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.surfaceData)
        }

    @get:Bindable
    @set:Bindable
    var showTip: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.showTip)
        }

    @get:Bindable
    @set:Bindable
    var dianShowWT: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.dianShowWT)
        }


    fun getLineType(): String {
        var type = ""
        when (lineTYpe) {
            0 -> type = "点"
            1 -> type = "线"
            2 -> type = "面"
        }
        return type
    }

    /**
     *  0 卫星上 1 卫星下  2 路线上  3 路线下
     */
    @get:Bindable
    @set:Bindable
    var mapSelect: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.mapSelect)
        }


    @get:Bindable
    @set:Bindable
    var mapSelectShow: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.mapSelectShow)
        }

    /**
     *  true 卫星 false 路线
     */
    @get:Bindable
    @set:Bindable
    var wxOrLx: Boolean = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.wxOrLx)
        }
}