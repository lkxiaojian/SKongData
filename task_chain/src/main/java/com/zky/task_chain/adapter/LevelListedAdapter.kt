package com.zky.task_chain.adapter

import android.content.Context

import androidx.databinding.ObservableArrayList
import androidx.databinding.ViewDataBinding
import com.zky.basics.api.common.entity.chine.SelectPeople
import com.zky.basics.api.common.entity.task.TaskItem
import com.zky.basics.common.adapter.BaseBindAdapter

import com.zky.task_chain.R
import com.zky.task_chain.bean.SelectPeopleLevelBean
import com.zky.task_chain.databinding.*


/**
 *create_time : 21-3-3 下午2:14
 *author: lk
 *description： LevelListedAdapter
 */
class LevelListedAdapter(context: Context, items: ObservableArrayList<SelectPeopleLevelBean>?) :
    BaseBindAdapter<SelectPeopleLevelBean, ViewDataBinding>(context, items) {
    override fun getLayoutItemId(viewType: Int): Int {
        val qType = items?.get(viewType)?.type
        var layout = R.layout.level_item
        when (qType) {
            "level" -> layout = R.layout.level_item
            "city" -> layout = R.layout.city_item
        }
        return layout
    }

    override fun onBindItem(binding: ViewDataBinding?, item: SelectPeopleLevelBean, position: Int) {

        when (binding) {
            is LevelItemBinding -> {
                binding.data = item
                binding.cvClick.setOnClickListener {
                    if (item.hintText != "个人") {
                        mItemClickListener?.onItemClick(item, position)
                    }
                }
            }
            is CityItemBinding -> {
                binding.data = item
                binding.cvClick.setOnClickListener {
                    mItemClickListener?.onItemClick(item, position)
                }
            }
        }

    }
}