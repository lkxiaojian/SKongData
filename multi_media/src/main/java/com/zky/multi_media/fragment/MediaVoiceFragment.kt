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
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.hjq.permissions.OnPermission
import com.hjq.permissions.XXPermissions
import com.zky.basics.api.config.API
import com.zky.basics.api.room.bean.MediaBean
import com.zky.basics.common.adapter.BaseBindAdapter
import com.zky.basics.common.mvvm.BaseMvvmRefreshFragment
import com.zky.basics.common.util.ObservableListUtil
import com.zky.basics.common.util.PermissionToSetting
import com.zky.basics.common.util.reflec.instanceOf
import com.zky.basics.common.util.showCustomDialog
import com.zky.basics.common.util.spread.showToast
import com.zky.basics.common.util.view.CustomDialog
import com.zky.multi_media.BR
import com.zky.multi_media.R
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
    BaseBindAdapter.OnItemClickListener<Any>, BaseBindAdapter.OnItemLongClickListener<Any> {

    private lateinit var adapter: MediaVoiceListAdapter
    override fun refreshLayout() = mBinding?.drlMedia
    override fun onBindViewModel() = MediaVoiceListViewModle::class.java
    override fun onBindViewModelFactory() =
        MediaViewModelFactory.getInstance(mActivity.application)

    override fun initViewObservable() {
        adapter = MediaVoiceListAdapter(mActivity, mViewModel?.mList)
        mViewModel?.mList?.addOnListChangedCallback(
            ObservableListUtil.getListChangedCallback(
                adapter
            )
        )
        mBinding?.recview?.layoutManager = GridLayoutManager(activity, 3)
        adapter.setItemClickListener(this)
        adapter.setOnItemLongClickListener(this)
        mBinding?.recview?.adapter = adapter
        mViewModel?.mList?.add(instanceOf<MediaBean>())


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
        val bean = e as MediaBean
        XXPermissions.with(activity).permission(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
        ).request(object :
            OnPermission {
            override fun hasPermission(granted: MutableList<String>?, all: Boolean) {
                if (all) {
                    if (bean.create_data.isNullOrEmpty()) {

                        ARouter.getInstance().build(ARouterPath.MEDIA_SELECT_VOICE).navigation(
                            mActivity,
                            0
                        )
                    } else {
                        var filePath = bean.file_path

                        if (!filePath.contains("/mnt")&&!filePath.contains("/storage/emulated/0") && !filePath.startsWith("http")
                        ) {
                            filePath = API.ImageFolderPath + bean.file_path
                        }

                        AudioUtlis.getAudioUtlis().setAudioClick(mediaClick())
                            .startAudio(filePath)
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
        val tmpList = arrayListOf<MediaBean>()
        if (data != null) {
            mViewModel?.mList?.let { tmpList.addAll(it) }
            val parcelableArrayListExtra = data.getParcelableArrayListExtra<MediaBean>("data")
            if (parcelableArrayListExtra != null) {
                parcelableArrayListExtra.forEach {
                    it.startIng = 1
                }
                tmpList.addAll(0, parcelableArrayListExtra)
                val distinct = tmpList.distinctBy { it.file_path }
                mViewModel?.mList?.clear()
                mViewModel?.mList?.addAll(distinct)
            }

        }


    }


    inner class mediaClick : AudioUtlis.AudioClick {
        override fun completionListener(path: String) {
            mViewModel?.setTime(path, 0)
        }

        override fun startListener(path: String, length: Int) {
            mViewModel?.setTime(path, length)
        }

        override fun pauseListener(path: String, length: Int) {
            mViewModel?.setTime(path, length)
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
            val mediaBean = e as MediaBean
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
//                    mViewModel?.mList?.removeAt(postion)
                    if (mediaBean.upload) {
                        mViewModel?.deleFile(mediaBean.code, postion)
                    } else {
                        mViewModel?.mList?.removeAt(postion)
                    }
                }

                override fun onDismiss() {
                }

            })

        }


        return true

    }
}