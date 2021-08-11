package com.zky.basics.common.mvvm.model

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.zky.basics.api.common.entity.GeocoderBean
import com.zky.basics.api.dto.RespDTO
import com.zky.basics.api.http.CustomException
import com.zky.basics.api.http.ExceptionHandler
import com.zky.basics.common.R
import com.zky.basics.common.util.spread.showToast
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.RuntimeException


abstract class BaseModel(protected var mApplication: Application?) : IBaseModel {
    private var mCompositeDisposable = CompositeDisposable()
    fun addSubscribe(disposable: Disposable?) {
        disposable?.let {
            mCompositeDisposable.add(it)
        }
    }

    suspend fun <T : Any> request(call: suspend () -> RespDTO<T>): T? {
        try {
            return withContext(Dispatchers.IO) { call.invoke() }.run {
                when {
                    ExceptionHandler.SYSTEM_ERROR.LONG_TIME_NO_ACTION == code -> {
                        R.string.long_time.showToast()
                        ARouter.getInstance().build(ARouterPath.LOGIN).navigation()
                        null
                    }
                    ExceptionHandler.SYSTEM_ERROR.INTERNAL_SERVER_ERROR == code -> {
                        R.string.server_error.showToast()
                        throw CustomException(msg)
                    }
                    code != ExceptionHandler.APP_ERROR.SUCC -> {
                        msg.showToast()
                        throw CustomException(msg)
                    }
                    else -> {
                        data
                    }
                }
            }
        } catch (e: Exception) {
            val handleException = ExceptionHandler.handleException(e)
            if (e is RuntimeException) {
                return null
            }
            handleException.message?.showToast()
            throw  CustomException(handleException.message)
        }
    }

    suspend fun <T : Any> request1(call: suspend () -> T): T? {
        return try {
            withContext(Dispatchers.IO) { call.invoke() }
        } catch (e: Exception) {
            null
        }
    }

    override fun onCleared() {
        mCompositeDisposable.clear()
    }
}