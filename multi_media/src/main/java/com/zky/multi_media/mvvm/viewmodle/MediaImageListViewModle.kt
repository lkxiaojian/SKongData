package com.zky.multi_media.mvvm.viewmodle

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider
import com.zky.basics.api.config.API
import com.zky.basics.api.file.FileData
import com.zky.basics.api.room.bean.MediaBean
import com.zky.basics.api.splash.entity.Userinfo
import com.zky.basics.common.constant.Constants
import com.zky.basics.common.constant.Constants.itemCode
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
import com.zky.multi_media.adapter.MediaImageListAdapter
import com.zky.multi_media.adapter.MediaImageTypeListAdapter
import com.zky.multi_media.mvvm.model.MediaModel
import java.util.ArrayList

/**
 * Created by lk
 * Date 2020/2/27
 * Time 17:07
 * Detail:
 */
class MediaImageListViewModle(application: Application, mediaModel: MediaModel) :
    BaseRefreshViewModel<FileData, MediaModel>(application, mediaModel) {
    private val applicatio = application
    var fileType = ObservableField<String>()
    private var mVoidSingleLiveEvent: SingleLiveEvent<String>? = null

    override fun refreshData() {
    }

    override fun loadMore() {
    }

    override fun enableLoadMore() = false
    override fun enableRefresh() = false

    fun getFileData() {
        getmVoidSingleLiveEvent().value = "showLoading"
        launchUI({
            val fileList = mModel.getFileList(itemCode, fileType.get())
            for ((index, value) in mList.withIndex()) {
                fileList?.forEach {
                    if(value.titleTmp==it.mediaType2&&value.subTile==it.mediaType3){
                        val bean = instanceOf<MediaBean>()
                        bean.code = it.code
                        bean.file_name = it.fileName
                        bean.file_path = it.filePath
                        bean.upload = true
                        bean.file_type = it.mediaType
                        bean.uploader = it.userName
                        bean.create_data = it.createDate
                        bean.videoImagePath = it.filePath
                        bean.mediaType2 = value.titleTmp
                        bean.mediaType3 = it.mediaType3
                        mList[index].files?.add(bean)
                    }
                }
            }
            getmVoidSingleLiveEvent().value = "notify"
        },object :NetError{
            override fun getError(e: Exception) {
                getmVoidSingleLiveEvent().value = "dimssLoding"
            }
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


                for ((_, value) in mList.withIndex()) {
                    value.files?.forEach { it ->
                        if (!it.upload && it.videoImagePath != null) {
                            val uploadingFile = UploadingFile()
                            uploadingFile.servicePath = API.URL_UPLOAD_NEW
                            uploadingFile.filePath = it.file_path
                            uploadingFile.fileName = it.file_name
                            uploadingFile.tmpCode = it.code
                            val map = hashMapOf<String, String>()
                            val filePath =
                                "skData/${itemCode}/${it.file_type}/${value.type}/${it.code}/${it.file_name}"
                            map["filePath"] = filePath
                            map["mediaType"] = it.file_type
                            map["fileName"] = it.file_name
                            map["createTime"] = it.create_data
                            map["itemCode"] = itemCode
                            map["code"] = it.code
                            map["mediaType2"] = value.titleTmp
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
                                onShowDialogLoading.dismiss()
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

    fun deleFile(code: String, index: Int, postion: Int, adapter: MediaImageTypeListAdapter) {
        launchUI({
            mModel.deleteFileInfo(code)
            mList[index].files?.removeAt(postion)
            adapter.notifyDataSetChanged()
        })
    }

    fun getIndex(): Int {
        var index = -1
        for ((i, item) in mList.withIndex()) {
            if (item.type == imageSelectType) {
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

    companion object {
        var videoSelectType = ""
        var imageSelectType = ""
    }
}