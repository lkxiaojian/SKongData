package com.zky.sk.data

import android.util.Log
import com.zxy.recovery.callback.RecoveryCallback

/**
 * Created by lk
 * Date 2019-12-26
 * Time 10:52
 * Detail:
 */
class MyCrashCallback : RecoveryCallback {
    private val TAG = "--skData--"
    override fun stackTrace(stackTrace: String) {
        Log.e(TAG, "stackTrace--->$stackTrace")
    }

    override fun cause(cause: String) {
        Log.e(TAG, "cause--->$cause")
    }

    override fun exception(
        throwExceptionType: String,
        throwClassName: String,
        throwMethodName: String,
        throwLineNumber: Int
    ) {

    }

    override fun throwable(throwable: Throwable) {
        Log.e(TAG, "throwable--->$throwable")
    }
}