package com.zky.basics.common.util

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import java.lang.reflect.Method


/**
 *create_time : 21-3-26 上午8:39
 *author: lk
 *description： BangUtli  刘海瓶
 */
object BangUtli {
    /**
     * 是否刘海 华为
     *
     * @param context
     * @return
     */
    fun hasNotchInScreen(context: Context): Boolean {
        var ret = false
        try {
            val cl: ClassLoader = context.classLoader
            val HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil")
            val get: Method = HwNotchSizeUtil.getMethod("hasNotchInScreen")
            ret = get.invoke(HwNotchSizeUtil) as Boolean
        } catch (e: ClassNotFoundException) {
            Log.e("test", "hasNotchInScreen ClassNotFoundException")
        } catch (e: NoSuchMethodException) {
            Log.e("test", "hasNotchInScreen NoSuchMethodException")
        } catch (e: Exception) {
            Log.e("test", "hasNotchInScreen Exception")
        }
        return ret
    }


    /********************
     * 判断该 OPPO 手机是否为刘海屏手机
     * @param context
     * @return
     */
    fun hasNotchInOppo(context: Context): Boolean {
        return context.packageManager.hasSystemFeature("com.oppo.feature.screen.heteromorphism")
    }

    /**
     * TODO  Vivo判断是否有刘海， Vivo的刘海高度小于等于状态栏高度
     *
     * @param context
     * @return
     */
    @SuppressLint("PrivateApi")
    fun hasNotchAtVivo(context: Context): Boolean {
        var ret = false
        try {
            val classLoader = context.classLoader
            val FtFeature = classLoader.loadClass("android.util.FtFeature")
            val method = FtFeature.getMethod("isFeatureSupport", Int::class.javaPrimitiveType)
            ret = method.invoke(FtFeature, VIVO_NOTCH) as Boolean
        } catch (e: ClassNotFoundException) {
            Log.e("Notch", "hasNotchAtVivo ClassNotFoundException")
        } catch (e: NoSuchMethodException) {
            Log.e("Notch", "hasNotchAtVivo NoSuchMethodException")
        } catch (e: java.lang.Exception) {
            Log.e("Notch", "hasNotchAtVivo Exception")
        } finally {
            return ret
        }
    }


    /**
     * 小米刘海屏判断.
     * @return 0 if it is not notch ; return 1 means notch
     * @throws IllegalArgumentException if the key exceeds 32 characters
     */

//    @RequiresApi(api = Build.VERSION_CODES.O)
//    open fun isNotchScreen(window: Window?): Boolean {
//        return "1" ==SystemProperties.getInstance().get("ro.miui.notch")
//    }

        /**
         * 获取刘海尺寸：width、height,int[0]值为刘海宽度 int[1]值为刘海高度。
         *
         * @param context
         * @return
         */
        fun getNotchSize(context: Context): IntArray? {
            var ret = intArrayOf(0, 0)
            try {
                val cl = context.classLoader
                val HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil")
                val get = HwNotchSizeUtil.getMethod("getNotchSize")
                ret = get.invoke(HwNotchSizeUtil) as IntArray
            } catch (e: ClassNotFoundException) {
                Log.e("test", "getNotchSize ClassNotFoundException")
            } catch (e: NoSuchMethodException) {
                Log.e("test", "getNotchSize NoSuchMethodException")
            } catch (e: java.lang.Exception) {
                Log.e("test", "getNotchSize Exception")
            }
            return ret
        }


        /**
         * 设置使用刘海区域
         *
         * @param window
         */
        fun setFullScreenWindowLayoutInDisplayCutout(window: Window?) {
            if (window == null) {
                return
            }
            try {
                val layoutParams: WindowManager.LayoutParams = window.getAttributes()
                val layoutParamsExCls = Class.forName("com.huawei.android.view.LayoutParamsEx")
                val con =
                    layoutParamsExCls.getConstructor(WindowManager.LayoutParams::class.java)
                val layoutParamsExObj: Any = con.newInstance(layoutParams)
                val method = layoutParamsExCls.getMethod("addHwFlags", Int::class.javaPrimitiveType)
                method.invoke(layoutParamsExObj, FLAG_NOTCH_SUPPORT)
            } catch (e: java.lang.Exception) {
                Log.e("test", "other Exception")
            }
        }

        /*刘海屏全屏显示FLAG*/
        const val FLAG_NOTCH_SUPPORT = 0x00010000


        /**
         * 设置应用窗口在华为刘海屏手机不使用刘海
         *
         * @param window 应用页面window对象
         */
        fun setNotFullScreenWindowLayoutInDisplayCutout(window: Window?) {
            if (window == null) {
                return
            }
            try {
                val layoutParams = window.attributes
                val layoutParamsExCls = Class.forName("com.huawei.android.view.LayoutParamsEx")
                val con = layoutParamsExCls.getConstructor(WindowManager.LayoutParams::class.java)
                val layoutParamsExObj = con.newInstance(layoutParams)
                val method =
                    layoutParamsExCls.getMethod("clearHwFlags", Int::class.javaPrimitiveType)
                method.invoke(layoutParamsExObj, FLAG_NOTCH_SUPPORT)
            } catch (e: java.lang.Exception) {
                Log.e("test", "hw clear notch screen flag api error")
            }
        }


        /*********
         * 1、声明全屏显示。
         *
         * 2、适配沉浸式状态栏，避免状态栏部分显示应用具体内容。
         *
         * 3、如果应用可横排显示，避免应用两侧的重要内容被遮挡。
         */


        /**
         * 刘海高度和状态栏的高度是一致的
         *
         * @param context
         * @return
         */
        fun getStatusBarHeight(context: Context): Int {
            val resId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
            return if (resId > 0) {
                context.resources.getDimensionPixelSize(resId)
            } else 0
        }







    const val VIVO_NOTCH = 0x00000020 //是否有刘海

    const val VIVO_FILLET = 0x00000008 //是否有圆角
    // 是否是小米手机
    fun isXiaomi(): Boolean {
        return "Xiaomi" == Build.MANUFACTURER
    }


    fun setViewPading(view:View?,window: Window?){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val displayCutout = window?.decorView?.rootWindowInsets?.displayCutout
            displayCutout?.let {
                view?.setPadding(
                    it.safeInsetLeft, it.safeInsetTop, it.safeInsetRight, it.safeInsetBottom
                )
                view?.invalidate()
            }
        }
    }




}