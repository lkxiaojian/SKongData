package com.zky.zky_questionnaire.adapter

import android.content.Context
import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ViewDataBinding
import com.zky.basics.api.common.entity.task.TaskQuestion
import com.zky.basics.common.adapter.BaseBindAdapter
import com.zky.zky_questionnaire.R
import com.zky.zky_questionnaire.databinding.QnCheboxItemBinding
import com.zky.zky_questionnaire.databinding.QnEdittextItemBinding
import com.zky.zky_questionnaire.databinding.QnRadioItemBinding
import com.zky.zky_questionnaire.databinding.QnSelectItemBinding
import com.zky.zky_questionnaire.inter.itemChangeListener

/**
 *create_time : 21-3-17 下午1:50
 *author: lk
 *description： QnListAdapter
 */
class QnListAdapter(
    context: Context,
    items: ObservableArrayList<TaskQuestion>?,
    _itemChang: itemChangeListener
) :
    BaseBindAdapter<TaskQuestion, ViewDataBinding>(context, items) {
    private var itemChang = _itemChang
    override fun onBindItem(binding: ViewDataBinding?, item: TaskQuestion, position: Int) {

        when (binding) {
            is QnRadioItemBinding -> binding.data = item
            is QnCheboxItemBinding -> binding.data = item
            is QnEdittextItemBinding -> binding.data = item
            is QnSelectItemBinding -> {
                binding.data = item
                binding.atvSelectXl.setOnClickListener {
                    mItemClickListener?.onItemClick(item, position)
                }
            }
        }
    }

    override fun getLayoutItemId(viewType: Int): Int {

        val qType = items?.get(viewType)?.q_type
        var layout = R.layout.qn_radio_item
        when (qType) {
            "radio" -> layout = R.layout.qn_radio_item
            "checkbox" -> layout = R.layout.qn_chebox_item
            "blank" -> layout = R.layout.qn_edittext_item
            "select" -> layout = R.layout.qn_select_item
        }
        return layout
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


}