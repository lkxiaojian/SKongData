package com.zky.basics.main.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import androidx.databinding.ObservableArrayList
import com.zky.basics.common.adapter.BaseBindAdapter
import com.zky.basics.main.R
import com.zky.basics.main.databinding.MainListBinding


/**
 *create_time : 21-3-3 下午2:14
 *author: lk
 *description： MainListAdapter
 */
class MainListAdapter(context: Context, items: ObservableArrayList<String>?) :
    BaseBindAdapter<String, MainListBinding>(context, items) {
    override fun getLayoutItemId(viewType: Int) = R.layout.main_list

    @SuppressLint("SetTextI18n")
    override fun onBindItem(binding: MainListBinding?, item: String, position: Int) {
        binding?.data = item
        val string: Spannable = SpannableString("任务名称: 都是阿卡丽肯定骄傲了空间里的静安寺到了就算了")
        string.setSpan(StyleSpan(Typeface.BOLD), 0, 5, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        binding?.atvTaskName?.text = string
        binding?.atvTaskNum?.text="任务${position+1}"

        binding?.cvClick?.setOnClickListener {
            mItemClickListener?.onItemClick(item, position)
        }
    }


}