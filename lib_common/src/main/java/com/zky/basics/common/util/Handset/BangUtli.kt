import android.content.Context
import android.os.Build
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.zky.basics.common.BuildConfig
import com.zky.basics.common.util.Handset.HandsetMakers
import com.zky.basics.common.util.Handset.HasNotch.checkPhoneHas


/**
 *create_time : 21-3-26 上午8:39
 *author: lk
 *description： BangUtli  刘海瓶
 */
object BangUtli {

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
            method.invoke(
                layoutParamsExObj,
                FLAG_NOTCH_SUPPORT
            )
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
            method.invoke(
                layoutParamsExObj,
                FLAG_NOTCH_SUPPORT
            )
        } catch (e: java.lang.Exception) {
            Log.e("test", "hw clear notch screen flag api error")
        }
    }

    /**
     * 刘海高度和状态栏的高度是一致的
     *
     * @param context
     * @return
     */
    fun getStatusBarHeight(context: Context?): Int {
        context?.let {
            val resId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
            return if (resId > 0) {
                context.resources.getDimensionPixelSize(resId)
            } else 0
        }
        return 0
    }

    fun setCJViewPading(view: View?) {
        setView(view)
    }

    fun setViewPading(view: View?, window: Window?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val checkPhoneHas = checkPhoneHas(view?.context)
            if (checkPhoneHas) {
                if (BuildConfig.DEBUG && HandsetMakers.checkPhoneMasker() == 0) {
                    val displayCutout = window?.decorView?.rootWindowInsets?.displayCutout
                    displayCutout?.let {
                        view?.setPadding(
                            it.safeInsetLeft, it.safeInsetTop, it.safeInsetRight, it.safeInsetBottom
                        )
                        view?.invalidate()
                    }
                } else {
                    //获取
                    setView(view)
                }

            }
        }
    }

    private fun setView(view: View?) {
        val statusBarHeight = getStatusBarHeight(view?.context)
        if (statusBarHeight > 0) {
            view?.setPadding(0, statusBarHeight, 0, 0)
            view?.invalidate()
        }
    }


}