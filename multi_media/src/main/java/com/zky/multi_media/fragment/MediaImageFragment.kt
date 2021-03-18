package com.zky.multi_media.fragment

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.hjq.permissions.OnPermission
import com.hjq.permissions.XXPermissions
import com.zky.basics.api.room.bean.MediaBean
import com.zky.basics.api.splash.entity.Userinfo
import com.zky.basics.common.adapter.BaseBindAdapter
import com.zky.basics.common.constant.Constants.itemCode
import com.zky.basics.common.mvvm.BaseMvvmRefreshFragment
import com.zky.basics.common.util.*
import com.zky.basics.common.util.reflec.instanceOf
import com.zky.basics.common.util.spread.decodeParcelable
import com.zky.basics.common.util.view.CustomDialog
import com.zky.multi_media.BR
import com.zky.multi_media.R
import com.zky.multi_media.adapter.MediaImageListAdapter
import com.zky.multi_media.databinding.MediaFragmentBinding
import com.zky.multi_media.mvvm.factory.MediaViewModelFactory
import com.zky.multi_media.mvvm.viewmodle.MediaImageListViewModle
import me.bzcoder.mediapicker.config.MediaPickerEnum
import java.util.*

/**
 *create_time : 21-3-5 上午9:25
 *author: lk
 *description： Media
 */
class MediaImageFragment :
    BaseMvvmRefreshFragment<MediaBean, MediaFragmentBinding, MediaImageListViewModle>(),
    BaseBindAdapter.OnItemClickListener<Any>, BaseBindAdapter.OnItemLongClickListener<Any> {

    private lateinit var mediaListAdapter: MediaImageListAdapter
    private val userinfo = decodeParcelable<Userinfo>("user")
    override fun refreshLayout() = mBinding?.drlMedia
    override fun onBindViewModel() = MediaImageListViewModle::class.java

    override fun onBindViewModelFactory() =
        MediaViewModelFactory.getInstance(activity!!.application)

    override fun initViewObservable() {
        mediaListAdapter = MediaImageListAdapter(activity!!, mViewModel?.mList)
        mViewModel?.mList?.addOnListChangedCallback(
            ObservableListUtil.getListChangedCallback(
                mediaListAdapter
            )
        )
        mBinding?.recview?.layoutManager = GridLayoutManager(activity, 3)
        mediaListAdapter.setItemClickListener(this)
        mediaListAdapter.setOnItemLongClickListener(this)
        mBinding?.recview?.adapter = mediaListAdapter
        mViewModel?.mList?.add(instanceOf<MediaBean>())

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
        val bean = e as MediaBean
        if (e.create_data.isNullOrEmpty()) {
            XXPermissions.with(activity).permission(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            ).request(object :
                OnPermission {
                override fun hasPermission(granted: MutableList<String>?, all: Boolean) {
                    if (all) {
                        selectMedia(bean, position)
                    }
                }

                override fun noPermission(denied: MutableList<String>?, never: Boolean) {
                    PermissionToSetting(activity!!, denied!!, never, "获取存储权限失败")
                }

            })

        } else {
            val params = Bundle()
            val projectPhoto = arrayListOf<MediaBean>()
            mViewModel?.mList?.let { projectPhoto.addAll(it) }
            mViewModel?.mList?.size?.minus(1)?.let { projectPhoto.removeAt(it) }
            params.putSerializable("images", projectPhoto)
//            params.putInt("position", position)
            ARouter.getInstance().build(ARouterPath.MEDIA_SHOW_IMAGE)
                .withInt("position", position)
                .withSerializable("images", projectPhoto).navigation()
        }
    }

    private fun selectMedia(e: MediaBean, position: Int) {
        if (position == mViewModel?.mList?.size?.minus(1)) {
            var imageCount = 9
            var videoCount = 0

            SmartMediaPickerComsur.builder(activity) //最大图片选择数目 如果不需要图片 将数目设置为0
                .withMaxImageSelectable(imageCount) //最大视频选择数目 如果不需要视频 将数目设置为0
                .withMaxVideoSelectable(videoCount) //图片选择器是否显示数字
                .withCountable(true)        //最大视频长度
                .withMaxVideoLength(600 * 1000) //                            //最大视频文件大小 单位MB
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
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val resultData =
            SmartMediaPickerComsur.getResultData(activity, requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
        if (resultData != null && resultData.size > 0) {

            val imaFile = FileUtil.isImageFile(resultData[0])
            if (!imaFile) {
                return
            }

            var tmpList = arrayListOf<MediaBean>()

            for (item in resultData) {
                var bean = instanceOf<MediaBean>()
                bean.code = UUID.randomUUID().toString().replace("-", "")
                bean.create_data = DateUtil.getCurrentTime(DateUtil.FormatType.yyyyMMddHHmm)
                bean.file_path = item
                bean.file_type = fileType
                bean.videoImagePath = item
                bean.uploader = userinfo?.username
                bean.file_name = FileUtil.getNameByPath(item)
                bean.upload = false
                tmpList.add(bean)
            }
            val minus = mViewModel?.mList?.size?.minus(1)
            mViewModel?.mList?.addAll(minus!!, tmpList)
            mediaListAdapter.notifyDataSetChanged()
        }


    }

    companion object {
        private var fileType = "photo"

        @JvmStatic
        fun mediaImageInstance(code: String): Fragment {
            itemCode = code
            return MediaImageFragment()
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