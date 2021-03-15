package com.zky.basics.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.TextViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.zky.basics.main.R

/**
 *create_time : 21-3-15 下午3:36
 *author: lk
 *description： AddressDialogAdapter
 */
class AddressDialogAdapter(context: Context, data: ArrayList<Any>) :
    RecyclerView.Adapter<AddressDialogAdapter.ViewHolder>() {

    private val context = context
    private val list = data

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
        holder.atvAddress.text = "test"
    }

    override fun getItemCount() = if (list.isNullOrEmpty()) 0 else list.size


    fun setList(data: ArrayList<Any>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()

    }
}