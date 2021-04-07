package com.zky.basics.main.fragment

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.PopupWindow
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bigkoo.pickerview.view.OptionsPickerView
import com.zky.basics.api.splash.entity.AccountLevel
import com.zky.basics.api.splash.entity.RegionOrSchoolBean
import com.zky.basics.common.adapter.MessageAdapter
import com.zky.basics.common.mvvm.viewmodel.BaseViewModel
import com.zky.basics.common.util.spread.showToast
import com.zky.basics.common.util.view.showListPopupWindow
import com.zky.basics.common.util.view.showRvListPopupWindow
import com.zky.basics.main.R
import com.zky.basics.main.adapter.AddressDialogAdapter
import com.zky.basics.main.mvvm.model.MainModel
import kotlinx.android.synthetic.main.fragment_select_address_dialog.*
import kotlinx.coroutines.launch
import java.lang.Exception


/**
 *create_time : 21-3-15 下午2:03
 *author: lk
 *description： CustomDialogFragment
 */
class CustomDialogFragment : DialogFragment(), AddressDialogAdapter.itemOnClik {
    private var adapter: AddressDialogAdapter? = null
    private var mainModel: MainModel? = null
    private var baseViewModel: BaseViewModel<MainModel>? = null
    private var addressSelect: AddressSelect? = null
    private var levelListT: List<AccountLevel>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_select_address_dialog, container, false)
        val rv = view?.findViewById<RecyclerView>(R.id.rv_dialog)
        rv?.layoutManager = LinearLayoutManager(activity)
        adapter = context?.let { AddressDialogAdapter(it, arrayListOf<AccountLevel>()) }
        adapter?.setClick(this)
        rv?.adapter = adapter
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        try {
            initD()
            mainModel = MainModel(activity!!.application)
            baseViewModel = BaseViewModel<MainModel>(activity!!.application, mainModel!!)
            baseViewModel?.launchUI({
                levelListT = mainModel?.getAddrLevel()
                levelListT?.let {
                    adapter?.setList(it)
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun initD() {
        iv_close?.setOnClickListener {
            dismiss()
        }
        abt_sure?.setOnClickListener {
            levelListT?.forEach {
                if (it.valueCode.isNullOrEmpty()) {
                    it.attr_tip?.showToast()
                    return@setOnClickListener
                }
            }
            addressSelect?.getSelectAddress(levelListT!!)
            dismiss()
        }
    }

    private val levelList: ArrayList<String> = arrayListOf()
    private var levelListNext: ArrayList<RegionOrSchoolBean>? = arrayListOf()
    private var flag = true
    private var currentIndex: Int = 1
    private var  showWindow : PopupWindow?=null

        private fun setAd(view: View, index: Int, attr_idx: Int) {

        try {
            if (showWindow?.isShowing==true) {
                return
            }
            baseViewModel?.launchUI({
                flag = false
                if (levelListNext.isNullOrEmpty() || attr_idx == 1 || attr_idx < currentIndex) {
                    levelListNext = if(attr_idx==1){
                        mainModel?.getAddr(attr_idx,"") as ArrayList<RegionOrSchoolBean>?
                    }else{
                        mainModel?.getAddr(attr_idx, levelListT!![attr_idx - 2].valueCode) as ArrayList<RegionOrSchoolBean>?
                    }
                }
                levelList.clear()
                levelListNext?.let { it ->
                    levelList.add("请选择")
                    it.forEach {
                        levelList.add(it.name!!)
                    }

                    showWindow= showRvListPopupWindow(view, levelList, object : MessageAdapter.ItemOnClick {
                            override fun onClick(value: String, position: Int) {
                                levelListT?.let {
                                    for ((tindex, _) in it.withIndex()) {
                                        if (tindex >= index) {
                                            levelListT!![tindex].valueCode = ""
                                            levelListT!![tindex].value = ""
                                        }
                                    }
                                    if (position != 0) {
                                        levelListT!![index].valueCode = levelListNext!![position - 1].code
                                        levelListT!![index].value = value
                                    }
                                    adapter?.setList(levelListT!!)
                                    currentIndex=attr_idx+1
                                    baseViewModel?.launchUI({
                                        if (currentIndex <= levelListT!!.size&& ! levelListNext!![position - 1].code.isNullOrEmpty()) {
                                            if(currentIndex>levelListT!!.size){
                                                currentIndex=levelListT!!.size
                                            }
                                            levelListNext =
                                                mainModel?.getAddr(
                                                    currentIndex,
                                                    levelListNext!![position - 1].code
                                                ) as ArrayList<RegionOrSchoolBean>?
                                        }

                                    })

                                }
                                flag = true
                            }
                        })

                }

            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun itemClick(view: View, data: AccountLevel, positon: Int) {
        if (positon != 0 && levelListT?.get(positon - 1)?.valueCode.isNullOrEmpty()) {
            "${levelListT?.get(positon - 1)?.attr_tip}".showToast()
            return
        }
        setAd(view, positon, data.attr_idx)
    }

    fun setAddressSelect(asd: AddressSelect) {
        addressSelect = asd
    }

    interface AddressSelect {
        fun getSelectAddress(levelListT: List<AccountLevel>)
    }


}