package com.zky.task_chain.activity

import android.Manifest
import android.content.Intent
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.hjq.permissions.OnPermission
import com.hjq.permissions.XXPermissions
import com.zky.basics.api.common.entity.chine.SelectPeople
import com.zky.basics.api.room.bean.MediaBean
import com.zky.basics.api.splash.entity.Userinfo
import com.zky.basics.common.adapter.BaseBindAdapter
import com.zky.basics.common.mvvm.BaseMvvmActivity
import com.zky.basics.common.util.*
import com.zky.basics.common.util.reflec.instanceOf
import com.zky.basics.common.util.spread.decodeParcelable
import com.zky.task_chain.BR
import com.zky.task_chain.R
import com.zky.task_chain.activity.SelectPeopleActivity.Companion.selects
import com.zky.task_chain.adapter.ImageViewListAdapter
import com.zky.task_chain.adapter.PeopleListedAdapter
import com.zky.task_chain.databinding.ActivityAddDealMessageBinding
import com.zky.task_chain.mvvm.factory.TaskChineViewModelFactory
import com.zky.task_chain.mvvm.viewmodle.AddDealMessageViewModle
import me.bzcoder.mediapicker.config.MediaPickerEnum
import java.util.*

class AddDealMessageActivity :
    BaseMvvmActivity<ActivityAddDealMessageBinding, AddDealMessageViewModle>(),
    BaseBindAdapter.OnItemClickListener<Any> {
    private lateinit var imageViewListAdapter: ImageViewListAdapter
    private lateinit var peopleAdapter: PeopleListedAdapter
    private val userinfo = decodeParcelable<Userinfo>("user")
    private val observableArrayList = ObservableArrayList<MediaBean>()
    private val observablePeopleArrayList = ObservableArrayList<SelectPeople>()
    private var type = ""

    override fun onBindViewModel() = AddDealMessageViewModle::class.java

    override fun onBindViewModelFactory() = TaskChineViewModelFactory.getInstance(application)

    override fun initViewObservable() {
        type = intent.getStringExtra("type")
        mViewModel?.getmVoidSingleLiveEvent()?.observe(this, androidx.lifecycle.Observer {
            when (it) {
                "startSelectPeople" -> {
                    val intent = Intent(this, SelectPeopleActivity::class.java)
                    intent.putExtra("type",mViewModel?.queryType?.get())
                    startActivity(intent)
                }
            }

        })


        observableArrayList.add(instanceOf<MediaBean>())
        imageViewListAdapter = ImageViewListAdapter(this, observableArrayList)
        imageViewListAdapter.setItemClickListener(this)
        mBinding?.rvImgave?.layoutManager = GridLayoutManager(this, 3)
        mBinding?.rvImgave?.adapter = imageViewListAdapter

        peopleAdapter= PeopleListedAdapter(this, observablePeopleArrayList)
        peopleAdapter.setItemClickListener(this)
        mBinding?.rvJsr?.layoutManager = GridLayoutManager(this, 2)
        mBinding?.rvJsr?.adapter = peopleAdapter

        observablePeopleArrayList.addOnListChangedCallback(
            ObservableListUtil.getListChangedCallback(
                peopleAdapter
            )
        )

        mViewModel?.queryType?.set(type)
        val optionsPickerBuilder = OptionsPickerBuilder()
        var pickerBuilder = this.let { optionsPickerBuilder.pickerBuilder(it) }
        mViewModel?.pickerBuilder = pickerBuilder
        mViewModel?.pickerView = pickerBuilder?.build()
    }

    override fun onBindVariableId() = BR.addDealMessageViewModle
    override fun onBindLayout() = R.layout.activity_add_deal_message

    override val tootBarTitle = "新增处理"
    private var posIndex = 0
    override fun onItemClick(e: Any, position: Int) {
        if (e is MediaBean) {
            if (e.videoImagePath.isNullOrEmpty()) {
                //选择照片
                XXPermissions.with(this@AddDealMessageActivity).permission(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
                ).request(object :
                    OnPermission {
                    override fun hasPermission(granted: MutableList<String>?, all: Boolean) {
                        if (all) {
                            posIndex = position
                            selectImageView()
                        }
                    }

                    override fun noPermission(denied: MutableList<String>?, never: Boolean) {
                        PermissionToSetting(
                            this@AddDealMessageActivity,
                            denied!!,
                            never,
                            "获取存储权限失败"
                        )
                    }

                })
            } else {
                //预览
                val projectPhoto = arrayListOf<MediaBean>()
                projectPhoto.addAll(observableArrayList)
                projectPhoto.removeAt(observableArrayList.size - 1)
                ARouter.getInstance().build(ARouterPath.MEDIA_SHOW_IMAGE)
                    .withInt("position", position)
                    .withSerializable("images", projectPhoto).navigation()
            }
        } else if(e is SelectPeople){
            observablePeopleArrayList.removeAt(position)
        }
    }


    private fun selectImageView() {
        SmartMediaPickerComsur.builder(this) //最大图片选择数目 如果不需要图片 将数目设置为0
            .withMaxImageSelectable(9) //最大视频选择数目 如果不需要视频 将数目设置为0
            .withMaxVideoSelectable(0) //图片选择器是否显示数字
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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val resultData =
            SmartMediaPickerComsur.getResultData(this, requestCode, resultCode, data)
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
                bean.file_type = "image"
                bean.videoImagePath = item
                bean.uploader = userinfo?.username
                bean.file_name = FileUtil.getNameByPath(item)
                bean.mediaType2 = item
                bean.upload = false
                bean.mediaType2 = ""
                bean.mediaType3 = ""
                tmpList.add(bean)
            }
//            observableArrayList.addAll(tmpList)
            observableArrayList.addAll(posIndex, tmpList)
            imageViewListAdapter.notifyDataSetChanged()

        }


    }

    override fun onResume() {
        super.onResume()
        observablePeopleArrayList.clear()
        observablePeopleArrayList.addAll(selects)
    }
    override fun onDestroy() {
        super.onDestroy()
        selects.clear()
    }
}