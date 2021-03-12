package com.zky.multi_media.adapter

import android.content.Context
import android.view.View
import androidx.databinding.ObservableArrayList
import com.bumptech.glide.Glide
import com.zky.basics.api.room.bean.MediaBean
import com.zky.basics.common.adapter.BaseBindAdapter
import com.zky.multi_media.R
import com.zky.multi_media.databinding.MediaItemBinding
import com.zky.multi_media.databinding.VoiceSearchItemBinding

/**
 *create_time : 21-3-5 上午9:34
 *author: lk
 *description： VoiceSearchListAdapter
 */
class VoiceSearchListAdapter(context: Context, items: ObservableArrayList<MediaBean>?) :
    BaseBindAdapter<MediaBean, VoiceSearchItemBinding>(context, items) {
    override fun getLayoutItemId(viewType: Int) = R.layout.voice_search_item

    override fun onBindItem(binding: VoiceSearchItemBinding?, item: MediaBean, position: Int) {
        binding?.data = item

        binding?.clClick?.setOnClickListener {
            mItemClickListener?.onItemClick(item, position)
        }
    }
}