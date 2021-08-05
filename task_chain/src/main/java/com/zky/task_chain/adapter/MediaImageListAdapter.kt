package com.zky.task_chain.adapter

import android.content.Context

import androidx.databinding.ObservableArrayList
import com.zky.basics.api.common.entity.chine.ChineMedia

import com.zky.basics.api.room.bean.MediaBean
import com.zky.basics.common.adapter.BaseBindAdapter
import com.zky.task_chain.R
import com.zky.task_chain.databinding.MediaChineItemBinding


/**
 *create_time : 21-3-5 上午9:34
 *author: lk
 *description： MediaListAdapter
 */
class MediaImageListAdapter(
    context: Context,
    items: ObservableArrayList<ChineMedia>?
) :
    BaseBindAdapter<ChineMedia, MediaChineItemBinding>(context, items) {
    override fun getLayoutItemId(viewType: Int) = R.layout.media_chine_item

    override fun onBindItem(binding: MediaChineItemBinding?, item: ChineMedia, position: Int) {
        binding?.data = item
        binding?.clClick?.setOnClickListener {
            items?.let { it1 -> mItemClickListener?.onItemClick(it1, position) }
        }
    }
}