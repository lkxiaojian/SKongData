package com.zky.task_chain.mvvm.viewmodle

import android.app.Application
import android.view.View
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import com.alibaba.android.arouter.launcher.ARouter
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.view.OptionsPickerView
import com.zky.basics.api.common.entity.chine.SelectPeople
import com.zky.basics.api.config.API
import com.zky.basics.api.room.bean.MediaBean
import com.zky.basics.api.splash.entity.Userinfo
import com.zky.basics.common.constant.Constants
import com.zky.basics.common.event.SingleLiveEvent
import com.zky.basics.common.mvvm.viewmodel.BaseViewModel
import com.zky.basics.common.util.*
import com.zky.basics.common.util.spread.decodeParcelable
import com.zky.basics.common.util.spread.removeLastString
import com.zky.basics.common.util.spread.showToast
import com.zky.basics.common.util.uploadFile.OSSAuthCredentialsProvider
import com.zky.basics.common.util.uploadFile.OssUploadingFileUtil
import com.zky.basics.common.util.uploadFile.UploadingFile
import com.zky.basics.common.util.view.CustomDialog
import com.zky.task_chain.R
import com.zky.task_chain.mvvm.model.ChainModel
import views.ViewOption.OptionsPickerBuilder
import java.util.*


/**
 * Created by lk
 * Date 2019-11-08
 * Time 11:06
 * Detail:
 */
