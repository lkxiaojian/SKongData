package com.zky.basics.main.mvvm.factory

import android.annotation.SuppressLint
import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zky.basics.main.mvvm.model.MainModel
import com.zky.basics.main.mvvm.model.MapModel
import com.zky.basics.main.mvvm.model.SplashModel
import com.zky.basics.main.mvvm.viewmodel.*

class MainViewModelFactory private constructor(private val mApplication: Application) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> {
                val splashModel = SplashModel(mApplication)
                SplashViewModel(mApplication, splashModel) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                val mainModel = MainModel(mApplication)
                MainViewModel(mApplication, mainModel) as T
            }
            modelClass.isAssignableFrom(TaskViewModel::class.java) -> {
                val mainModel = MainModel(mApplication)
                TaskViewModel(mApplication, mainModel) as T
            }
            modelClass.isAssignableFrom(MapViewModle::class.java) -> {
                val mainModel = MapModel(mApplication)
                MapViewModle(mApplication, mainModel) as T
            }

            modelClass.isAssignableFrom(StatisticsModel::class.java) -> {
                val mainModel = MainModel(mApplication)
                StatisticsModel(mApplication, mainModel) as T
            }
            modelClass.isAssignableFrom(QStateViewModel::class.java) -> {
                val mainModel = MainModel(mApplication)
                QStateViewModel(mApplication, mainModel) as T
            }
            modelClass.isAssignableFrom(DepartmentDataViewModel::class.java) -> {
                val mainModel = MainModel(mApplication)
                DepartmentDataViewModel(mApplication, mainModel) as T
            }
            modelClass.isAssignableFrom(DepartmentDataItemViewModel::class.java) -> {
                val mainModel = MainModel(mApplication)
                DepartmentDataItemViewModel(mApplication, mainModel) as T
            }

            modelClass.isAssignableFrom(LocationMapViewModle::class.java) -> {
                val mainModel = MapModel(mApplication)
                LocationMapViewModle(mApplication, mainModel) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: MainViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): MainViewModelFactory? {
            if (INSTANCE == null) {
                synchronized(MainViewModelFactory::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = MainViewModelFactory(application)
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