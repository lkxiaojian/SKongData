package com.zky.task_chain.mvvm.viewmodle

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.view.OptionsPickerView
import com.zky.basics.common.event.SingleLiveEvent
import com.zky.basics.common.mvvm.viewmodel.BaseViewModel
import com.zky.task_chain.R
import com.zky.task_chain.mvvm.model.ChainModel
import views.ViewOption.OptionsPickerBuilder


/**
 * Created by lk
 * Date 2019-11-08
 * Time 11:06
 * Detail:
 */
class AddDealMessageViewModle(application: Application, model: ChainModel) :
    BaseViewModel<ChainModel>(application, model) {
    val zpList= arrayListOf<Any>("请示","指派","对接")
    val zpListr= arrayListOf<Any>("请示","指派","对接","回复")
    var queryType = ObservableField<String>()
    var pickerBuilder: OptionsPickerBuilder? = null
    var pickerView: OptionsPickerView<Any>? = null
    var zpMessage = ObservableField<String>()
    private var mVoidSingleLiveEvent: SingleLiveEvent<String>? = null
    init {
        zpMessage.set("请示")
    }

    fun startClick(view: View) {
        when (view.id) {
            R.id.aiv_del_people -> {
                getmVoidSingleLiveEvent().value = "startSelectPeople"
            }
            R.id.atv_type->{
                showzpPicker()
            }
        }
    }

    fun getmVoidSingleLiveEvent(): SingleLiveEvent<String> {
        return createLiveData(mVoidSingleLiveEvent).also {
            mVoidSingleLiveEvent = it
        }
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