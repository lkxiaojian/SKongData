package com.zky.multi_media.activity

import android.annotation.SuppressLint
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.zky.basics.api.room.bean.MediaBean
import com.zky.basics.common.mvvm.BaseActivity
import com.zky.multi_media.R
import com.zky.multi_media.adapter.ImagePrePagerAdapter
import kotlinx.android.synthetic.main.activity_image.*


@Route(path = ARouterPath.MEDIA_SHOW_IMAGE)
class ImageActivity : BaseActivity() {

    @Autowired
    lateinit var images: ArrayList<MediaBean>

    override fun onBindLayout() = R.layout.activity_image

    override fun initView() {
    }

    @SuppressLint("SetTextI18n")
    override fun initData() {
        val adapter = ImagePrePagerAdapter(this)
        var position = intent.getIntExtra("position", -1)
        adapter.startIndex = position
        adapter.addAll(images)
        viewPager.adapter = adapter
        viewPager.currentItem = position
        preview_title_top.text = "${position + 1}/${adapter.count}"
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {}

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}

            override fun onPageSelected(position: Int) {
                preview_title_top.text = "${position + 1}/${adapter.count}"
            }
        })

        preview_back_top.setOnClickListener {
            finish()
        }
    }


    override fun enableToolbar()=false

    override val isFullScreen=true

}
