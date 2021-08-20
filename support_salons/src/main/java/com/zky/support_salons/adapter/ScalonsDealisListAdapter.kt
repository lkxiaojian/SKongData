package com.zky.support_salons.adapter

import android.content.Context

import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
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
import com.zky.support_salons.databinding.ScalonDealisListItemBinding


/**
 *create_time : 21-3-3 下午2:14
 *author: lk
 *description： ScalonsDealisListAdapter
 */
class ScalonsDealisListAdapter(context: Context, items: ObservableArrayList<String>?) :
    BaseBindAdapter<String, ScalonDealisListItemBinding>(context, items) {

    override fun getLayoutItemId(viewType: Int) = R.layout.scalon_dealis_list_item

    override fun onBindItem(binding: ScalonDealisListItemBinding?, item: String, position: Int) {
        binding?.data = item

        val observableArrayList = ObservableArrayList<String>()
        observableArrayList.add("")
        observableArrayList.add("")
        val adapter = FhDealisListAdapter(context, observableArrayList)
        binding?.rv?.layoutManager = LinearLayoutManager(context)
        binding?.rv?.adapter = adapter
        observableArrayList.addOnListChangedCallback(
            ObservableListUtil.getListChangedCallback(
                adapter
            )
        )
    }


}