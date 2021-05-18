package com.zky.zky_mine.fragment


import android.content.Intent
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.zky.basics.common.mvvm.BaseMvvmFragment
import com.zky.basics.common.util.FileUtil
import com.zky.basics.common.util.Glide4Engine
import com.zky.basics.common.util.SmartMediaPickerComsur
import com.zky.zky_mine.BR
import com.zky.zky_mine.R
import com.zky.zky_mine.mvvm.factory.MineViewModelFactory
import com.zky.zky_mine.mvvm.viewmodle.MineViewModle
import me.bzcoder.mediapicker.config.MediaPickerEnum

/**
 * Created by lk
 * Date 2019-10-28
 * Time 16:45
 * Detail:
 */
class MainMineFragment : BaseMvvmFragment<ViewDataBinding, MineViewModle>() {
    override fun initView(view: View?) {
    }

    override fun initData() {
        mViewModel?.getAppToken()
    }

    override fun onBindViewModel() = MineViewModle::class.java

    override fun onBindViewModelFactory() = MineViewModelFactory.getInstance(mActivity.application)

    override fun initViewObservable() {
        mViewModel?.getmVoidSingleLiveEvent()?.observe(this, Observer {
            if (it == "selectImage") {
                selectMedia()
            }
        })

    }


    private fun selectMedia() {
        SmartMediaPickerComsur.builder(activity) //最大图片选择数目 如果不需要图片 将数目设置为0
            .withMaxImageSelectable(1) //最大视频选择数目 如果不需要视频 将数目设置为0
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
            SmartMediaPickerComsur.getResultData(activity, requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
        if (resultData != null && resultData.size > 0) {

            val imaFile = FileUtil.isImageFile(resultData[0])
            if (!imaFile) {
                return
            }

            var list = mutableListOf<String>()
            list.add(resultData[0])
          mViewModel?.upLoad(list)
        }


    }


    override fun onBindVariableId(): Int {
        return BR.mineViewModle
    }

    companion object {
        @JvmStatic
        fun newInstance(): Fragment {
            return MainMineFragment()
        }
    }

    override fun onBindLayout() = R.layout.main_mine_fragment

    override fun getToolbarTitle() = ""

    override fun enableToolbar() = false

}