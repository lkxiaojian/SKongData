package com.zky.basics.main.fragment

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zky.basics.common.mvvm.viewmodel.BaseViewModel
import com.zky.basics.main.R
import com.zky.basics.main.adapter.AddressDialogAdapter
import com.zky.basics.main.mvvm.model.MainModel
import com.zky.basics.main.mvvm.viewmodel.TaskViewModel
import kotlinx.android.synthetic.main.fragment_select_address_dialog.*

/**
 *create_time : 21-3-15 下午2:03
 *author: lk
 *description： CustomDialogFragment
 */
class CustomDialogFragment : DialogFragment() {
    private var adapter: AddressDialogAdapter? = null
    private var mainModel: MainModel? = null
    private var baseViewModel: BaseViewModel<MainModel>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_select_address_dialog, container, false)
        val rv = view.findViewById<RecyclerView>(R.id.rv_dialog)
        rv.layoutManager = LinearLayoutManager(activity)
        adapter = context?.let { AddressDialogAdapter(it, arrayListOf<Any>()) }
        rv.adapter = adapter
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        try {
            mainModel = MainModel(activity!!.application)
            baseViewModel = BaseViewModel<MainModel>(activity!!.application, mainModel!!)
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }


}