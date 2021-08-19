package com.zky.multi_media.mvvm.viewmodle

import android.app.Application
import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider
import com.zky.basics.api.config.API
import com.zky.basics.api.file.FileData
import com.zky.basics.api.room.bean.MediaBean
import com.zky.basics.api.splash.entity.Userinfo
import com.zky.basics.common.constant.Constants
import com.zky.basics.common.event.SingleLiveEvent
import com.zky.basics.common.mvvm.viewmodel.BaseRefreshViewModel
import com.zky.basics.common.util.*
import com.zky.basics.common.util.reflec.instanceOf
import com.zky.basics.common.util.spread.decodeParcelable
import com.zky.basics.common.util.uploadFile.OSSAuthCredentialsProvider
import com.zky.basics.common.util.uploadFile.OssUploadingFileUtil
import com.zky.basics.common.util.uploadFile.UploadingFile
import com.zky.basics.common.util.view.CustomDialog
import com.zky.multi_media.R
import com.zky.multi_media.adapter.MediaImageTypeListAdapter
import com.zky.multi_media.mvvm.model.MediaModel
import java.util.*

/**
 * Created by lk
 * Date 2020/2/27
 * Time 17:07
 * Detail:
 */
class MediaVoiceListViewModle(application: Application, mediaModel: MediaModel) :
    BaseRefreshViewModel<FileData, MediaModel>(application, mediaModel) {
    var fileType = ObservableField<String>()
    private val applicatio = application
    private var mVoidSingleLiveEvent: SingleLiveEvent<String>? = null

    override fun refreshData() {
    }


    override fun loadMore() {
    }


    fun getFileData() {
        launchUI({
            val fileList = mModel.getFileList(Constants.itemCode, fileType.get())
//            val tmpFile = arrayListOf<MediaBean>()
            for ((index, value) in mList.withIndex()) {
                fileList?.forEach {
                    if (value.title == it.mediaType2 && value.subTile == it.mediaType3) {
                        val bean = instanceOf<MediaBean>()
                        bean.code = it.code
                        bean.file_name = it.fileName
                        bean.file_path = it.filePath
                        bean.upload = true
                        bean.startIng = 1
                        bean.filePathIsNUll = bean.file_path.isEmpty()
                        bean.create_data = it.createDate
                        bean.mediaType2 = value.titleTmp
                        bean.mediaType3 = it.mediaType3
                        mList[index].files?.add(bean)
                    }
                }
            }
            getmVoidSingleLiveEvent().value = "notify"
//            mList.addAll(0, tmpFile)

        })
    }

    fun startClick(view: View) {
        when (view.id) {
            R.id.abt_up -> {
                if (mList.isNullOrEmpty()) {
                    return
                }
                val userinfo = decodeParcelable<Userinfo>("user")
                var uploadingList = arrayListOf<UploadingFile>()

                for (value in mList) {
                    value.files?.forEach { it ->

                        if (!it.upload && it.file_path != null) {
                            val uploadingFile = UploadingFile()
                            uploadingFile.servicePath = API.URL_UPLOAD_NEW
                            uploadingFile.filePath = it.file_path
                            uploadingFile.fileName = it.file_name
                            uploadingFile.tmpCode = it.code
                            val map = hashMapOf<String, String>()
                            val filePath =
                                "skData/${Constants.itemCode}/${it.file_type}/${it.code}/${it.file_name}"
                            map["filePath"] = filePath
                            map["mediaType"] = it.file_type
                            map["fileName"] = it.file_name
                            map["createTime"] = it.create_data
                            map["itemCode"] = Constants.itemCode
                            map["code"] = it.code
                            map["mediaType2"] =value.titleTmp
                            map["mediaType3"] = it.mediaType3
                            userinfo?.username?.let {
                                map["userName"] = it
                            }
                            userinfo?.phone?.let {
                                map["userPhone"] = it
                            }
                            uploadingFile.params = map
                            uploadingList.add(uploadingFile)
                        }
                    }
                }
                if (uploadingList.size == 0) {
                    ToastUtil.showToast("上传成功")
                    return
                }

                uploadFile(view, uploadingList)
            }
        }

    }

    private fun uploadFile(view: View, uploadingList: ArrayList<UploadingFile>) {
        val onShowDialogLoading = onShowDialogLoading(view.context)
        dialogSetText(onShowDialogLoading, "上传中。。。")
        OssUploadingFileUtil().upLoadingImageFile(applicatio, uploadingList,
            object : UpLoadingFileListener {
                override fun upLoadSuccessPostiion(file: UploadingFile) {
                    val tmpCode = file.params?.get("code")
                    for ((index, value) in mList.withIndex()) {
                        for ((i, data) in value.files!!.withIndex())
                            if (data.code == tmpCode) {
                                mList[index].files?.get(i)?.upload = true
                            }
                    }

                    getmVoidSingleLiveEvent().value = "notify"
                }

                override fun upLoadingProgress(
                    progress: Int,
                    index: Int,
                    fileTotalProgress: Int
                ) {
                }

                override fun upLoadSuccess(failNum: Int) {
                    onShowDialogLoading.dismiss()
                    if (failNum == 0) {
                        ToastUtil.showToast("上传成功")
                    } else {
                        val showCustomDialog =
                            showCustomDialog(
                                view.context,
                                "提示",
                                "",
                                "有 $failNum 个上传失败,是否继续上传",
                                "取消",
                                "确定"
                            )

                        showCustomDialog.setOnItemClickListener(object :
                            CustomDialog.SimpleClickListener() {
                            override fun onSure() {
                                uploadFile(view, uploadingList)
                            }

                            override fun onDismiss() {
                                super.onDismiss()
                            }
                        })
                    }
                }

            })


    }

    fun getAppToken() {
        if (!NetUtil.checkNet()) {
            return
        }

        val userinfo = decodeParcelable<Userinfo>("user")
        val phone = userinfo?.phone
        val paw = userinfo?.password

        launchUI({
            val appToken = mModel.getAppToken(phone, paw)
            appToken?.let {
                Constants.ossStsTokenCredentialProvider =
                    OSSStsTokenCredentialProvider(
                        it.AccessKeyId,
                        it.AccessKeySecret,
                        it.SecurityToken
                    )
                val ossAuthCredentialsProvider = OSSAuthCredentialsProvider(it)
                ossAuthCredentialsProvider.federationToken
            }

        })
    }

//    fun deleFile(code: String, postion: Int) {
//        launchUI({
//            mModel.deleteFileInfo(code)
//            mList.removeAt(postion)
//        })
//
//    }

    fun deleFile(
        code: String,
        index: Int,
        postion: Int,
        adapter: MediaImageTypeListAdapter
    ) {
        launchUI({
            mModel.deleteFileInfo(code)
            mList[index].files?.removeAt(postion)
            adapter.notifyDataSetChanged()
        })
    }

    override fun enableLoadMore() = false
    override fun enableRefresh() = false

    private
    var timer: Timer? = null

    private
    var task: tTask? = null

    private
    var length = 0

    fun setTime(dataIndex: Int, fileIndex: Int,path: String, _length: Int) {
        length = _length
        var position = -1

        for ((index, data) in mList.withIndex()) {
            if (data.files.isNullOrEmpty()) {
                continue
            }
            for ((i, bean) in data.files!!.withIndex()) {
                if (bean.create_data.isNullOrEmpty()) {
                    continue
                }
                if (dataIndex==index&&i==fileIndex) {
                    position = index
                    mList[index].files?.get(i)?.startIng = 2
                } else {
                    mList[index].files?.get(i)?.startIng = 1
                }
            }


        }

        if (timer != null) {
            task?.cancel()
            task = null
            timer?.cancel()
            timer = null
        }
        if (position == -1 || fileIndex == -1) {
            return
        }
        if (_length == 0) {
            mList[position].files?.get(fileIndex)?.startIng = 1
            return
        }
        timer = Timer()
        task = tTask(position, fileIndex)
        timer?.schedule(task, 0, 1000)
    }

    fun vioceStop() {
        if (timer != null) {
            task?.cancel()
            task = null
            timer?.cancel()
            timer = null
        }
    }


    override fun onStop() {
        super.onStop()
        vioceStop()
    }


    inner class tTask(index: Int, fileIndex: Int) : TimerTask() {
        private val position = index
        private val mFileIndex = fileIndex
        override fun run() {
            if (position != -1) {
                var i = --length
                if (i < 0) {
                    i = 0
                }
                mList[position].files?.get(mFileIndex)?.lastTime = "$i 秒"
//                getmVoidSingleLiveEvent().value="notify"
            }


        }

    }

    fun getIndex(): Int {
        var index = -1
        for ((i, item) in mList.withIndex()) {
            if (item.type == MediaImageListViewModle.imageSelectType) {
                index = i
            }
        }
        return index
    }

    fun getmVoidSingleLiveEvent(): SingleLiveEvent<String> {
        return createLiveData(mVoidSingleLiveEvent).also {
            mVoidSingleLiveEvent = it
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
        timer = null
    }


}