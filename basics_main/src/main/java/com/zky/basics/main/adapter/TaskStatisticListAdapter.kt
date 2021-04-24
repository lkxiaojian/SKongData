package com.zky.basics.main.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import androidx.databinding.ObservableArrayList
import com.zky.basics.api.common.entity.task.TaskBean
import com.zky.basics.api.common.entity.task.TaskChartAdBean
import com.zky.basics.api.common.entity.task.TaskItem
import com.zky.basics.common.adapter.BaseBindAdapter
import com.zky.basics.common.view.Statistics.DragInerfaces
import com.zky.basics.main.R
import com.zky.basics.main.databinding.MainListBinding
import com.zky.basics.main.databinding.TaskListBinding
import com.zky.basics.main.databinding.TaskStatisticListBinding


/**
 *create_time : 21-3-3 下午2:14
 *author: lk
 *description： TaskStatisticListAdapter
 */
class TaskStatisticListAdapter(context: Context, items: ObservableArrayList<TaskChartAdBean>?) :
    BaseBindAdapter<TaskChartAdBean, TaskStatisticListBinding>(context, items) {
    override fun getLayoutItemId(viewType: Int) = R.layout.task_statistic_list

    override fun onBindItem(
        binding: TaskStatisticListBinding?,
        item: TaskChartAdBean,
        position: Int
    ) {
        binding?.data = item
        binding?.lbcv?.setDatas(item.mDatas, item.mDesciption, item.avgData, item.title, true)
        binding?.lbcv?.setDragInerfaces(object : DragInerfaces {
            override fun onEnd() {
                binding.lbcv.setScroBall(100000)
            }

            override fun onStart() {
            }

            override fun preScrollX(preScrollX: Int) {
                binding.lbcv.setScroBall(preScrollX)
            }

        })
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


}