package com.zky.multi_media.fragment

import ARouterPath
import AudioUtlis
import android.Manifest
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hjq.permissions.OnPermission
import com.hjq.permissions.XXPermissions
import com.zky.basics.api.config.API
import com.zky.basics.api.file.FileData
import com.zky.basics.api.file.MediaJson
import com.zky.basics.api.room.bean.MediaBean
import com.zky.basics.common.adapter.BaseBindAdapter
import com.zky.basics.common.constant.Constants
import com.zky.basics.common.mvvm.BaseMvvmRefreshFragment
import com.zky.basics.common.util.ObservableListUtil
import com.zky.basics.common.util.PermissionToSetting
import com.zky.basics.common.util.reflec.instanceOf
import com.zky.basics.common.util.showCustomDialog
import com.zky.basics.common.util.spread.showToast
import com.zky.basics.common.util.view.CustomDialog
import com.zky.multi_media.BR
import com.zky.multi_media.R
import com.zky.multi_media.adapter.MediaImageTypeListAdapter
import com.zky.multi_media.adapter.MediaVoiceListAdapter
import com.zky.multi_media.databinding.MediaVoiceFragmentBinding
import com.zky.multi_media.mvvm.factory.MediaViewModelFactory
import com.zky.multi_media.mvvm.viewmodle.MediaVoiceListViewModle


/**
 *create_time : 21-3-5 上午9:25
 *author: lk
 *description： Media
 */
