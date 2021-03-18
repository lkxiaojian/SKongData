package com.zky.basics.main.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import androidx.databinding.ObservableArrayList
import com.zky.basics.api.common.entity.task.TaskBean
import com.zky.basics.api.common.entity.task.TaskItem
import com.zky.basics.common.adapter.BaseBindAdapter
import com.zky.basics.main.R
import com.zky.basics.main.databinding.MainListBinding
import com.zky.basics.main.databinding.TaskListBinding


/**
 *create_time : 21-3-3 下午2:14
 *author: lk
 *description： MainListAdapter
 */
class TaskListAdapter(context: Context, items: ObservableArrayList<TaskItem>?) :
    BaseBindAdapter<TaskItem, TaskListBinding>(context, items) {
    override fun getLayoutItemId(viewType: Int) = R.layout.task_list

    override fun onBindItem(binding: TaskListBinding?, item: TaskItem, position: Int) {
        binding?.data = item
        binding?.cvClick?.setOnClickListener {
            mItemClickListener?.onItemClick(item, position)
        }
    }


}