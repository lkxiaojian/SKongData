package com.zky.support_salons.adapter

import android.content.Context

import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.zky.basics.api.common.entity.chine.ChineMedia
import com.zky.basics.api.common.entity.chine.TaskChineItemBean
import com.zky.basics.api.room.bean.MediaBean
import com.zky.basics.common.adapter.BaseBindAdapter
import com.zky.basics.common.adapter.MediaImageListAdapter
import com.zky.basics.common.util.ObservableListUtil
import com.zky.support_salons.R
import com.zky.support_salons.databinding.MyRemarkListItemBinding
import com.zky.support_salons.databinding.OpenPublishListItemBinding


/**
 *create_time : 21-3-3 下午2:14
 *author: lk
 *description： OpenPublishListAdapter
 */
class MyRemarkListAdapter(context: Context, items: ObservableArrayList<String>?) :
    BaseBindAdapter<String, MyRemarkListItemBinding>(context, items) {

    override fun getLayoutItemId(viewType: Int) = R.layout.my_remark_list_item

    override fun onBindItem(binding: MyRemarkListItemBinding?, item: String, position: Int) {
        binding?.data = item
    }


}