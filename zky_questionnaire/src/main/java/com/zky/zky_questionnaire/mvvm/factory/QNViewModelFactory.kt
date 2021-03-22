package com.zky.zky_questionnaire.mvvm.factory

import android.annotation.SuppressLint
import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zky.zky_questionnaire.mvvm.model.qnModel
import com.zky.zky_questionnaire.mvvm.viewmodle.QNViewModle


class QNViewModelFactory private constructor(private val mApplication: Application) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QNViewModle::class.java)) {
            val qnModel = qnModel(mApplication)
            return QNViewModle(mApplication, qnModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: QNViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): QNViewModelFactory? {
            if (INSTANCE == null) {
                synchronized(QNViewModelFactory::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = QNViewModelFactory(application)
                    }
                }
            }
            return INSTANCE
        }

        @VisibleForTesting
        fun destroyInstance() {
            INSTANCE = null
        }
    }

}