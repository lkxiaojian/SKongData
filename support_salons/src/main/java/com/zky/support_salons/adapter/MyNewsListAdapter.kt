package com.zky.support_salons.adapter

import android.content.Context
import androidx.databinding.ObservableArrayList
import com.zky.basics.common.adapter.BaseBindAdapter
import com.zky.support_salons.R
import com.zky.support_salons.databinding.MyNewListItemBinding


/**
 *create_time : 21-3-3 下午2:14
 *author: lk
 *description： OpenPublishListAdapter
 */
class MyNewsListAdapter(context: Context, items: ObservableArrayList<String>?) :
    BaseBindAdapter<String, MyNewListItemBinding>(context, items) {

    override fun getLayoutItemId(viewType: Int) = R.layout.my_new_list_item

    override fun onBindItem(binding: MyNewListItemBinding?, item: String, position: Int) {
        binding?.data = item
        binding?.cvClick?.setOnClickListener {
            mItemClickListener?.onItemClick(item, position)
        }
    }


}