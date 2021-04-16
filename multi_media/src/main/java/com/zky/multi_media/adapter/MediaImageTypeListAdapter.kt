package com.zky.multi_media.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.zky.basics.api.file.FileData
import com.zky.basics.api.file.FileDataS
import com.zky.basics.api.room.bean.MediaBean
import com.zky.basics.common.adapter.BaseBindAdapter
import com.zky.basics.common.util.ObservableListUtil
import com.zky.basics.common.util.reflec.instanceOf
import com.zky.multi_media.R
import com.zky.multi_media.databinding.RecycleItemTypeBinding

/**
 *create_time : 21-3-5 上午9:34
 *author: lk
 *description： MediaListAdapter
 */
class MediaImageTypeListAdapter(context: Context, items: ObservableArrayList<FileData>?) :
    BaseBindAdapter<FileData, RecycleItemTypeBinding>(context, items) {
    override fun getLayoutItemId(viewType: Int) = R.layout.recycle_item_type
    override fun onBindItem(binding: RecycleItemTypeBinding?, item: FileData, position: Int) {
        binding?.data = item
        val observableArrayList = ObservableArrayList<MediaBean>()
        item.files?.let { observableArrayList.addAll(it) }
        observableArrayList.add(instanceOf<MediaBean>())
        observableArrayList.add(instanceOf<MediaBean>())
        observableArrayList.add(instanceOf<MediaBean>())
        observableArrayList.add(instanceOf<MediaBean>())
        observableArrayList.add(instanceOf<MediaBean>())
        val adapter = MediaImageListAdapter(context, observableArrayList)
        val glid = GridLayoutManager(context,3)
        binding?.rvType?.layoutManager = glid
        binding?.rvType?.adapter = adapter
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


}