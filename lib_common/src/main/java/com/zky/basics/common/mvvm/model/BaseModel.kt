package com.zky.basics.common.mvvm.model

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.zky.basics.api.dto.RespDTO
import com.zky.basics.api.http.ExceptionHandler
import com.zky.basics.common.R
import com.zky.basics.common.util.ToastUtil
import com.zky.basics.common.util.spread.showToast
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.SocketTimeoutException


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
                        throw Exception(msg)
                    }
                    code != ExceptionHandler.APP_ERROR.SUCC -> {
                        if (!msg.isNullOrEmpty()) {
                            ToastUtil.showToast(msg)
                            msg.showToast()
                        }
                        throw Exception(msg)
                    }
                    else -> {
                        data
                    }
                }
            }
        } catch (e: Exception) {
            if(e is HttpException){
                when(e.code()){
                    ExceptionHandler.SYSTEM_ERROR.INTERNAL_SERVER_ERROR->{
                        R.string.server_error.showToast()
                    }
                }
            } else if(e is SocketTimeoutException){
                e.message?.showToast()
            }

            throw Exception(e)
        }
    }


    override fun onCleared() {
        mCompositeDisposable.clear()
    }
}