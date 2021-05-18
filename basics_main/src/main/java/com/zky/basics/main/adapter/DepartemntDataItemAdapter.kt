package com.zky.basics.main.adapter

import android.content.Context
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.LinearLayoutManager
import com.zky.basics.common.adapter.BaseBindAdapter
import com.zky.basics.common.util.ObservableListUtil
import com.zky.basics.main.R
import com.zky.basics.main.databinding.QDeparDataItemItemListBinding
import com.zky.basics.main.databinding.QDeparDataItemListBinding



/**
 *create_time : 21-3-3 下午2:14
 *author: lk
 *description： DepartemntDataItemAdapter
 */
class DepartemntDataItemAdapter(context: Context, items: ObservableArrayList<String>?) :
    BaseBindAdapter<String, QDeparDataItemItemListBinding>(context, items) {
    override fun getLayoutItemId(viewType: Int) = R.layout.q_depar_data_item_item_list

    override fun onBindItem(binding: QDeparDataItemItemListBinding?, item: String, position: Int) {
        binding?.data = item


        val observableArrayList = ObservableArrayList<String>()
        observableArrayList.add("1")
        observableArrayList.add("2")
        observableArrayList.add("2")
        observableArrayList.add("2")
        observableArrayList.add("2")
        observableArrayList.add("2")
        observableArrayList.add("2")
        val adapter=DepartemntDataItemItemAdapter(context,observableArrayList)
        binding?.rv?.layoutManager = LinearLayoutManager(context)
        binding?.rv?.adapter = adapter
        observableArrayList.addOnListChangedCallback(
            ObservableListUtil.getListChangedCallback(
                adapter
            )
        )


//        binding?.cvClick?.setOnLongClickListener {
//            mOnItemLongClickListener?.onItemLongClick(item, position)!!
//        }
    }





}