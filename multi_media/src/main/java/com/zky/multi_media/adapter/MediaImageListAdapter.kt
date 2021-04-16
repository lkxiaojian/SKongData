package com.zky.multi_media.adapter

import android.content.Context
import android.view.View
import androidx.databinding.ObservableArrayList
import com.bumptech.glide.Glide
import com.zky.basics.api.room.bean.MediaBean
import com.zky.basics.common.adapter.BaseBindAdapter
import com.zky.multi_media.R
import com.zky.multi_media.databinding.MediaItemBinding
import com.zky.multi_media.mvvm.viewmodle.MediaImageListViewModle.Companion.imageSelectType

/**
 *create_time : 21-3-5 上午9:34
 *author: lk
 *description： MediaListAdapter
 */
class MediaImageListAdapter(
    context: Context,
    items: ObservableArrayList<MediaBean>?,
    type: String
) :
    BaseBindAdapter<MediaBean, MediaItemBinding>(context, items) {
    private val mType = type
    override fun getLayoutItemId(viewType: Int) = R.layout.media_item

    override fun onBindItem(binding: MediaItemBinding?, item: MediaBean, position: Int) {
        binding?.data = item
        binding?.clClick?.setOnClickListener {
            var type = mType
            imageSelectType=type
            if (position == items?.size!! -1) {
                type = "add"
            }

            mItemClickListener?.onItemClick(type, position)
        }
        binding?.clClick?.setOnLongClickListener {
            var type = mType
            imageSelectType=type
            if (position == items?.size!! -1) {
                type = "del"
            }

            mOnItemLongClickListener?.onItemLongClick(type, position)!!
        }

    }
}