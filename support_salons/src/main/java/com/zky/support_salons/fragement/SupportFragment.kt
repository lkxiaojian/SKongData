package com.zky.support_salons.fragement

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.zky.basics.common.adapter.FragmentPager2Adapter
import com.zky.basics.common.mvvm.BaseFragment
import com.zky.support_salons.R

/**
 * @Description:     帮扶沙龙
 * @Author:         lk
 * @CreateDate:     2021/8/17 15:09
 */
class SupportFragment : BaseFragment() {
    private val titles = arrayListOf<String>("公开", "我的")
    private val mListFragments = arrayListOf<Fragment>()
    override fun onBindLayout()= R.layout.support_fragment
    override fun initView(view: View?) {
        val viewPage = view?.findViewById<ViewPager2>(R.id.pager_tour_task)
        val tabLayout = view?.findViewById<TabLayout>(R.id.layout_tour)
        tabLayout?.setSelectedTabIndicatorHeight(0)
        val aivAddTaskMessage =
            view?.findViewById<AppCompatImageView>(R.id.aiv_add_task_message)

        val fragmentPager2Adapter = FragmentPager2Adapter(mActivity, mListFragments)
        mListFragments.add(Fragment())
        mListFragments.add(Fragment())
        viewPage?.adapter = fragmentPager2Adapter
        viewPage?.currentItem = 0
        viewPage?.isUserInputEnabled = false
        viewPage?.let {
            TabLayoutMediator(
                tabLayout!!, it
            ) { tab, position ->
                tab.text = titles[position]
            }.attach()
        }

        tabLayout?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            @RequiresApi(Build.VERSION_CODES.M)
            override fun onTabSelected(tab: TabLayout.Tab) {
//                    setTab(tab)
            }

            @RequiresApi(Build.VERSION_CODES.M)
            override fun onTabUnselected(tab: TabLayout.Tab) {
                tab.customView = null
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }

    override fun initData() {
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private val fragment = SupportFragment()
        fun getSupportInstance(): Fragment {
            return fragment
        }

    }
}