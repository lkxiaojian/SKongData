package com.zky.task_chain.mvvm.viewmodle

import android.app.Application
import android.view.View
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import com.zky.basics.api.common.entity.chine.SelectPeople
import com.zky.basics.api.common.entity.task.TaskQuestion
import com.zky.basics.api.splash.entity.AccountLevel
import com.zky.basics.api.splash.entity.Userinfo
import com.zky.basics.common.event.SingleLiveEvent
import com.zky.basics.common.mvvm.viewmodel.BaseRefreshViewModel
import com.zky.basics.common.util.spread.decodeParcelable
import com.zky.task_chain.R
import com.zky.task_chain.activity.SelectPeopleActivity.Companion.selects
import com.zky.task_chain.bean.SelectPeopleLevelBean
import com.zky.task_chain.mvvm.model.ChainModel


/**
 * Created by lk
 * Date 2019-11-08
 * Time 11:06
 * Detail:
 */
class SelectPeopleViewModle(application: Application, model: ChainModel) :
    BaseRefreshViewModel<SelectPeople, ChainModel>(application, model) {
    private var mVoidSingleLiveEvent: SingleLiveEvent<String>? = null
    val zpListr = arrayListOf<Any>()
    var accountLevel = ObservableField<String>()
    var selectType = ObservableField<String>()

    var town = ObservableField<String>()
    var village = ObservableField<String>()
    var type = ObservableField<String>()
    var searchMessage = ObservableField<String>()
    var firstShow = ObservableField<Boolean>()
    var index = 1
    var userinfo: Userinfo? = null
    var  levelList:List<AccountLevel>?=null
    var showList = ObservableArrayList<SelectPeopleLevelBean>()
    init {
        userinfo = decodeParcelable<Userinfo>("user")
    }

    override fun refreshData() {
        index = 1
        getData()
    }

    override fun loadMore() {
        index += 1
        postStopLoadMoreEvent()
        postStopRefreshEvent()
//        getData()
    }
//    "请示", "指派", "对接", "回复"
    fun setLevelData() {

        launchUI({
            if(userinfo==null){
                return@launchUI
            }
            levelList = mModel.getAccountLevel(null)
            levelList?.let { it ->
                zpListr.clear()
                it.forEach{
                    when (selectType.get()) {
                        "请示"->{
                            if(it.attr_idx>userinfo!!.accountLevel){
                                it.attr_name?.let { it1 -> zpListr.add(it1) }
                            }
                        }
                        "指派"->{
                            if(it.attr_idx<userinfo!!.accountLevel){
                                it.attr_name?.let { it1 -> zpListr.add(it1) }
                            }
                        }
                        "对接"->{
                            if(it.attr_idx==userinfo!!.accountLevel){
                                it.attr_name?.let { it1 -> zpListr.add(it1) }
                            }
                        }

                        "回复"->{
                            it.attr_name?.let { it1 -> zpListr.add(it1) }
                        }

                    }
                }

            }

            showList.add(SelectPeopleLevelBean("level","级别",zpListr))
        })


    }

    fun getData() {
        launchUI({
            val userList = mModel.getUserList(
                accountLevel.get(),
                town.get(),
                village.get(),
                searchMessage.get(),
                type.get(),
                index
            )
            userList?.let { it ->
                if (index == 1) {
                    mList.clear()
                }
                if (firstShow.get() == true) {
                    for ((i, v) in it.withIndex()) {
                        selects.forEach { a ->
                            if (v.code == a.code) {
                                it[i].check = true
                            }
                        }
                    }
                }
                mList.addAll(it)
                firstShow.set(false)
            }
            postStopLoadMoreEvent()
            postStopRefreshEvent()
        }, object : NetError {
            override fun getError(e: Exception) {
                postStopLoadMoreEvent()
                postStopRefreshEvent()
            }
        })
    }

    fun startClick(view: View) {
        when (view.id) {
            R.id.aiv_back -> {
                getmVoidSingleLiveEvent().value = "back"

            }
            R.id.search_people -> {

            }
            R.id.abt_sure -> {
                getmVoidSingleLiveEvent().value = "sure"
            }
        }

    }


    fun getmVoidSingleLiveEvent(): SingleLiveEvent<String> {
        return createLiveData(mVoidSingleLiveEvent).also {
            mVoidSingleLiveEvent = it
        }
    }
}