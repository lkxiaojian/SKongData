package com.zky.basics.main.adapter

import android.content.Context
import androidx.databinding.ObservableArrayList
import com.zky.basics.api.common.entity.task.KeyAndValue
import com.zky.basics.common.adapter.BaseBindAdapter
import com.zky.basics.main.R
import com.zky.basics.main.databinding.DepartDataActivtiyItemBinding
import com.zky.basics.main.databinding.QDeparDataItemItemItemListBinding

/**
 *create_time : 21-3-3 下午2:14
 *author: lk
 *description： DepartemntDataItemAdapter
 */
class DepartemntDataActivityAdapter(context: Context, items: ObservableArrayList<KeyAndValue>?) :
    BaseBindAdapter<KeyAndValue, DepartDataActivtiyItemBinding>(context, items) {
    override fun getLayoutItemId(viewType: Int)=R.layout.depart_data_activtiy_item
    override fun onBindItem(binding: DepartDataActivtiyItemBinding?, item: KeyAndValue, position: Int) {
        binding?.data = item
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


}