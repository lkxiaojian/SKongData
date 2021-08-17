package com.zky.support_salons.mvvm.factory

import android.annotation.SuppressLint
import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zky.support_salons.mvvm.model.SupportModel
import com.zky.support_salons.mvvm.viewmodle.SupportModelViewModle


class SupportSalonsViewModelFactory private constructor(private val mApplication: Application) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SupportModelViewModle::class.java)) {
            val splashModel = SupportModel(mApplication)
            return SupportModelViewModle(mApplication, splashModel) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: SupportSalonsViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): SupportSalonsViewModelFactory? {
            if (INSTANCE == null) {
                synchronized(SupportSalonsViewModelFactory::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = SupportSalonsViewModelFactory(application)
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