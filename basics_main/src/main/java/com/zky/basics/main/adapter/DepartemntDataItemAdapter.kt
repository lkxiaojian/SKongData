package com.zky.basics.main.adapter

import android.content.Context
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.LinearLayoutManager
import com.zky.basics.api.common.entity.task.KeyAndValue
import com.zky.basics.common.adapter.BaseBindAdapter
import com.zky.basics.common.util.ObservableListUtil
import com.zky.basics.main.R
import com.zky.basics.main.databinding.QDeparDataItemItemListBinding


/**
 *create_time : 21-3-3 下午2:14
 *author: lk
 *description： DepartemntDataItemAdapter
 */
class DepartemntDataItemAdapter(context: Context, items: ObservableArrayList<ArrayList<KeyAndValue>>?) :
    BaseBindAdapter<ArrayList<KeyAndValue>, QDeparDataItemItemListBinding>(context, items) {
    override fun getLayoutItemId(viewType: Int) = R.layout.q_depar_data_item_item_list

    override fun onBindItem(
        binding: QDeparDataItemItemListBinding?,
        item: ArrayList<KeyAndValue>,
        position: Int
    ) {
        val observableArrayList = ObservableArrayList<KeyAndValue>()
        observableArrayList.addAll(item)
        val adapter = DepartemntDataItemItemAdapter(context, observableArrayList)
        binding?.rv?.layoutManager = LinearLayoutManager(context)
        binding?.rv?.adapter = adapter
        observableArrayList.addOnListChangedCallback(
            ObservableListUtil.getListChangedCallback(
                adapter
            )
        )
    }


}