class AddDealMessageViewModle(application: Application, model: ChainModel) :
    BaseViewModel<ChainModel>(application, model) {
    private val mApplication = application
    var zpList = arrayListOf<Any>("请示", "指派", "对接")

    //
    var queryType = ObservableField<String>()
    var pickerBuilder: OptionsPickerBuilder? = null
    var pickerView: OptionsPickerView<Any>? = null
    var zpMessage = ObservableField<String>()
    var content = ObservableField<String>()
    var longitude = ObservableField<Double>()
    var latitude = ObservableField<Double>()
    var locationMessage = ObservableField<String>()
    private var mVoidSingleLiveEvent: SingleLiveEvent<String>? = null
    val observablePeopleArrayList = ObservableArrayList<SelectPeople>()
    val observableArrayList = ObservableArrayList<MediaBean>()
    val parentCode = ObservableField<String>()
    val showJs = ObservableField<Boolean>()
    val taskCode = ObservableField<String>()
    var userinfo: Userinfo? = null
    var level = 0 //0 最高級別 1 中間級別 2 最低界別
    var message = ObservableField<String>()

    init {
        userinfo = decodeParcelable<Userinfo>("user")
        taskCode.set("")
        showJs.set(true)


    }

    fun startClick(view: View) {
        when (view.id) {
            R.id.aiv_del_people -> {
                getmVoidSingleLiveEvent().value = "startSelectPeople"
            }
            R.id.atv_type -> {
                showzpPicker()
            }
            R.id.cl_location -> {
                ARouter.getInstance().build(ARouterPath.LOCATION)
                    .withString("longitude", longitude.get()?.toString())
                    .withString("latitude", latitude.get()?.toString())
                    .withString("message", message.get())
                    .navigation()
            }

            R.id.abt_commit -> {
                commitData(view)
            }
        }
    }

    fun getLevel() {
        launchUI({
            val accountLevel = mModel.getAccountLevel(null)
            accountLevel?.let { it ->
                val accountLevel1 = userinfo?.accountLevel
                accountLevel1?.let { it1 ->
                    when {
                        it1 >= it[0].attr_idx -> {
                            zpList = arrayListOf<Any>("指派", "对接", "回复")
                            level = 0
                        }
                        it1 <= it[it.size - 1].attr_idx -> {
                            zpList = arrayListOf<Any>("请示", "对接", "回复")
                            level = 2
                        }
                        else -> {
                            zpList = arrayListOf<Any>("请示", "指派", "对接", "回复")
                            level = 1
                        }
                    }
                    zpMessage.set(zpList[0].toString())
                }

            }


        })

    }

    private fun commitData(view: View) {
        if (content.get().isNullOrEmpty()) {
            "内容为空".showToast()
            return
        }

        if (observablePeopleArrayList.isNullOrEmpty() && showJs.get() == true) {
            "${zpMessage.get()} 的人员为空".showToast()
            return
        }
        var receiverArr = ""
        observablePeopleArrayList.forEach {
            receiverArr = receiverArr + it.code + ","
        }

        val removeLastString = receiverArr.removeLastString()

        if (longitude.get() == null || latitude.get() == null) {
            "位置信息为空".showToast()
            return
        }
        if (queryType.get() != "send" && parentCode.get().isNullOrEmpty()) {
            parentCode.set(taskCode.get())
        }


        launchUI({
            val insertTaskLink = mModel.insertTaskLink(
                parentCode.get(),
                userinfo?.code,
                userinfo?.username,
                zpMessage.get(),
                content.get(),
                removeLastString,
                longitude.get()?.toString(),
                latitude.get()?.toString(),
                message.get()
            )

            if (taskCode.get().isNullOrEmpty()) {
                taskCode.set(insertTaskLink?.toString())
            }
            delFileData(view, insertTaskLink?.toString())

        }, object : BaseViewModel.NetError {
            override fun getError(e: Exception) {

            }
        })
    }

    private fun delFileData(view: View, relationCode: String?) {

        if (observableArrayList.isNullOrEmpty()) {
            return
        }
        if (relationCode.isNullOrEmpty()) {
            return
        }
//            val userinfo = decodeParcelable<Userinfo>("user")
        var uploadingList = arrayListOf<UploadingFile>()


        for ((_, value) in observableArrayList.withIndex()) {
            if (value.videoImagePath != null) {
                val uploadingFile = UploadingFile()
                uploadingFile.servicePath = API.uploadFileInfo
                uploadingFile.filePath = value.file_path
                uploadingFile.fileName = value.file_name
                uploadingFile.parentCode = parentCode.get()
                uploadingFile.tmpCode = value.code
                val map = hashMapOf<String, String>()
                val code = if (taskCode.get().isNullOrEmpty()) UUID.randomUUID().toString()
                    .replace("-", "") else taskCode.get()
                val filePath =
                    "${API.PROJECT_NAME}/tasklink/${code}/${value.file_type}/${value.file_name}"
                map["filePath"] = filePath
                map["relationCode"] = relationCode
                map["mediaType"] = value.file_type
                map["fileName"] = value.file_name
                map["createTime"] = value.create_data
                map["itemCode"] = Constants.itemCode
                map["code"] = value.code
                map["mediaType2"] = value.mediaType2
                map["mediaType3"] = value.mediaType3
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

        if (uploadingList.size == 0) {
            ToastUtil.showToast("上传成功")
            return
        }

        uploadFile(view, uploadingList)

    }


    private fun uploadFile(view: View, uploadingList: ArrayList<UploadingFile>) {
        val onShowDialogLoading = onShowDialogLoading(view.context)
        dialogSetText(onShowDialogLoading, "上传中。。。")
        OssUploadingFileUtil().upLoadingImageFile(mApplication, uploadingList,
            object : UpLoadingFileListener {
                override fun upLoadSuccessPostiion(file: UploadingFile) {
                    val tmpCode = file.params?.get("code")
                    for ((index, value) in observableArrayList.withIndex()) {
                        if (value.code == tmpCode) {
                            observableArrayList[index]?.upload = true
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

//        val userinfo = decodeParcelable<Userinfo>("user")
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

    fun getmVoidSingleLiveEvent(): SingleLiveEvent<String> {
        return createLiveData(mVoidSingleLiveEvent).also {
            mVoidSingleLiveEvent = it
        }
    }


    fun showzpPicker() {
        val list = zpList
        pickerView?.setPicker(list)
        pickerView?.show()
        pickerBuilder?.setOnOptionsSelectListener(OnOptionsSelectListener { options1, _, _, _ ->
            val toString = list[options1].toString()
            zpMessage.set(toString)
            showJs.set(toString != "回复")
        })
    }

}