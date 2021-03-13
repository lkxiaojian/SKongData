package com.zky.multi_media.mvvm.viewmodle

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import com.hjq.permissions.OnPermission
import com.hjq.permissions.XXPermissions
import com.zky.basics.api.room.bean.MediaBean
import com.zky.basics.common.event.SingleLiveEvent
import com.zky.basics.common.mvvm.viewmodel.BaseRefreshViewModel
import com.zky.basics.common.util.DateUtil
import com.zky.basics.common.util.FileUtil
import com.zky.basics.common.util.PermissionToSetting
import com.zky.basics.common.util.onShowDialogLoading
import com.zky.basics.common.util.reflec.instanceOf
import com.zky.multi_media.R
import com.zky.multi_media.mvvm.model.MediaModel
import java.io.File


/**
 * Created by lk
 * Date 2020/2/27
 * Time 17:07
 * Detail:
 */
class VoiceListViewModle(application: Application, mediaModel: MediaModel) :
    BaseRefreshViewModel<MediaBean, MediaModel>(application, mediaModel) {
    lateinit var activity: Activity
    var searchMessage = ObservableField<String>()
    private var mVoidSingleLiveEvent: SingleLiveEvent<String>? = null

    val list = arrayListOf<MediaBean>()
    override fun refreshData() {
    }


    override fun loadMore() {
    }


    fun startClick(view: View) {
        val id = view.id
        when {
            R.id.abt_search == id -> {
                search()
            }
            R.id.abt_sure == id -> {

                getmVoidSingleLiveEvent().value = "sure"
            }
        }


    }

    override fun enableLoadMore() = false
    override fun enableRefresh() = false

    @SuppressLint("Recycle")
    fun search() {
        XXPermissions.with(activity).permission(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).request(object :
            OnPermission {
            override fun hasPermission(granted: MutableList<String>?, all: Boolean) {
                if (all) {
                    getVoice()
                }
            }

            override fun noPermission(denied: MutableList<String>?, never: Boolean) {
                PermissionToSetting(activity, denied!!, never, "获取存储权限失败")

            }

        })
    }


    private fun getVoice() {
        launchUI({

            val tmpList = arrayListOf<MediaBean>()
            if (list.isEmpty()) {
                val path = "${File.separator}mnt${File.separator}sdcard${File.separator}"
                val files = FileUtil.getFiles(path, "voice")
                files.forEach {
                    val song = instanceOf<MediaBean>()
                    song.file_name = it.name
                    song.file_path = it.path
                    song.startIng = 1
                    song.create_data = DateUtil.getCurrentTime(DateUtil.FormatType.yyyyMMddHHmm)
                    list.add(song)
                }
            }
            val seMessage = searchMessage.get()
            if (seMessage.isNullOrEmpty()) {
                tmpList.addAll(list)
            } else {
                list.forEach {
                    if (it.file_name.contains(seMessage, true)) {
                        tmpList.add(it)
                    }
                }
            }
            mList.clear()
            if (tmpList.isNotEmpty()) {
                mList.addAll(tmpList)
            }
            getmVoidSingleLiveEvent().value = "dismiss"
        })
    }

    fun getmVoidSingleLiveEvent(): SingleLiveEvent<String> {
        return createLiveData(mVoidSingleLiveEvent).also {
            mVoidSingleLiveEvent = it
        }
    }
}