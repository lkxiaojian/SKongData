package com.zky.basics.common.util.Handset

import android.os.Build
import android.text.TextUtils
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*


object HandsetMakers {
    private const val TAG = "Rom"
    const val ROM_MIUI = "MIUI"
    const val ROM_EMUI = "EMUI"
    const val ROM_FLYME = "FLYME"
    const val ROM_OPPO = "OPPO"
    const val ROM_SMARTISAN = "SMARTISAN"
    const val ROM_VIVO = "VIVO"
    const val ROM_QIKU = "QIKU"

    private const val KEY_VERSION_MIUI = "ro.miui.ui.version.name"
    private const val KEY_VERSION_EMUI = "ro.build.version.emui"
    private const val KEY_VERSION_OPPO = "ro.build.version.opporom"
    private const val KEY_VERSION_SMARTISAN = "ro.smartisan.version"
    private const val KEY_VERSION_VIVO = "ro.vivo.os.version"
    private var sName: String? = null
    private var sVersion: String? = null

    /**
     *  检测手机厂家
     *  0 未知 1小米  2 华为 3 vivo  4 oppo 5 魅族  6  360  7三星
     */
    fun checkPhoneMasker(): Int {
        var i = 0
        when {
            isMiui() -> i = 1
            isEmui() -> i = 2
            isVivo() -> i = 3
            isOppo() -> i = 4
            isFlyme() -> i = 5
            is360() -> i = 6
            isSmartisan() -> i = 7
        }
        return i
    }

    private fun isEmui(): Boolean {
        return check(ROM_EMUI)
    }

    private fun isMiui(): Boolean {
        return check(ROM_MIUI)
    }

    private fun isVivo(): Boolean {
        return check(ROM_VIVO)
    }

    private fun isOppo(): Boolean {
        return check(ROM_OPPO)
    }

    private fun isFlyme(): Boolean {
        return check(ROM_FLYME)
    }

    private fun is360(): Boolean {
        return check(ROM_QIKU) || check("360")
    }

    private fun isSmartisan(): Boolean {
        return check(ROM_SMARTISAN)
    }

    private fun getName(): String? {
        if (sName == null) {
            check("")
        }
        return sName
    }

    private fun getVersion(): String? {
        if (sVersion == null) {
            check("")
        }
        return sVersion
    }

    private fun check(rom: String): Boolean {
        if (sName != null) {
            return sName == rom
        }
        if (!TextUtils.isEmpty(getProp(KEY_VERSION_MIUI).also { sVersion = it })) {
            sName = ROM_MIUI
        } else if (!TextUtils.isEmpty(getProp(KEY_VERSION_EMUI).also { sVersion = it })) {
            sName = ROM_EMUI
        } else if (!TextUtils.isEmpty(getProp(KEY_VERSION_OPPO).also { sVersion = it })) {
            sName = ROM_OPPO
        } else if (!TextUtils.isEmpty(getProp(KEY_VERSION_VIVO).also { sVersion = it })) {
            sName = ROM_VIVO
        } else if (!TextUtils.isEmpty(getProp(KEY_VERSION_SMARTISAN).also { sVersion = it })) {
            sName = ROM_SMARTISAN
        } else {
            sVersion = Build.DISPLAY
            if (sVersion?.toUpperCase(Locale.ROOT)?.contains(ROM_FLYME) == true) {
                sName = ROM_FLYME
            } else {
                sVersion = Build.UNKNOWN
                sName = Build.MANUFACTURER.toUpperCase(Locale.ROOT)
            }
        }
        return sName == rom
    }

    private fun getProp(name: String): String? {
        var line: String? = null
        var input: BufferedReader? = null
        try {
            val p = Runtime.getRuntime().exec("getprop $name")
            input = BufferedReader(InputStreamReader(p.inputStream), 1024)
            line = input.readLine()
            input.close()
        } catch (ex: IOException) {
            Log.e(TAG, "Unable to read prop $name", ex)
            return null
        } finally {
            if (input != null) {
                try {
                    input.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return line
    }


}