package com.zky.zky_mine.mvvm.viewmodle

import android.Manifest
import android.app.Application
import android.content.Intent

import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.fragment.app.FragmentActivity
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider
import com.hjq.permissions.OnPermission
import com.hjq.permissions.XXPermissions
import com.zky.basics.api.config.API
import com.zky.basics.api.splash.entity.Userinfo
import com.zky.basics.common.constant.Constants
import com.zky.basics.common.event.SingleLiveEvent
import com.zky.basics.common.mvvm.viewmodel.BaseViewModel
import com.zky.basics.common.util.*
import com.zky.basics.common.util.security.MMKVUtil
import com.zky.basics.common.util.security.SM3
import com.zky.basics.common.util.security.SPSecurityUtils
import com.zky.basics.common.util.spread.decode
import com.zky.basics.common.util.spread.decodeParcelable
import com.zky.basics.common.util.spread.encode
import com.zky.basics.common.util.spread.showToast
import com.zky.basics.common.util.uploadFile.OSSAuthCredentialsProvider
import com.zky.basics.common.util.uploadFile.UploadingFile
import com.zky.basics.common.view.PhotoSelectDialog

import com.zky.zky_mine.R
import com.zky.zky_mine.activity.AboutActivity
import com.zky.zky_mine.activity.ModificationPasswadActivity
import com.zky.zky_mine.entity.MineModelBean
import com.zky.zky_mine.mvvm.model.MineModel

import com.zky.basics.common.util.uploadFile.OssUploadingFileUtil
import java.io.File

/**
 * Created by lk
 * Date 2019-11-08
 * Time 11:06
 * Detail:
 */
