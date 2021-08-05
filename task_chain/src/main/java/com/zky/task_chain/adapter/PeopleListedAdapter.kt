package com.zky.task_chain.adapter

import android.content.Context

import androidx.databinding.ObservableArrayList
import com.zky.basics.api.common.entity.chine.SelectPeople
import com.zky.basics.api.common.entity.task.TaskItem
import com.zky.basics.common.adapter.BaseBindAdapter

import com.zky.task_chain.R
import com.zky.task_chain.databinding.DealMessageListItemBinding
import com.zky.task_chain.databinding.PeopleListedItemBinding
import com.zky.task_chain.databinding.SelectPeopleListItemBinding
import com.zky.task_chain.databinding.TaskChainListItemBinding


/**
 *create_time : 21-3-3 下午2:14
 *author: lk
 *description： PeopleListedAdapter
 */
class PeopleListedAdapter(context: Context, items: ObservableArrayList<SelectPeople>?) :
    BaseBindAdapter<SelectPeople, PeopleListedItemBinding>(context, items) {
    override fun getLayoutItemId(viewType: Int) = R.layout.people_listed_item

    override fun onBindItem(binding: PeopleListedItemBinding?, item: SelectPeople, position: Int) {
        binding?.data = item
        binding?.aivDel?.setOnClickListener {
            mItemClickListener?.onItemClick(item, position)
        }
    }
}