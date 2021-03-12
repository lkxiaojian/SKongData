package com.zky.multi_media.adapter

import android.content.Context
import android.view.View
import androidx.databinding.ObservableArrayList
import com.bumptech.glide.Glide
import com.zky.basics.api.room.bean.MediaBean
import com.zky.basics.common.adapter.BaseBindAdapter
import com.zky.multi_media.R
import com.zky.multi_media.databinding.MediaItemBinding

/**
 *create_time : 21-3-5 上午9:34
 *author: lk
 *description： MediaListAdapter
 */
class MediaImageListAdapter(context: Context, items: ObservableArrayList<MediaBean>?, type: Int) :
    BaseBindAdapter<MediaBean, MediaItemBinding>(context, items) {
    private val type = type
    override fun getLayoutItemId(viewType: Int) = R.layout.media_item

    override fun onBindItem(binding: MediaItemBinding?, item: MediaBean, position: Int) {
        if (items == null) {
            return
        }
        binding?.data = item
        if (type == 0 || type == 1) {
            binding?.cl?.visibility = View.GONE
            binding?.aivMediaV?.visibility = View.VISIBLE
            binding?.aivMediaV?.let {
                Glide.with(context).load(item.videoImagePath)
                    .into(it)
            }
        } else {
            binding?.cl?.visibility = View.VISIBLE
            binding?.aivMediaV?.visibility = View.GONE
            binding?.aivMedia?.let {
                Glide.with(context).load(item.videoImagePath)
                    .into(it)
            }
        }


        if (position == items!!.size - 1) {
            binding?.aivMedia?.let {
                Glide.with(context).load(R.mipmap.add_media).into(it)
            }
            binding?.aivMediaV?.let {
                Glide.with(context).load(R.mipmap.add_media).into(it)
            }
            binding?.atvHcState?.visibility = View.GONE
        }else{
            binding?.atvHcState?.visibility = View.VISIBLE
        }

        binding?.clClick?.setOnClickListener {
            mItemClickListener?.onItemClick(item, position)
        }

    }
}