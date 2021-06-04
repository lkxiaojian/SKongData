package com.zky.basics.main.adapter

import android.content.Context
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.LinearLayoutManager
import com.zky.basics.api.common.entity.task.DepartmentDataList
import com.zky.basics.api.common.entity.task.KeyAndValue
import com.zky.basics.common.adapter.BaseBindAdapter
import com.zky.basics.common.util.ObservableListUtil
import com.zky.basics.main.R
import com.zky.basics.main.databinding.QDeparDataItemListBinding


/**
 *create_time : 21-3-3 下午2:14
 *author: lk
 *description： MainListAdapter
 */
class DepartemntDataAdapter(context: Context, items: ObservableArrayList<DepartmentDataList>?) :
    BaseBindAdapter<DepartmentDataList, QDeparDataItemListBinding>(context, items) {
    override fun getLayoutItemId(viewType: Int) = R.layout.q_depar_data_item_list

    override fun onBindItem(
        binding: QDeparDataItemListBinding?,
        item: DepartmentDataList,
        position: Int
    ) {
        binding?.data = item
        binding?.cvClick?.setOnClickListener {
            mItemClickListener?.onItemClick(item, position)
        }
        binding?.cvClick2?.setOnClickListener {
            mItemClickListener?.onItemClick(item, position)
        }
        binding?.atvTime?.setOnClickListener {
            mItemClickListener?.onItemClick("", position)
        }


        val observableArrayList = ObservableArrayList<ArrayList<KeyAndValue>>()
        val adapter = DepartemntDataItemAdapter(context, observableArrayList)
        binding?.rv?.layoutManager = LinearLayoutManager(context)
        binding?.rv?.adapter = adapter

        observableArrayList.addOnListChangedCallback(
            ObservableListUtil.getListChangedCallback(
                adapter
            )
        )
        if(item.valueList==null){
            observableArrayList.clear()
        }else{
            observableArrayList.addAll(item.valueList)
        }

    }
}