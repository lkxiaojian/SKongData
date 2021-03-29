package com.zky.basics.common.util.Handset

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.Window
import androidx.annotation.RequiresApi
import com.zky.basics.common.util.Handset.HandsetMakers.checkPhoneMasker
import java.lang.Exception
import java.lang.reflect.Method

/**
 * 判断手机是否有刘海
 */
object HasNotch {

    fun checkPhoneHas(context: Context?): Boolean {
        var f = false
        val checkPhoneMasker = checkPhoneMasker()
        when (checkPhoneMasker) {

            1 -> f = hasNotchAtXiaoMi(context)
            2 -> f = hasNotchAtHuaWei(context)
            3 -> f = hasNotchAtVivo(context)
            4 -> f = hasNotchInOppo(context)

        }

        return f
    }


    private const val VIVO_NOTCH = 0x00000020 //是否有刘海

    /**
     * TODO  Vivo判断是否有刘海， Vivo的刘海高度小于等于状态栏高度
     *
     * @param context
     * @return
     */
    @SuppressLint("PrivateApi")
    fun hasNotchAtVivo(context: Context?): Boolean {
        var ret = false
        try {
            val classLoader = context?.classLoader
            val FtFeature = classLoader?.loadClass("android.util.FtFeature")
            val method = FtFeature?.getMethod("isFeatureSupport", Int::class.javaPrimitiveType)
            ret = method?.invoke(
                FtFeature,
                VIVO_NOTCH
            ) as Boolean
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
    @SuppressLint("PrivateApi")
    fun hasNotchAtXiaoMi(activity: Context?): Boolean {
        var result = 0
        try {
            val classLoader = activity?.classLoader
            val SystemPropertiesClass = classLoader?.loadClass("android.os.SystemProperties")
            val getInt =
                SystemPropertiesClass?.getMethod("getInt", String::class.java, Int::class.java)
            result =
                getInt?.invoke(SystemPropertiesClass, "ro.miui.notch",0) as Int
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result == 1
    }

    /**
     * 是否刘海 华为
     *
     * @param context
     * @return
     */
    fun hasNotchAtHuaWei(context: Context?): Boolean {
        var ret = false
        try {
            val cl = context?.classLoader
            val HwNotchSizeUtil = cl?.loadClass("com.huawei.android.util.HwNotchSizeUtil")
            val get = HwNotchSizeUtil?.getMethod("hasNotchInScreen")
            ret = get?.invoke(HwNotchSizeUtil) as Boolean
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
    fun hasNotchInOppo(context: Context?): Boolean {
        return context?.packageManager?.hasSystemFeature("com.oppo.feature.screen.heteromorphism") == true
    }


    /**
     * 判断vivo是否有刘海屏
     * https://swsdl.vivo.com.cn/appstore/developer/uploadfile/20180328/20180328152252602.pdf
     *
     * @param activity
     * @return
     */
    @SuppressLint("PrivateApi")
    fun hasNotchVIVO(context: Context?): Boolean {
        return try {
            val c = Class.forName("android.util.FtFeature")
            val get = c.getMethod("isFeatureSupport", Int::class.java)
            get.invoke(c, 0x20) as Boolean
        } catch (e: Exception) {
            e.printStackTrace();
            false
        }
    }
}