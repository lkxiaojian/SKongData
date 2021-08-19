package com.zky.support_salons.mvvm.factory

import android.annotation.SuppressLint
import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zky.support_salons.mvvm.model.SupportModel
import com.zky.support_salons.mvvm.viewmodle.*


class SupportSalonsViewModelFactory private constructor(private val mApplication: Application) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SupportModelViewModle::class.java) -> {
                val splashModel = SupportModel(mApplication)
                SupportModelViewModle(mApplication, splashModel) as T
            }
            modelClass.isAssignableFrom(OpenPublishViewModle::class.java) -> {
                val splashModel = SupportModel(mApplication)
                OpenPublishViewModle(mApplication, splashModel) as T
            }
            modelClass.isAssignableFrom(RemarkViewModle::class.java) -> {
                val splashModel = SupportModel(mApplication)
                RemarkViewModle(mApplication, splashModel) as T
            }
            modelClass.isAssignableFrom(NewsViewModle::class.java) -> {
                val splashModel = SupportModel(mApplication)
                NewsViewModle(mApplication, splashModel) as T
            }
            modelClass.isAssignableFrom(ScalonsPubViewModle::class.java) -> {
                val splashModel = SupportModel(mApplication)
                ScalonsPubViewModle(mApplication, splashModel) as T
            }


            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }


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