package com.zky.multi_media.fragment

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.reflect.TypeToken
import com.hjq.permissions.OnPermission
import com.hjq.permissions.XXPermissions
import com.zky.basics.api.file.FileData
import com.zky.basics.api.file.MediaJson
import com.zky.basics.api.room.bean.MediaBean
import com.zky.basics.api.splash.entity.Userinfo
import com.zky.basics.common.constant.Constants
import com.zky.basics.common.mvvm.BaseMvvmRefreshFragment
import com.zky.basics.common.util.*
import com.zky.basics.common.util.FileUtil.isVideoFile
import com.zky.basics.common.util.reflec.instanceOf
import com.zky.basics.common.util.spread.decodeParcelable
import com.zky.basics.common.util.view.CustomDialog
import com.zky.multi_media.BR
import com.zky.multi_media.R
import com.zky.multi_media.adapter.MediaImageTypeListAdapter
import com.zky.multi_media.databinding.MediaFragmentBinding
import com.zky.multi_media.mvvm.factory.MediaViewModelFactory
import com.zky.multi_media.mvvm.viewmodle.MediaImageListViewModle
import me.bzcoder.mediapicker.config.MediaPickerEnum
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList


/**
 *create_time : 21-3-5 上午9:25
 *author: lk
 *description： Media
 */
class MediaVideoFragment :
    BaseMvvmRefreshFragment<MediaBean, MediaFragmentBinding, MediaImageListViewModle>(),
    MediaImageTypeListAdapter.MediaImageTypeListAdapterListener {
    private val userinfo = decodeParcelable<Userinfo>("user")
    private lateinit var mediaListAdapter: MediaImageTypeListAdapter
    override fun refreshLayout() = mBinding?.drlMedia
    override fun onBindViewModel() = MediaImageListViewModle::class.java

    override fun onBindViewModelFactory() =
        MediaViewModelFactory.getInstance(activity!!.application)

    override fun initViewObservable() {
        mViewModel?.getmVoidSingleLiveEvent()?.observe(this, Observer { a: String? ->
            if (a == "notify") {
                mediaListAdapter.notifyDataSetChanged()
            }
        })

        mediaListAdapter = MediaImageTypeListAdapter(activity!!, mViewModel?.mList, this, "video")
        mViewModel?.mList?.addOnListChangedCallback(
            ObservableListUtil.getListChangedCallback(
                mediaListAdapter
            )
        )
        mBinding?.recview?.layoutManager = LinearLayoutManager(context)
        mBinding?.recview?.adapter = mediaListAdapter
        mViewModel?.mList?.addAll(MediaJsonToData.getMediaList("video"))


    }

    override fun onBindVariableId() = BR.mediaListViewModel

    override fun onBindLayout() = R.layout.media_fragment

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
        if (e.toString() == "add") {
            XXPermissions.with(activity).permission(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            ).request(object :
                OnPermission {
                override fun hasPermission(granted: MutableList<String>?, all: Boolean) {
                    if (all) {
                        selectMedia()
                    }
                }

                override fun noPermission(denied: MutableList<String>?, never: Boolean) {
                    PermissionToSetting(activity!!, denied!!, never, "获取存储权限失败")
                }

            })

        } else {
            val index = mViewModel?.getIndex()
            if (index == null || index == -1) {
                return
            }
            val params = Bundle()
            params.putParcelable("file", mViewModel?.mList?.get(index)?.files?.get(position))
            ARouter.getInstance().build(ARouterPath.MEDIA_SHOW_VIDEO).with(params).navigation()
        }
    }

    private fun selectMedia() {
        var imageCount = 0
        var videoCount = 9

        SmartMediaPickerComsur.builder(activity) //最大图片选择数目 如果不需要图片 将数目设置为0
            .withMaxImageSelectable(imageCount) //最大视频选择数目 如果不需要视频 将数目设置为0
            .withMaxVideoSelectable(videoCount) //图片选择器是否显示数字
            .withCountable(true)        //最大视频长度
            .withMaxVideoLength(20 * 1000) //                            //最大视频文件大小 单位MB
            .withMaxVideoSize(1000) //最大图片高度 默认1920
            .withMaxHeight(11920) //最大图片宽度 默认1920
            .withMaxWidth(11920) //最大图片大小 单位MB
            .withMaxImageSize(20) //设置图片加载引擎
            .withImageEngine(Glide4Engine()) //前置摄像头拍摄是否镜像翻转图像
            .withIsMirror(false) //弹出类别，默认弹出底部选择栏，也可以选择单独跳转
            .withMediaPickerType(MediaPickerEnum.BOTH)
            .build()
            .show()

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val resultData =
            SmartMediaPickerComsur.getResultData(activity, requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
        if (resultData != null && resultData.size > 0) {


            val videoFile = isVideoFile(resultData[0])
            if (!videoFile) {
                return
            }

            mViewModel?.mList?.let {
                var index = mViewModel?.getIndex()
                if (index == -1 || index == null) {
                    return
                }
                val dataBean = it[index]

                var tmpList = arrayListOf<MediaBean>()
                for (item in resultData) {
                    val bean = instanceOf<MediaBean>()
                    bean.code = UUID.randomUUID().toString().replace("-", "")

                    bean.create_data = DateUtil.getCurrentTime(DateUtil.FormatType.yyyyMMddHHmm)
                    bean.file_path = item
                    bean.file_type = fileType
                    bean.videoImagePath = item
                    bean.uploader = userinfo?.username
                    bean.file_name = FileUtil.getNameByPath(item)
                    bean.upload = false
                    bean.mediaType2 = dataBean.title
                    bean.mediaType3 = dataBean.subTile
                    tmpList.add(bean)
                }
                mViewModel?.mList?.get(index)?.files?.addAll(tmpList)
                mediaListAdapter.notifyDataSetChanged()
            }
        }


    }

    companion object {
        private var fileType: String = "video"

        @JvmStatic
        fun mediaVoiceInstance(): Fragment {
            return MediaVideoFragment()
        }
    }

    override fun onItemLongClick(e: Any, position: Int): Boolean {
        if (position + 1 != mViewModel?.mList?.size) {
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
                        val bean = it.files?.get(position)
                        if (bean != null) {
                            if (bean.upload) {
                                mViewModel?.deleFile(bean.code, index, position, mediaListAdapter)
                            } else {
                                mViewModel?.mList?.get(index)?.files?.removeAt(position)
                                mediaListAdapter.notifyDataSetChanged()
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

    override fun enableLazyData()=true
}