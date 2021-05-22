package com.zky.task_chain.adapter

import android.content.Context

import androidx.databinding.ObservableArrayList
import com.zky.basics.api.common.entity.task.TaskItem
import com.zky.basics.common.adapter.BaseBindAdapter

import com.zky.task_chain.R
import com.zky.task_chain.databinding.DealMessageListItemBinding
import com.zky.task_chain.databinding.SelectPeopleListItemBinding
import com.zky.task_chain.databinding.TaskChainListItemBinding


/**
 *create_time : 21-3-3 下午2:14
 *author: lk
 *description： TaskChainListAdapter
 */
class SelectPeopleListAdapter(context: Context, items: ObservableArrayList<String>?) :
    BaseBindAdapter<String, SelectPeopleListItemBinding>(context, items) {
    override fun getLayoutItemId(viewType: Int) = R.layout.select_people_list_item

    override fun onBindItem(binding: SelectPeopleListItemBinding?, item: String, position: Int) {
        binding?.data = item
        binding?.cvClick?.setOnClickListener {
            mItemClickListener?.onItemClick(item, position)
        }
//        binding?.cvClick?.setOnLongClickListener {
//            mOnItemLongClickListener?.onItemLongClick(item, position)!!
//        }
    }


}