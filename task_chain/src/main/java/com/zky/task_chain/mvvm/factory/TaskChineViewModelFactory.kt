package com.zky.task_chain.mvvm.factory

import android.annotation.SuppressLint
import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.zky.task_chain.mvvm.model.ChainModel
import com.zky.task_chain.mvvm.viewmodle.AddDealMessageViewModle
import com.zky.task_chain.mvvm.viewmodle.ChainViewModle
import com.zky.task_chain.mvvm.viewmodle.DealMessageViewModle

class TaskChineViewModelFactory private constructor(private val mApplication: Application) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChainViewModle::class.java)) {
            val splashModel = ChainModel(mApplication)
            return ChainViewModle(mApplication, splashModel) as T
        } else if (modelClass.isAssignableFrom(DealMessageViewModle::class.java)) {
            val splashModel = ChainModel(mApplication)
            return DealMessageViewModle(mApplication, splashModel) as T
        } else if (modelClass.isAssignableFrom(AddDealMessageViewModle::class.java)) {
            val splashModel = ChainModel(mApplication)
            return AddDealMessageViewModle(mApplication, splashModel) as T
        }



        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: TaskChineViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): TaskChineViewModelFactory? {
            if (INSTANCE == null) {
                synchronized(TaskChineViewModelFactory::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = TaskChineViewModelFactory(application)
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