package com.zky.basics.common.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.zky.basics.common.R

/**
 *create_time : 21-3-19 下午1:57
 *author: lk
 *description： MessageAdapter
 */
class MessageAdapter(list: List<String>) : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {
    private val data = list
    private var itemOnClick: ItemOnClick? = null

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val atv: AppCompatTextView =
            view.findViewById<AppCompatTextView>(R.id.atv)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate =
            LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false)
        return ViewHolder(inflate)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val s = data[position]
        holder.atv.text = s
        holder.atv.setOnClickListener {
            itemOnClick?.onClick(s, position)
        }
    }

    override fun getItemCount() = data.size

    fun setItemOnClick(c: ItemOnClick) {
        itemOnClick = c
    }

    interface ItemOnClick {
        fun onClick(value: String, position: Int)
    }
}