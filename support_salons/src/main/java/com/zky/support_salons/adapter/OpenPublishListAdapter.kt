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
import com.zky.support_salons.databinding.OpenPublishListItemBinding


/**
 *create_time : 21-3-3 下午2:14
 *author: lk
 *description： OpenPublishListAdapter
 */
class OpenPublishListAdapter(context: Context, items: ObservableArrayList<String>?) :
    BaseBindAdapter<String, OpenPublishListItemBinding>(context, items),
    BaseBindAdapter.OnItemClickListener<Any> {

    override fun getLayoutItemId(viewType: Int) = R.layout.open_publish_list_item

    override fun onBindItem(binding: OpenPublishListItemBinding?, item: String, position: Int) {
        binding?.data = item
//        binding?.show=queryType=="send"
        binding?.cvClick?.setOnClickListener {
            mItemClickListener?.onItemClick(item, position)
        }
//
//        val observableArrayList = ObservableArrayList<ChineMedia>()
//        item.fileList?.let { observableArrayList.addAll(it) }
//        val adapter = MediaImageListAdapter(context, observableArrayList)
//        adapter.setItemClickListener(this)
//        binding?.rvImage?.layoutManager = GridLayoutManager(context, 3)
//        binding?.rvImage?.adapter = adapter
//        observableArrayList.addOnListChangedCallback(
//            ObservableListUtil.getListChangedCallback(
//                adapter
//            )
//        )
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
                    it.userName,
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