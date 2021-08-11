package com.zky.task_chain.mvvm.viewmodle

import android.app.Application
import android.view.View
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.view.OptionsPickerView
import com.zky.basics.api.common.entity.chine.SelectPeople
import com.zky.basics.api.common.entity.task.TaskQuestion
import com.zky.basics.api.config.API
import com.zky.basics.api.splash.entity.AccountLevel
import com.zky.basics.api.splash.entity.RegionOrSchoolBean
import com.zky.basics.api.splash.entity.Userinfo
import com.zky.basics.common.event.SingleLiveEvent
import com.zky.basics.common.mvvm.viewmodel.BaseRefreshViewModel
import com.zky.basics.common.util.spread.decodeParcelable
import com.zky.task_chain.R
import com.zky.task_chain.activity.SelectPeopleActivity.Companion.selects
import com.zky.task_chain.bean.SelectPeopleLevelBean
import com.zky.task_chain.mvvm.model.ChainModel
import views.ViewOption.OptionsPickerBuilder


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
    var pickerBuilder: OptionsPickerBuilder? = null
    var pickerView: OptionsPickerView<Any>? = null
    var town = ObservableField<String>()
    var village = ObservableField<String>()
    var type = ObservableField<String>()
    var searchMessage = ObservableField<String>()
    var firstShow = ObservableField<Boolean>()
    var index = 1
    var userinfo: Userinfo? = null
    var levelList: List<AccountLevel>? = null
    var showList = ObservableArrayList<SelectPeopleLevelBean>()

    init {
        userinfo = decodeParcelable<Userinfo>("user")
        searchMessage.set("")
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
            if (userinfo == null) {
                return@launchUI
            }
            levelList = mModel.getAccountLevel(null)
            levelList?.let { it ->
                zpListr.clear()
                it.forEach {
                    when (selectType.get()) {
                        "请示" -> {
                            if (it.attr_idx > userinfo!!.accountLevel) {
                                it.attr_name?.let { it1 -> zpListr.add(it1) }
                            }
                        }
                        "指派" -> {
                            if (it.attr_idx < userinfo!!.accountLevel) {
                                it.attr_name?.let { it1 -> zpListr.add(it1) }
                            }
                        }
                        "对接" -> {
                            if (it.attr_idx == userinfo!!.accountLevel) {
                                it.attr_name?.let { it1 -> zpListr.add(it1) }
                            }
                        }

                        "回复" -> {
                            it.attr_name?.let { it1 -> zpListr.add(it1) }
                        }

                    }
                }
            }
            showList.add(SelectPeopleLevelBean("level", "级别", zpListr))
        })
    }

    fun getLevel() {
        launchUI({
            val accountLevel = mModel.getAccountLevel(null)
            val selectPeopleLevelBean = SelectPeopleLevelBean("level", "级别", accountLevel)
            if (showList.size > 0) {
                showList[0] = selectPeopleLevelBean
            }
            val list = arrayListOf<String>()
            accountLevel?.let { it ->
                it.forEach {
                    it.attr_name?.let { it1 -> list.add(it1) }
                }
                showList("level", 0, list, it)
            }

        })

    }

    fun getRegion(regionLevel: Int?, position: Int) {
        launchUI({
            var code: String? = ""
            if (position > 1) {
                code = showList[position - 1].valueCode
            }
            val region = mModel.getRegion(regionLevel, code)

            region?.let { it ->
                val list = arrayListOf<String>()
                it.forEach {
                    it.name?.let { it1 -> list.add(it1) }
                }

                showList("city", position, list, it)
            }


        })

    }

    fun getData() {
        launchUI({
//            val userList = mModel.getUserList(
//                accountLevel.get(),
//                town.get(),
//                village.get(),
//                searchMessage.get(),
//                type.get(),
//                index
//            )
            var url =
                API.URL_HOST + "tasklink/getUserList.do?accountLevel=${userinfo?.accountLevel}" +
                        "&userName=${searchMessage.get()}&pageIndex=$index&pageSize=${API.PAGE_SIZE}&type=${selectType.get()}"

            if (!showList.isNullOrEmpty()) {
                showList?.forEach {
                    if(!it.attr.isNullOrEmpty()&&!it.value.isNullOrEmpty()){
                        url += "&${it.attr}=${it.value}"
                    }

                }
            }
            val userList = mModel.getUserList(url)
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
                index=1
                getData()

            }
            R.id.abt_sure -> {
                getmVoidSingleLiveEvent().value = "sure"
            }
        }
    }


    fun showList(type: String, position: Int, list: List<String>, listValue: List<Any>) {
        pickerView?.setPicker(list)
        pickerView?.show()
        pickerBuilder?.setOnOptionsSelectListener(OnOptionsSelectListener { options1, _, _, _ ->
            when (type) {
                "level" -> {
                    val s = list[options1]
                    showList[position].value = s

                    val selectPeopleLevelBean = showList[0]
                    showList.clear()
                    showList.add(selectPeopleLevelBean)
                    val accountLevel1 = userinfo?.accountLevel
                    val get = selectType.get()
                    if (accountLevel1 == null) {
                        return@OnOptionsSelectListener
                    }

                    val list1 = listValue as List<AccountLevel>
                    showList[position].attr = "regionLevel"
                    showList[position].valueCode = list1[options1].attr_idx.toString()
                    val tmpList = arrayListOf<SelectPeopleLevelBean>()
                    list1.forEach {
                        when (get) {
                            "请示" -> {
                                if (it.attr_idx > accountLevel1) {

                                    tmpList.add(SelectPeopleLevelBean("city", it.attr_name, it))
                                }
                            }
                            "指派" -> {
//                                if (it.attr_idx < accountLevel1) {
//                                    it.attr_name?.let { it1 -> zpListr.add(it1) }
//                                }
                                tmpList.add(SelectPeopleLevelBean("city", it.attr_name, it))
                            }
                            "对接" -> {
                                if (it.attr_idx >= accountLevel1) {
                                    tmpList.add(SelectPeopleLevelBean("city", it.attr_name, it))
                                }
                            }

                            "回复" -> {
                                tmpList.add(SelectPeopleLevelBean("city", it.attr_name, it))
                            }
                        }
                    }

                    showList.addAll(tmpList)
                }

                "city" -> {
                    val list1 = listValue as List<RegionOrSchoolBean>
                    showList[position].value = list[options1]
                    val accountLevel1 = showList[position].data as AccountLevel
                    showList[position].attr = accountLevel1.attr.toString()
                    showList[position].valueCode = list1[options1].code.toString()
                    for ((index, value) in showList.withIndex()) {
                        if (index > position) {
                            showList[index].value = ""
                            showList[index].valueCode = ""
                            showList[position].attr = ""
                        }
                    }
                }
            }

        })
    }


    fun getmVoidSingleLiveEvent(): SingleLiveEvent<String> {
        return createLiveData(mVoidSingleLiveEvent).also {
            mVoidSingleLiveEvent = it
        }
    }
}