package com.zky.task_chain.adapter

import android.content.Context

import androidx.databinding.ObservableArrayList
import com.zky.basics.api.common.entity.task.TaskItem
import com.zky.basics.common.adapter.BaseBindAdapter

import com.zky.task_chain.R
import com.zky.task_chain.databinding.TaskChainListItemBinding


/**
 *create_time : 21-3-3 下午2:14
 *author: lk
 *description： TaskChainListAdapter
 */
class TaskChainListAdapter(context: Context, items: ObservableArrayList<String>?) :
    BaseBindAdapter<String, TaskChainListItemBinding>(context, items) {
    override fun getLayoutItemId(viewType: Int) = R.layout.task_chain_list_item

    override fun onBindItem(binding: TaskChainListItemBinding?, item: String, position: Int) {
        binding?.data = item
        binding?.cvClick?.setOnClickListener {
            mItemClickListener?.onItemClick(item, position)
        }
        binding?.cvClick?.setOnLongClickListener {
            mOnItemLongClickListener?.onItemLongClick(item, position)!!
        }
    }


}