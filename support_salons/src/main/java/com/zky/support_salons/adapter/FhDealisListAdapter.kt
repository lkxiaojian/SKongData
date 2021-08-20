package com.zky.support_salons.adapter

import android.content.Context

import androidx.databinding.ObservableArrayList
import com.zky.basics.common.adapter.BaseBindAdapter

import com.zky.support_salons.R
import com.zky.support_salons.databinding.FbDealisListItemBinding



/**
 *create_time : 21-3-3 下午2:14
 *author: lk
 *description： ScalonsDealisListAdapter
 */
class FhDealisListAdapter(context: Context, items: ObservableArrayList<String>?) :
    BaseBindAdapter<String, FbDealisListItemBinding>(context, items) {

    override fun getLayoutItemId(viewType: Int) = R.layout.fb_dealis_list_item

    override fun onBindItem(binding: FbDealisListItemBinding?, item: String, position: Int) {
        binding?.data = item


    }


}