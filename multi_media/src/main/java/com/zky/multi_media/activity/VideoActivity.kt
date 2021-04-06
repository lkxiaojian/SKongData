package com.zky.multi_media.activity

import android.content.pm.ActivityInfo
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.zky.basics.api.config.API
import com.zky.basics.api.room.bean.MediaBean
import com.zky.basics.common.mvvm.BaseActivity
import com.zky.basics.common.util.ScreenUtils.getStatusHeight
import com.zky.multi_media.R
import kotlinx.android.synthetic.main.activity_video.*


@Route(path = ARouterPath.MEDIA_SHOW_VIDEO)
class VideoActivity : BaseActivity() {
    @Autowired
    lateinit var file: MediaBean
    private var orientationUtils: OrientationUtils? = null

    override fun onBindLayout() = R.layout.activity_video

    override fun initView() {
    }

    override fun initData() {
        var  path=file.file_path
        if(!path.startsWith("http",false)){
            path=API.ImageFolderPath+path
        }


        video_player.setUp(path, true, file.file_name)
        //增加封面
        val imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        Glide.with(this).applyDefaultRequestOptions(RequestOptions().frame(1)).asBitmap()
            .load(path)
            .into(imageView)
        val topView =
            video_player.findViewById<LinearLayout?>(R.id.layout_top)
        topView?.let {
            val params = it.layoutParams as RelativeLayout.LayoutParams
            params.height = params.height + getStatusHeight(this)
            it.gravity = Gravity.BOTTOM
            it.layoutParams = params
        }
        video_player.thumbImageView = imageView
        //增加title
        video_player.titleTextView.visibility = View.VISIBLE
        //设置返回键
        video_player.backButton.visibility = View.VISIBLE
        //设置旋转
        orientationUtils = OrientationUtils(this, video_player)
        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
        video_player.fullscreenButton.setOnClickListener { orientationUtils?.resolveByClick() }
        //是否可以滑动调整
        video_player.setIsTouchWiget(true)
        //设置返回按键功能
        video_player.backButton.setOnClickListener { onaBackPressed() }
        video_player.startPlayLogic()
    }


    override fun onResume() {
        super.onResume()
        video_player.onVideoResume()
    }

    override fun onPause() {
        super.onPause()
        video_player.onVideoPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
    }

    private fun onaBackPressed() {
        //先返回正常状态
        if (orientationUtils?.screenType == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            video_player.fullscreenButton.performClick()
            return
        }
        //释放所有
        video_player.setVideoAllCallBack(null)
        onBackPressed()
    }

    override fun enableToolbar() = false

    override val isFullScreen: Boolean = true


}
