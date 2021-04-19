package com.zky.multi_media.adapter

import android.content.Context
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.GridLayoutManager
import com.zky.basics.api.file.FileData
import com.zky.basics.api.room.bean.MediaBean
import com.zky.basics.common.adapter.BaseBindAdapter
import com.zky.basics.common.util.ObservableListUtil
import com.zky.basics.common.util.reflec.instanceOf
import com.zky.multi_media.R
import com.zky.multi_media.databinding.RecycleItemTypeBinding
import com.zky.multi_media.fragment.MediaVideoFragment

/**
 *create_time : 21-3-5 上午9:34
 *author: lk
 *description： MediaListAdapter
 */
class MediaImageTypeListAdapter(
    context: Context,
    items: ObservableArrayList<FileData>?,
    listener: MediaImageTypeListAdapterListener,
    type: String
) :
    BaseBindAdapter<FileData, RecycleItemTypeBinding>(context, items),
    BaseBindAdapter.OnItemClickListener<Any>, BaseBindAdapter.OnItemLongClickListener<Any> {
    private val mListener = listener
    private val mType = type
    override fun getLayoutItemId(viewType: Int) = R.layout.recycle_item_type
    override fun onBindItem(binding: RecycleItemTypeBinding?, item: FileData, position: Int) {
        binding?.data = item
        val observableArrayList = ObservableArrayList<MediaBean>()
        item.files?.let { observableArrayList.addAll(it) }
        observableArrayList.add(instanceOf<MediaBean>())
        val adapter = when (mType) {
            "image" -> {
                MediaImageListAdapter(context, observableArrayList, item.type)
            }
            "video" -> {
                MediaVideoListAdapter(context, observableArrayList, item.type)
            }
            else -> {
                MediaVoiceListAdapter(context, observableArrayList,item.type)
            }
        }

        adapter.setItemClickListener(this)
        adapter.setOnItemLongClickListener(this)
        binding?.rvType?.layoutManager = GridLayoutManager(context, 3)
        binding?.rvType?.adapter = adapter
        observableArrayList.addOnListChangedCallback(
            ObservableListUtil.getListChangedCallback(
                adapter
            )
        )
    }


    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onItemClick(e: Any, position: Int) {
        mListener.onItemClick(e, position)
    }

    override fun onItemLongClick(e: Any, postion: Int): Boolean {
        return mListener.onItemLongClick(e, postion)
    }

    interface MediaImageTypeListAdapterListener {
        fun onItemClick(e: Any, position: Int)
        fun onItemLongClick(e: Any, position: Int): Boolean
    }

}