class MineViewModle(application: Application, model: MineModel) :
    BaseViewModel<MineModel>(application, model) {
    var modifyPaw = ObservableField<String>()
    var modifyQrPaw = ObservableField<String>()
    var oldPaw = ObservableField<String>()
    var mineBean = ObservableField<MineModelBean>()
    var userInfo = ObservableField<Userinfo>()
    var mApplication: Application? = null
    private var mVoidSingleLiveEvent: SingleLiveEvent<String>? = null

    init {
        mApplication = application
        modifyPaw.set("")
        modifyQrPaw.set("")
        oldPaw.set("")
        val mineModelBean = MineModelBean()
        val userinfo = decodeParcelable<Userinfo>("user")
        mineModelBean.errorHeard = R.drawable.head_img
        mineModelBean.imageHeard =
            API.ImageFolderPath + "".decode("headImgPath")
        mineModelBean.curName = userinfo?.username
        mineModelBean.phone = userinfo?.phone
        mineModelBean.fzqy = if (userinfo?.province == null) "" else userinfo?.province +
                if (userinfo?.city == null) "" else userinfo?.city +
                        if (userinfo?.county == null) "" else userinfo?.county +
                                if (userinfo?.town == null) "" else userinfo?.town +
                                        if (userinfo?.village == null) "" else userinfo?.village
        mineBean.set(mineModelBean)

    }


    fun startClick(view: View) {
        when (view.id) {
            R.id.about -> {
                view.context.startActivity(Intent(view.context, AboutActivity::class.java))
            }
            R.id.password -> {
                val intent = Intent(view.context, ModificationPasswadActivity::class.java)
                intent.putExtra("safe", "safe")
                view.context.startActivity(intent)
            }

            R.id.modify_paw -> {
                modifPaw()
            }
            R.id.head_img -> {
                //选择头像
                if (!NetUtil.checkNet()) {
                    ToastUtil.showToast("当前没有网络")
                    return
                }
                val viewToActivity = ViewToActivity(view)
                XXPermissions.with(viewToActivity).permission(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
                ).request(object :
                    OnPermission {
                    override fun hasPermission(granted: MutableList<String>?, all: Boolean) {
                        if (all) {
                            val photoSelectDialog = PhotoSelectDialog.newInstance()
                            photoSelectDialog.show(viewToActivity.supportFragmentManager, "选择照片")
                            photoSelectDialog.setCount(1)
                            photoSelectDialog.setOnClickLisener(object :
                                PhotoSelectDialog.OnPhotoClickLisener {
                                override fun onTakePhototClick(path: String) {
                                    Log.e("tag", "path")
                                    var list = mutableListOf<String>()
                                    list.add(path)
                                    upLoad(list, viewToActivity)
                                }

                                override fun onSelectPhotoClick(list: MutableList<String>?) {
                                    upLoad(list, viewToActivity)
                                }

                            })
                        }
                    }

                    override fun noPermission(denied: MutableList<String>?, never: Boolean) {
                        PermissionToSetting(viewToActivity, denied!!, never, "获取存储权限失败")
                    }

                })
            }

        }

    }

    private fun upLoad(list: MutableList<String>?, viewToActivity: FragmentActivity) {

        val userinfo = decodeParcelable<Userinfo>("user")
        if (list != null && list.size > 0) {
            mineBean.get()?.imageHeard = list[0]
            //把图片上传到oos
            val file = File(list[0])
            val uploadingFile = UploadingFile()
            uploadingFile.servicePath = API.URL_UPLOAD_HEARD
            uploadingFile.filePath = list[0]
            uploadingFile.fileName = file.name
            val map = hashMapOf<String, String>()
            val filePath =
                "user/headImg/${
                userinfo?.code
                }/${uploadingFile.fileName}"

            map["filePath"] = filePath
            map["headImgPath"] = filePath
            userinfo?.code?.let {
                map["code"] = it
            }
            map["servicePath"] = API.URL_UPLOAD_HEARD
            uploadingFile.params = map
            var uploadingList = arrayListOf<UploadingFile>()
            uploadingList.add(uploadingFile)
            OssUploadingFileUtil().upLoadingImageFile(mApplication!!, uploadingList,
                object : UpLoadingFileListener {
                    override fun upLoadSuccessPostiion(file: UploadingFile) {

                    }

                    override fun upLoadingProgress(
                        progress: Int,
                        index: Int,
                        fileTotalProgress: Int
                    ) {
                    }

                    override fun upLoadSuccess(failNum: Int) {
                        "上传头像成功".showToast()
                    }

                })

        }
    }

    private fun modifyInfo() {
        if (mineBean.get()?.userName == userInfo.get()?.username) {
            ToastUtil.showToast("修改姓名与原姓名一致")
            return
        }
        if (mineBean.get()?.userName.isNullOrEmpty()) {
            ToastUtil.showToast("姓名为空")
            return
        }
        if (!InfoVerify.isChinese(mineBean.get()?.userName)) {
            ToastUtil.showToast("姓名只能是中文")
            return
        }


        getmVoidSingleLiveEvent()?.value = "show"
        mVoidSingleLiveEvent?.call()
        launchUI({

            mModel.updateUserByCode(mineBean.get()?.userName, "", userInfo.get()?.code)
            ToastUtil.showToast("修改成功")
            mineBean.get()?.curName = mineBean.get()?.userName
            MMKVUtil.put("userName", mineBean.get()?.curName)
            getmVoidSingleLiveEvent()?.value = "success"
            mVoidSingleLiveEvent?.call()
        }, object : NetError {
            override fun getError(e: Exception) {
                getmVoidSingleLiveEvent()?.value = "dismiss"
                mVoidSingleLiveEvent?.call()
            }

        })


    }


    private fun modifPaw() {

        if (oldPaw.get().isNullOrEmpty()) {
            ToastUtil.showToast("原密码为空")
            return
        }

        if (modifyPaw.get().isNullOrEmpty()) {
            ToastUtil.showToast("新密码为空")
            return
        }
        if (modifyQrPaw.get().isNullOrEmpty()) {
            ToastUtil.showToast("确认密码为空")
            return
        }

        if (modifyPaw.get().isNullOrEmpty()) {
            ToastUtil.showToast("请输入6-20位字母和数字组合，必须同时含有字母和数字！")
            return
        }

        if (modifyPaw.get() != modifyQrPaw.get()) {
            ToastUtil.showToast("确认密码和新密码不一致！")
            return
        }

        val userinfo = decodeParcelable<Userinfo>("user")
        val phone = userinfo?.phone

        val oldMd5Paw = userinfo?.password
        val newPaw = SM3.encrypt(modifyPaw.get().toString())

        if (oldMd5Paw != SM3.encrypt(oldPaw.get().toString())) {
            "原密码错误".showToast()
            return
        }
        getmVoidSingleLiveEvent()?.value = "show"
        mVoidSingleLiveEvent?.call()
        launchUI({

            mModel.updateUserPassword("update", phone.toString(), oldMd5Paw.toString(), newPaw, "")
            newPaw.encode("password")
            "修改成功".showToast()
            getmVoidSingleLiveEvent()?.value = "success"
        }, object : NetError {
            override fun getError(e: Exception) {
                getmVoidSingleLiveEvent()?.value = "dismiss"
            }

        })
    }

    fun getmVoidSingleLiveEvent(): SingleLiveEvent<String>? {
        mVoidSingleLiveEvent = createLiveData(mVoidSingleLiveEvent)
        return mVoidSingleLiveEvent
    }

    fun getInfo() {
        val phone = SPSecurityUtils.get(mApplication, "phone", "")
        launchUI({
            mVoidSingleLiveEvent?.call()
            val user = mModel.getUser(phone.toString())
            if (user != null) {
                mineBean.get()?.countLevel = user.accountLevel
                mineBean.get()?.state = user.accountStatus
                userInfo.set(user)
            }

        }, object : NetError {
            override fun getError(e: Exception) {
                mVoidSingleLiveEvent?.call()
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


}