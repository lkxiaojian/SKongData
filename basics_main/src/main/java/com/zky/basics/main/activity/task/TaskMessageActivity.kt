package com.zky.basics.main.activity.task

import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.util.TypedValue
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.zky.basics.api.common.entity.task.TaskBean
import com.zky.basics.api.splash.entity.Userinfo
import com.zky.basics.common.adapter.FragmentPager2Adapter
import com.zky.basics.common.constant.Constants.itemCode
import com.zky.basics.common.constant.Constants.taskCode
import com.zky.basics.common.mvvm.BaseActivity
import com.zky.basics.common.provider.*
import com.zky.basics.common.util.spread.decodeParcelable
import com.zky.basics.main.R
import kotlinx.android.synthetic.main.activity_task_message.*
import java.util.*

class TaskMessageActivity : BaseActivity() {
    @JvmField
    @Autowired(name = ARouterPath.MEDIA)
    var mMineProvider: IMediaProvider? = null

    @JvmField
    @Autowired(name = ARouterPath.MAP_SHOW)
    var iMapProvider: IMapProvider? = null

    // 音频选择
    @JvmField
    @Autowired(name = ARouterPath.MEDIA_SELECT_SHOW_VOICE)
    var iMediaSelectVoiceProvider: IMediaSelectVoiceProvider? = null

    //视频选择
    @JvmField
    @Autowired(name = ARouterPath.MEDIA_SELECT_VIDEO)
    var iMediaSelectVideoProvider: IMediaSelectVideoProvider? = null

    @JvmField
    @Autowired(name = ARouterPath.QUESTION)
    var iQuestionProvider: IQuestionProvider? = null


    //    "空间数据", "问卷信息", "照片信息", "视频信息", "音频信息"
    private val titles = arrayListOf<String>()

    private val mListFragments = ArrayList<Fragment>()
    override fun onBindLayout() = R.layout.activity_task_message

    override fun initView() {
    }

    override val tootBarTitle= decodeParcelable<Userinfo>("user")?.username.toString()
    override fun initData() {
        try {
            titles.clear()
            val taskBean = intent.extras["dataTask"] as TaskBean
            itemCode = intent.extras["itemCode"].toString()

            taskCode = taskBean.taskCode
            if (!taskBean.spaceDataType.isNullOrEmpty()) {
                titles.add("空间数据")
                mListFragments.add(iMapProvider?.mapFragment!!)
            }
            titles.add("问卷信息")
            mListFragments.add(iQuestionProvider?.questionFragment!!)

            taskBean.mediaDataType?.let {

                if (it.contains("照片")) {
                    titles.add("照片信息")
                    mListFragments.add(mMineProvider?.mediaFragment(itemCode)!!)
                }

                if (it.contains("视频")) {
                    titles.add("视频信息")
                    mListFragments.add(iMediaSelectVideoProvider?.mediaVideoFragment!!)
                }
                if (it.contains("音频")) {
                    titles.add("音频信息")
                    mListFragments.add(iMediaSelectVoiceProvider?.mediaVoiceFragment(itemCode)!!)
                }
            }

            val fragmentPager2Adapter = FragmentPager2Adapter(this, mListFragments)

            pager_tour_task?.adapter = fragmentPager2Adapter
            pager_tour_task.currentItem = 0
            pager_tour_task.isUserInputEnabled = false
            //添加动画
//        pager_tour_task.setPageTransformer(ZoomOutPageTransformer())
            //切换tab页
            TabLayoutMediator(
                layout_tour, pager_tour_task
            ) { tab, position ->
                tab.text = titles[position]
            }.attach()

            setTab(layout_tour.getTabAt(0)?.view?.tab)
            layout_tour.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                @RequiresApi(Build.VERSION_CODES.M)
                override fun onTabSelected(tab: TabLayout.Tab) {
                    setTab(tab)
                }

                @RequiresApi(Build.VERSION_CODES.M)
                override fun onTabUnselected(tab: TabLayout.Tab) {
                    tab.customView = null
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onBindToolbarLayout() = R.layout.white_common_toolbar
    override val isFullScreen= false

    private fun setTab(tab: TabLayout.Tab?) {
        if (tab == null) {
            return
        }
        val textView = TextView(this@TaskMessageActivity)
        val selectedSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            7f,
            resources?.displayMetrics
        )
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, selectedSize)
        textView.setTextColor(
            ContextCompat.getColor(
                this@TaskMessageActivity,
                R.color.color_3388EF
            )
        )
        textView.typeface = Typeface.defaultFromStyle(Typeface.BOLD)//加粗
        textView.text = tab.text
        tab.customView = textView
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (supportFragmentManager.fragments.size > 0) {
            val fragments = supportFragmentManager.fragments
            fragments.forEach {
                it.onActivityResult(requestCode, resultCode, data)
            }
        }
    }
}