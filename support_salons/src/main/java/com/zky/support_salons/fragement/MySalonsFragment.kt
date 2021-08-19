package com.zky.support_salons.fragement

import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.zky.basics.common.adapter.FragmentPager2Adapter
import com.zky.basics.common.mvvm.BaseFragment
import com.zky.support_salons.R

/**
 * @Description:     沙龙 我的
 * @Author:         lk
 * @CreateDate:     2021/8/19 11:43
 */
class MySalonsFragment:BaseFragment() {
    private val titles = arrayListOf<String>("我的发表", "我的评论","我的消息")
    private val mListFragments = arrayListOf<Fragment>()
    override fun onBindLayout()= R.layout.my_salons_fragment

    override fun initView(view: View?) {
        val viewPage = view?.findViewById<ViewPager2>(R.id.pager_tour_task)
        val tabLayout = view?.findViewById<TabLayout>(R.id.layout_tour)
        mListFragments.add(OpenPublishFragment("publish"))
        mListFragments.add(MyRemarkFragment())
        mListFragments.add(MyNewsFragment())
        val fragmentPager2Adapter = FragmentPager2Adapter(mActivity, mListFragments)

        viewPage?.adapter = fragmentPager2Adapter
        viewPage?.currentItem = 0
        viewPage?.isUserInputEnabled = false
        //切换tab页
        viewPage?.let {
            TabLayoutMediator(
                tabLayout!!, it
            ) { tab, position ->
                tab.text = titles[position]
            }.attach()
        }

    }

    override fun initData() {
    }
}