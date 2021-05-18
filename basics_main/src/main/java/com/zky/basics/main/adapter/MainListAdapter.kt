package com.zky.basics.main.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import androidx.databinding.ObservableArrayList
import com.zky.basics.api.common.entity.task.TaskBean
import com.zky.basics.common.adapter.BaseBindAdapter
import com.zky.basics.main.R
import com.zky.basics.main.databinding.MainListBinding
import com.zky.basics.main.fragment.CollectFragment


/**
 *create_time : 21-3-3 下午2:14
 *author: lk
 *description： MainListAdapter
 */
class MainListAdapter(context: Context, items: ObservableArrayList<TaskBean>?) :
    BaseBindAdapter<TaskBean, MainListBinding>(context, items) {
    override fun getLayoutItemId(viewType: Int) = R.layout.main_list

    @SuppressLint("SetTextI18n")
    override fun onBindItem(binding: MainListBinding?, item: TaskBean, position: Int) {
        binding?.data = item
        val string: Spannable = SpannableString("行动名称: ${item.taskName}")
        string.setSpan(StyleSpan(Typeface.BOLD), 0, 5, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        binding?.atvTaskName?.text = string
        binding?.atvTaskNum?.text = "行动${position + 1}"
        binding?.atvTime?.text = "时间：${item.startDate} - ${item.endDate}"

        binding?.cvClick?.setOnClickListener {
            mItemClickListener?.onItemClick(item, position)
        }
    }


}