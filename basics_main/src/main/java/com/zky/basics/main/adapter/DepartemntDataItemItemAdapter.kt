package com.zky.basics.main.adapter

import android.content.Context
import android.content.Intent
import androidx.databinding.ObservableArrayList
import androidx.databinding.ViewDataBinding
import com.zky.basics.api.common.entity.task.KeyAndValue
import com.zky.basics.common.adapter.BaseBindAdapter
import com.zky.basics.main.R
import com.zky.basics.main.activity.task.DepartemntDataActivity
import com.zky.basics.main.databinding.QDeparDataItemItemItemListBinding
import com.zky.basics.main.databinding.QDeparDataItemItemListBinding
import com.zky.basics.main.databinding.QDeparDataItemListBinding
import com.zky.basics.main.databinding.QDeparDataMeessageBinding
import java.math.BigDecimal


/**
 *create_time : 21-3-3 下午2:14
 *author: lk
 *description： DepartemntDataItemAdapter
 */
class DepartemntDataItemItemAdapter(context: Context, items: ObservableArrayList<KeyAndValue>?) :
    BaseBindAdapter<KeyAndValue, ViewDataBinding>(context, items) {
    private val mContext=context
    private var mDatas=items
    override fun getLayoutItemId(viewType: Int): Int {
        return if (viewType == 4) {
            R.layout.q_depar_data_meessage
        } else {
            R.layout.q_depar_data_item_item_item_list
        }
    }

    override fun onBindItem(binding: ViewDataBinding?, item: KeyAndValue, position: Int) {

        when(binding){
            is QDeparDataItemItemItemListBinding->{
                binding.data = item
            }
            is QDeparDataMeessageBinding->{
                binding.clMessage.setOnClickListener {
                    val intent = Intent(mContext, DepartemntDataActivity::class.java)
                    intent.putExtra("data",mDatas)
                    mContext.startActivity(intent)
                }
            }

        }




    }

    override fun getItemCount(): Int {
        var count = 0
        items?.let {
            count = if (it.size >= 4) {
                5
            } else {
                it.size
            }
        }
        return count
    }


    override fun getItemViewType(position: Int): Int {
        return position
    }


}