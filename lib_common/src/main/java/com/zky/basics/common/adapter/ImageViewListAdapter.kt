package com.zky.basics.common.adapter

import android.content.Context

import androidx.databinding.ObservableArrayList
import com.zky.basics.api.room.bean.MediaBean
import com.zky.basics.common.R

import com.zky.basics.common.databinding.ImageListItemBinding




/**
 *create_time : 21-3-3 下午2:14
 *author: lk
 *description： TaskChainListAdapter
 */
class ImageViewListAdapter(context: Context,  items: ObservableArrayList<MediaBean>?) :
    BaseBindAdapter<MediaBean, ImageListItemBinding>(context, items) {
    override fun getLayoutItemId(viewType: Int) = R.layout.image_list_item

    override fun onBindItem(binding: ImageListItemBinding?, item: MediaBean, position: Int) {
        binding?.data = item
        binding?.aivShow?.setOnClickListener {
            mItemClickListener?.onItemClick(item, position)
        }
    }



    override fun getItemViewType(position: Int): Int {
        return position
    }


}