package com.zky.task_chain.adapter

import android.content.Context
import androidx.databinding.Bindable

import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.zky.basics.api.common.entity.chine.ChineMedia
import com.zky.basics.api.common.entity.chine.TaskChineItemBean
import com.zky.basics.api.room.bean.MediaBean
import com.zky.basics.common.adapter.BaseBindAdapter
import com.zky.basics.common.util.ObservableListUtil
import com.zky.basics.common.util.reflec.instanceOf

import com.zky.task_chain.R
import com.zky.task_chain.databinding.TaskChainListItemBinding
import java.io.Serializable


/**
 *create_time : 21-3-3 下午2:14
 *author: lk
 *description： TaskChainListAdapter
 */
class TaskChainListAdapter(context: Context, items: ObservableArrayList<TaskChineItemBean>?) :
    BaseBindAdapter<TaskChineItemBean, TaskChainListItemBinding>(context, items),
    BaseBindAdapter.OnItemClickListener<Any> {
    override fun getLayoutItemId(viewType: Int) = R.layout.task_chain_list_item

    override fun onBindItem(
        binding: TaskChainListItemBinding?,
        item: TaskChineItemBean,
        position: Int
    ) {
        binding?.data = item
        binding?.cvClick?.setOnClickListener {
            mItemClickListener?.onItemClick(item, position)
        }
//        binding?.cvClick?.setOnLongClickListener {
//            mOnItemLongClickListener?.onItemLongClick(item, position)!!
//        }
        val observableArrayList = ObservableArrayList<ChineMedia>()
        item.fileList?.let { observableArrayList.addAll(it) }
        val adapter = MediaImageListAdapter(context, observableArrayList)
        adapter.setItemClickListener(this)
        binding?.rvImage?.layoutManager = GridLayoutManager(context, 3)
        binding?.rvImage?.adapter = adapter
        observableArrayList.addOnListChangedCallback(
            ObservableListUtil.getListChangedCallback(
                adapter
            )
        )
    }

    override fun onItemClick(e: Any, position: Int) {
        val projectPhoto = ObservableArrayList<MediaBean>()
        if (e is ObservableArrayList<*>) {
            val observableArrayList = e as ObservableArrayList<ChineMedia>
            observableArrayList.forEach { it ->
                val mediaBean = MediaBean(
                    it.code,
                    "image",
                    it.filePath,
                    it.fileName,
                    it.createDate,
                    true,
                    it.filePath,
                    "",
                    "",
                    "",
                    ""
                )

                projectPhoto.add(mediaBean)
            }
            ARouter.getInstance().build(ARouterPath.MEDIA_SHOW_IMAGE)
                .withInt("position", position)
                .withSerializable("images", projectPhoto )
                .navigation()
        }


    }


}