class MediaVoiceFragment :
    BaseMvvmRefreshFragment<MediaBean, MediaVoiceFragmentBinding, MediaVoiceListViewModle>(),
    MediaImageTypeListAdapter.MediaImageTypeListAdapterListener {

    private lateinit var adapter: MediaImageTypeListAdapter
    override fun refreshLayout() = mBinding?.drlMedia
    override fun onBindViewModel() = MediaVoiceListViewModle::class.java
    override fun onBindViewModelFactory() =
        MediaViewModelFactory.getInstance(mActivity.application)

    override fun initViewObservable() {
        mViewModel?.getmVoidSingleLiveEvent()?.observe(this, Observer { a: String? ->
            if (a == "notify") {
                adapter.notifyDataSetChanged()
            }
        })
//        adapter = MediaVoiceListAdapter(mActivity, mViewModel?.mList)
        adapter = MediaImageTypeListAdapter(activity!!, mViewModel?.mList, this, "voice")

        mViewModel?.mList?.addOnListChangedCallback(
            ObservableListUtil.getListChangedCallback(
                adapter
            )
        )
        mBinding?.recview?.layoutManager = LinearLayoutManager(context)
        mBinding?.recview?.adapter = adapter
        val type = object : TypeToken<ArrayList<MediaJson>>() {}.type
        val list = Gson().fromJson<ArrayList<MediaJson>>(Constants.mediaDataTypeAudio, type)
        var listAd = arrayListOf<FileData>()
        list?.forEach {
            for ((index, value) in it.classifyList.withIndex()) {
                val fileData = if (index == 0) {
                    FileData(it.title, value.subtitle, value.type_id, arrayListOf(), true)
                } else {
                    FileData("", value.subtitle, value.type_id, arrayListOf(), false)
                }
                listAd.add(fileData)
            }
        }

        mViewModel?.mList?.addAll(listAd)


    }

    override fun onBindVariableId() = BR.mediaVoiceListViewModle

    override fun onBindLayout() = R.layout.media_voice_fragment

    override fun initView(view: View?) {
    }

    override fun initData() {
        mViewModel?.fileType?.set(fileType)
        mViewModel?.getFileData()
        mViewModel?.getAppToken()
    }

    override fun onResume() {
        super.onResume()
        mViewModel?.fileType?.set(fileType)
    }

    override fun onItemClick(e: Any, position: Int) {
        XXPermissions.with(activity).permission(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
        ).request(object :
            OnPermission {
            override fun hasPermission(granted: MutableList<String>?, all: Boolean) {
                if (all) {
                    if (e.toString() == "add") {
                        val index = mViewModel?.getIndex()
                        if (index == null || index == -1) {
                            return
                        }
                        val data = mViewModel?.mList?.get(index)
                        ARouter.getInstance().build(ARouterPath.MEDIA_SELECT_VOICE)
                            .withString("mediaType2", data?.title)
                            .withString("mediaType3", data?.subTile)
                            .navigation(
                                mActivity,
                                0
                            )
                    } else {
                        var index = mViewModel?.getIndex()
                        if (index == null || index == -1) {
                            return
                        }
                        mViewModel?.mList?.let {
                            val bean = it[index]
                            var filePath = bean?.files?.get(position)?.file_path
                            if (filePath.isNullOrEmpty()) {
                                return
                            }
                            if (!filePath?.contains("/mnt") && !filePath?.contains("/storage/emulated/0") && !filePath.startsWith(
                                    "http"
                                )
                            ) {
                                filePath = API.ImageFolderPath + filePath
                            }

                            AudioUtlis.getAudioUtlis().setAudioClick(mediaClick())
                                .startAudio(filePath,index,position)

                        }

                    }
                }
            }

            override fun noPermission(denied: MutableList<String>?, never: Boolean) {
                PermissionToSetting(activity!!, denied!!, never, "获取存储权限失败")
            }

        })

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            val index = mViewModel?.getIndex()
            if (index == null || index == -1) {
                return
            }
            mViewModel?.mList?.let { dataS ->
                val parcelableArrayListExtra = data.getParcelableArrayListExtra<MediaBean>("data")
                if (parcelableArrayListExtra != null) {
                    parcelableArrayListExtra.forEach {
                        it.startIng = 1
                        it.mediaType2 = dataS[index].title
                        it.mediaType3 = dataS[index].subTile
                    }
                    mViewModel?.mList?.get(index)?.files?.addAll(parcelableArrayListExtra)
                    adapter.notifyDataSetChanged()
                }

            }
        }
    }


    inner class mediaClick : AudioUtlis.AudioClick {

        override fun completionListener(dataIndex: Int, fileIndex: Int, path: String) {
            mViewModel?.setTime(dataIndex,fileIndex,path, 0)
        }

        override fun startListener(dataIndex: Int, fileIndex: Int, path: String, length: Int) {
            mViewModel?.setTime(dataIndex,fileIndex,path, length)
        }

        override fun pauseListener(dataIndex: Int, fileIndex: Int, path: String, length: Int) {
            mViewModel?.setTime(dataIndex,fileIndex,path, length)
            mViewModel?.vioceStop()
        }

    }

    override fun onStop() {
        super.onStop()
        AudioUtlis.getAudioUtlis().stop("")
    }


    companion object {
        private var fileType: String = "audio"

        @JvmStatic
        fun mediaSelctVoiceInstance(_type: String): Fragment {
            return MediaVoiceFragment()
        }
    }

    override fun onItemLongClick(e: Any, postion: Int): Boolean {

        if (postion + 1 != mViewModel?.mList?.size) {
            showCustomDialog(
                mActivity,
                "删除",
                "是否删除",
                "",
                "取消",
                "确定"
            ).setOnItemClickListener(object :
                CustomDialog.OnItemClickListener {
                override fun onSure() {

                    var index = mViewModel?.getIndex()
                    if (index == -1 || index == null) {
                        return
                    }
                    val fileData = mViewModel?.mList?.get(index)
                    fileData?.let {
                        val bean = it.files?.get(postion)
                        if (bean != null) {
                            if (bean.upload) {
                                mViewModel?.deleFile(bean.code, index, postion, adapter)
                            } else {
                                mViewModel?.mList?.get(index)?.files?.removeAt(postion)
                                adapter.notifyDataSetChanged()
                            }
                        }

                    }
                }

                override fun onDismiss() {
                }

            })

        }
        return true
    }
}