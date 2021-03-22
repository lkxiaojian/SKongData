package com.zky.zky_questionnaire.adapter

import android.content.Context
import androidx.databinding.ObservableArrayList
import androidx.databinding.ViewDataBinding
import com.zky.basics.common.adapter.BaseBindAdapter
import com.zky.zky_questionnaire.R
import com.zky.zky_questionnaire.TestData
import com.zky.zky_questionnaire.databinding.QnCheboxItemBinding
import com.zky.zky_questionnaire.databinding.QnRadioItemBinding

/**
 *create_time : 21-3-17 下午1:50
 *author: lk
 *description： QnListAdapter
 */
class QnListAdapter(context: Context, items: ObservableArrayList<TestData>?) :
    BaseBindAdapter<TestData, ViewDataBinding>(context, items) {
    override fun onBindItem(binding: ViewDataBinding?, item: TestData, position: Int) {

        when (binding) {
            is QnRadioItemBinding -> binding.data = item
            is QnCheboxItemBinding -> binding.data = item
        }


//        binding?.cvClick?.setOnClickListener {
//            mItemClickListener?.onItemClick(item, position)
//        }
    }

    override fun getLayoutItemId(viewType: Int): Int {
        var layout = R.layout.qn_radio_item
        when (viewType) {
            0 -> layout = R.layout.qn_radio_item
            1 -> layout = R.layout.qn_chebox_item
        }

        return layout
    }

    override fun getItemViewType(position: Int): Int {
        var type = 0
        when (position) {
            0 -> type = 0
            1, 2 -> type = 1
        }
        return 0
    }


}