package com.zky.basics.main.adapter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.zky.basics.api.splash.entity.AccountLevel
import com.zky.basics.main.R


/**
 *create_time : 21-3-5 下午4:04
 *author: lk
 *description： LevelAdapter
 */
class LevelAdapter(list: ArrayList<AccountLevel>, context: Context, click: ItemClick) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list = list
    private val context = context
    private val click = click

    inner class ViewHolderT(view: View) : RecyclerView.ViewHolder(view) {
        var register_account_level: AppCompatTextView =
            view.findViewById<AppCompatTextView>(R.id.register_account_level)

    }

    inner class ViewHolderE(view: View) : RecyclerView.ViewHolder(view) {
        var register_id =
            view.findViewById<AppCompatEditText>(R.id.register_id)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view: View
        return if (viewType == 0) {
            view =
                LayoutInflater.from(parent.context).inflate(R.layout.level_item_text, parent, false)
            ViewHolderT(view)
        } else {
            view =
                LayoutInflater.from(parent.context).inflate(R.layout.level_item_edit, parent, false)
            ViewHolderE(view)

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val bean = list[position]
        if (holder is ViewHolderT) {
//            android:textColor="@{viewModel.data.writeProvince?@color/color_4a4a4a:@color/color_b0b0b0}"

            if (bean.value.isNullOrEmpty()) {
                holder.register_account_level.text = bean.attr_tip
                holder.register_account_level.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.color_b0b0b0
                    )
                )
                holder.register_account_level.setOnClickListener {
                    click.itemClick(bean, position)
                }
            } else {

                holder.register_account_level.text = bean.value
                holder.register_account_level.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.color_4a4a4a
                    )
                )
            }
        } else if (holder is ViewHolderE) {
            holder.register_id.hint = bean.attr_tip
            holder.register_id?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    list[position].value = s?.toString()
                }

            })

        }


    }

    override fun getItemViewType(position: Int): Int {
        val attrTip = list[position].attr_tip
        return if (attrTip == null || attrTip.contains("身份证")) 1 else 0
    }

    override fun getItemCount() = list.size

    fun setList(listN: ArrayList<AccountLevel>) {

        list=listN
        notifyDataSetChanged()
    }

    fun getList(): ArrayList<AccountLevel> {
        return list
    }


    interface ItemClick {
        fun itemClick(bean: AccountLevel, position: Int)
    }


}