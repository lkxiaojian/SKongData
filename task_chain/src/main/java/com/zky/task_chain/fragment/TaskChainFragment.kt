package com.zky.task_chain.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.zky.basics.common.adapter.FragmentPager2Adapter
import com.zky.basics.common.mvvm.BaseFragment
import com.zky.task_chain.R
import com.zky.task_chain.activity.AddDealMessageActivity
import kotlinx.android.synthetic.main.task_chain_fragment.*
import java.lang.Exception

/**
 *create_time : 2021/5/18 上午9:13
 *author: lk
 *description： TaskChainFragment
 */
class TaskChainFragment : BaseFragment() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private val fragment = TaskChainFragment()
        fun getChainInstance(): Fragment {
            return fragment
        }

        var needFlush=false
    }

    private val titles = arrayListOf<String>("已发布任务", "已接受任务")
    private val mListFragments = arrayListOf<Fragment>()
    override fun onBindLayout() = R.layout.task_chain_fragment
    override fun initView(view: View?) {
        try {
            val viewPage = view?.findViewById<ViewPager2>(R.id.pager_tour_task)
            val tabLayout = view?.findViewById<TabLayout>(R.id.layout_tour)
            val aivAddTaskMessage =
                view?.findViewById<AppCompatImageView>(R.id.aiv_add_task_message)
            aivAddTaskMessage?.setOnClickListener {
                val type = if (viewPage?.currentItem == 0) "send" else "receive"
                val intent = Intent(mActivity, AddDealMessageActivity::class.java)
                intent.putExtra("type", type)
                intent.putExtra("parentCode", "")
                intent.putExtra("taskCode", "")
                intent.putExtra("message", "")
                mActivity.startActivity(intent)
            }
            mListFragments.add(TaskChainListFragment("send"))
            mListFragments.add(TaskChainListFragment("receive"))
            val fragmentPager2Adapter = FragmentPager2Adapter(mActivity, mListFragments)

            viewPage?.adapter = fragmentPager2Adapter
            viewPage?.currentItem = 0
            viewPage?.isUserInputEnabled = false
            //添加动画
//        pager_tour_task.setPageTransformer(ZoomOutPageTransformer())
            //切换tab页
            viewPage?.let {
                TabLayoutMediator(
                    tabLayout!!, it
                ) { tab, position ->
                    tab.text = titles[position]
                }.attach()
            }


//            setTab(tabLayout?.getTabAt(0)?.view?.tab)
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

        } catch (e: Exception) {
            Log.e("e0--", "${e.message}")

        }
    }

    override fun initData() {
    }


    private fun setTab(tab: TabLayout.Tab?) {
        if (tab == null) {
            return
        }
        val textView = TextView(mActivity)
        val selectedSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            7f,
            resources.displayMetrics
        )
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, selectedSize)
        textView.setTextColor(
            ContextCompat.getColor(
                mActivity,
                R.color.color_3388EF
            )
        )
        textView.typeface = Typeface.defaultFromStyle(Typeface.BOLD)//加粗
        textView.text = tab.text
        tab.customView = textView
    }

}