package com.zky.basics.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.TextViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.zky.basics.api.splash.entity.AccountLevel
import com.zky.basics.main.R

/**
 *create_time : 21-3-15 下午3:36
 *author: lk
 *description： AddressDialogAdapter
 */
class AddressDialogAdapter(context: Context, data: ArrayList<AccountLevel>) :
    RecyclerView.Adapter<AddressDialogAdapter.ViewHolder>() {

    private val context = context
    private val list = data
    private var click: itemOnClik? = null

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val atvAddress: AppCompatTextView =
            view.findViewById<AppCompatTextView>(R.id.atv_address)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate =
            LayoutInflater.from(context).inflate(R.layout.address_dialog_item, parent, false)
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val accountLevel = list[position]
        if (accountLevel.value.isNullOrEmpty()) {
            holder.atvAddress.text = ""
            holder.atvAddress.hint=accountLevel.attr_tip

        } else {
            holder.atvAddress.text = accountLevel.value
        }

        holder.atvAddress.setOnClickListener {
            click?.itemClick(holder.atvAddress,accountLevel,position)
        }
    }

    override fun getItemCount() = if (list.isNullOrEmpty()) 0 else list.size
    fun setList(data: List<AccountLevel>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()

    }

    fun setClick(c:itemOnClik) {
        click=c
    }

    interface itemOnClik {
        fun itemClick(view:View,data: AccountLevel, positon: Int)
    }
}