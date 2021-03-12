package com.zky.multi_media.mvvm.factory

import android.annotation.SuppressLint
import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zky.multi_media.mvvm.model.MediaModel
import com.zky.multi_media.mvvm.viewmodle.MediaImageListViewModle
import com.zky.multi_media.mvvm.viewmodle.MediaVoiceListViewModle
import com.zky.multi_media.mvvm.viewmodle.VoiceListViewModle

class MediaViewModelFactory private constructor(private val mApplication: Application) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(MediaImageListViewModle::class.java) -> {
                return MediaImageListViewModle(mApplication, MediaModel(mApplication)) as T
            }
            modelClass.isAssignableFrom(VoiceListViewModle::class.java) -> {
                return VoiceListViewModle(mApplication, MediaModel(mApplication)) as T
            }
            modelClass.isAssignableFrom(VoiceListViewModle::class.java) -> {
                return VoiceListViewModle(mApplication, MediaModel(mApplication)) as T
            }
            modelClass.isAssignableFrom(MediaVoiceListViewModle::class.java) -> {
                return MediaVoiceListViewModle(mApplication, MediaModel(mApplication)) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: MediaViewModelFactory? = null
        fun getInstance(application: Application): MediaViewModelFactory? {
            if (INSTANCE == null) {
                synchronized(MediaViewModelFactory::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = MediaViewModelFactory(application)
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