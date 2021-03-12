package com.zky.zky_mine.mvvm.factory

import android.annotation.SuppressLint
import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.zky.zky_mine.mvvm.model.MineModel
import com.zky.zky_mine.mvvm.viewmodle.MineViewModle

class MineViewModelFactory private constructor(private val mApplication: Application) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MineViewModle::class.java)) {
            val splashModel = MineModel(mApplication)
            return MineViewModle(mApplication, splashModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: MineViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): MineViewModelFactory? {
            if (INSTANCE == null) {
                synchronized(MineViewModelFactory::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = MineViewModelFactory(application)
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