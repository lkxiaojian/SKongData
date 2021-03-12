package com.zky.zky_map.mvvm.factory

import android.annotation.SuppressLint
import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zky.zky_map.mvvm.model.MapModel
import com.zky.zky_map.mvvm.viewmodle.MapViewModle


class MapViewModelFactory private constructor(private val mApplication: Application) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun < T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapViewModle::class.java)) {
            return MapViewModle(mApplication, MapModel(mApplication)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: MapViewModelFactory? = null
        fun getInstance(application: Application): MapViewModelFactory? {
            if (INSTANCE == null) {
                synchronized(MapViewModelFactory::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = MapViewModelFactory(application)
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