package com.zky.task_chain.mvvm.viewmodle

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.view.OptionsPickerView
import com.zky.basics.api.common.entity.chine.TaskChineItemBean
import com.zky.basics.api.splash.entity.Userinfo
import com.zky.basics.common.mvvm.viewmodel.BaseRefreshViewModel
import com.zky.basics.common.util.spread.decodeParcelable
import com.zky.task_chain.R
import com.zky.task_chain.mvvm.model.ChainModel
import views.ViewOption.OptionsPickerBuilder


/**
 * Created by lk
 * Date 2019-11-08
 * Time 11:06
 * Detail:
 */
class ChainViewModle(application: Application, model: ChainModel) :
    BaseRefreshViewModel<TaskChineItemBean, ChainModel>(application, model) {
    var userinfo: Userinfo? = null
    var queryType = ObservableField<String>()
    var pageIndex = 1
    var pickerBuilder: OptionsPickerBuilder? = null
    var pickerView: OptionsPickerView<Any>? = null
    val fkList= arrayListOf<Any>("请选择","已反馈","未反馈")
    val zpList= arrayListOf<Any>("请选择","请示","指派","对接")
    val zpListr= arrayListOf<Any>("请选择","请示","指派","对接","回复")
    var fkMessage = ObservableField<String>()
    var zpMessage = ObservableField<String>()
    var searchMessage=ObservableField<String>()

    init {
        userinfo = decodeParcelable<Userinfo>("user")
        fkMessage.set("")
        searchMessage.set("")
        zpMessage.set("")
    }

    override fun refreshData() {
        pageIndex = 1
        getData()
        postStopLoadMoreEvent()
        postStopRefreshEvent()
    }

    override fun loadMore() {
        pageIndex += 1
        postStopLoadMoreEvent()
        postStopRefreshEvent()
    }


    fun startClick(view: View){
        when(view.id){
            R.id.atv_fkzt->{
                showfkPicker()
            }
            R.id.atv_fk_type->{
                showzpPicker()
            }

            R.id.search_bar->{
                pageIndex=1
                getData()
            }
        }
    }

    fun getData() {
        launchUI({
            val taskPageList =
                mModel.getTaskPageList(userinfo?.code, queryType.get(), fkMessage.get(), searchMessage.get(),zpMessage.get(), pageIndex)
            taskPageList?.let {
                if (pageIndex == 1) {
                    mList.clear()
                }
                if (mList.size >= it.totalNum) {
                    postStopLoadMoreEvent()
                    postStopRefreshEvent()
                    return@launchUI
                }
                it.pageList?.let { it1 -> mList.addAll(it1) }
                postStopLoadMoreEvent()
                postStopRefreshEvent()
            }
        },object :NetError{
            override fun getError(e: Exception) {
                postStopLoadMoreEvent()
                postStopRefreshEvent()
            }

        })
    }
    fun showfkPicker() {
        pickerView?.setPicker(fkList)
        pickerView?.show()
        pickerBuilder?.setOnOptionsSelectListener(OnOptionsSelectListener { options1, _, _, _ ->
            if(options1==0){
                fkMessage.set("")
                return@OnOptionsSelectListener
            }
            fkMessage.set(fkList[options1].toString())
        })
    }

    fun showzpPicker(){
        val list=if(queryType.get()=="send") zpList else zpListr
        pickerView?.setPicker(list)
        pickerView?.show()
        pickerBuilder?.setOnOptionsSelectListener(OnOptionsSelectListener { options1, _, _, _ ->
            if(options1==0){
                zpMessage.set("")
                return@OnOptionsSelectListener
            }
            zpMessage.set(list[options1].toString())
        })
    }

}