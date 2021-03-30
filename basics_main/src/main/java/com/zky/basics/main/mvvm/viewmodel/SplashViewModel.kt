package com.zky.basics.main.mvvm.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableField
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.view.OptionsPickerView
import com.zky.basics.api.RetrofitManager
import com.zky.basics.api.config.API
import com.zky.basics.api.splash.entity.AccountLevel
import com.zky.basics.api.splash.entity.SplashViewBean
import com.zky.basics.api.splash.entity.Userinfo
import com.zky.basics.common.event.SingleLiveEvent
import com.zky.basics.common.mvvm.viewmodel.BaseViewModel
import com.zky.basics.common.util.DisplayUtil.dip2px
import com.zky.basics.common.util.InfoVerify
import com.zky.basics.common.util.NetUtil.checkNet
import com.zky.basics.common.util.SPUtils
import com.zky.basics.common.util.ToastUtil.showToast
import com.zky.basics.common.util.security.SM3
import com.zky.basics.common.util.spread.decodeParcelable
import com.zky.basics.common.util.spread.encode
import com.zky.basics.common.util.spread.showToast
import com.zky.basics.common.view.showFullPopupWindow
import com.zky.basics.main.R
import com.zky.basics.main.activity.FrogetActivity
import com.zky.basics.main.activity.RegistActivity
import com.zky.basics.main.adapter.LevelAdapter
import com.zky.basics.main.mvvm.model.SplashModel
import io.reactivex.rxjava3.disposables.Disposable
import views.ViewOption.OptionsPickerBuilder
import java.lang.ref.WeakReference
import java.util.*

class SplashViewModel(application: Application, model: SplashModel) :
    BaseViewModel<SplashModel>(application, model) {
    private var mVoidSingleLiveEvent: SingleLiveEvent<String>? = null
    private var pickerBuilder: OptionsPickerBuilder? = null

    var name = ObservableField<String>()
    var paw = ObservableField<String>()
    var levelListT = arrayListOf<AccountLevel>()
    lateinit var levelAdapter: LevelAdapter
    var attr_idx = -1
    var data = ObservableField<SplashViewBean>()
    private var timer: Timer? = null
    private var timerTask: TimerTask? = null
    private var rgitstView: View? = null
    private var splashViewBean: SplashViewBean? = null
    private var token: String? = null
    private var subscribe: Disposable? = null
    private var pickerView: OptionsPickerView<Any?>? = null
    private var handler = WeakReference(CustomHandle()).get()

    private val levelList: MutableList<Any?> = arrayListOf()


    private var time = 60
    private fun initPicker(application: Context) {

        pickerBuilder = OptionsPickerBuilder(application)
            .setCancelText("取消")
            .setCancelColor(ContextCompat.getColor(application, R.color.color_b0b0b0))
            .setSubCalSize(16)
            .setSubmitColor(ContextCompat.getColor(application, R.color.color_4a90e2))
            .setSubmitText("确定")
            .setContentTextSize(20) //滚轮文字大小
            .setTextColorCenter(ContextCompat.getColor(application, R.color.color_333)) //设置选中文本的颜色值
            .setLineSpacingMultiplier(2f) //行间距
            .setDividerColor(ContextCompat.getColor(application, R.color.color_f5f5f5)) //设置分割线的颜色
        pickerView = pickerBuilder!!.build()
    }

    private fun login() {
        val sName = name.get()
        val sTmpPaw = paw.get()

        if (sName!!.isEmpty()) {
            showToast("用户名为空")
            return
        }
        if (InfoVerify.isEmpty(sTmpPaw)) {
            showToast("密码为空")
            return
        }
        val sPaw = SM3.encrypt(sTmpPaw!!)
        //离线登入
//        if (!checkNet()) {
//            val userinfo = decodeParcelable<Userinfo>("user")
//            if (userinfo?.accountLevel == 5) {
//                val phone = userinfo.phone
//                val pwd = userinfo.password
//                if (phone != name.get() || pwd != paw.get()) {
//                    R.string.acountorpaw.showToast()
//                    return
//                }
//                getmVoidSingleLiveEvent().value = "noNet"
//                mVoidSingleLiveEvent!!.call()
//            } else {
//                R.string.net_error.showToast()
//            }
//            return
//        }
        getmVoidSingleLiveEvent().value = "loadShow"
        getmVoidSingleLiveEvent().call()
        launchUI({
            val userinfo = mModel.login(sName, sPaw)
            userinfo?.let { it ->
                it.token?.let {
                    RetrofitManager.TOKEN = it

                }
                //存储 user 对象
                userinfo.headImgPath?.encode("headImgPath")
                userinfo.password?.encode("password")
                userinfo.encode("user")
                getmVoidSingleLiveEvent().value = "login"
                getmVoidSingleLiveEvent().call()
            }

        }, object : NetError {
            override fun getError(e: Exception) {
                getmVoidSingleLiveEvent().value = "miss"
                getmVoidSingleLiveEvent().call()
            }

        })
    }

    fun startClick(view: View) {
        val i = view.id //账号注册
        //忘记密码
        if (i == R.id.register) {
            view.context.startActivity(Intent(view.context, RegistActivity::class.java))
        } else if (i == R.id.forgetPassword) {
            view.context.startActivity(Intent(view.context, FrogetActivity::class.java))
        } else if (i == R.id.forget) { //重置密码
            if (InfoVerify.isEmpty(data.get()?.rgPhone)) {
                showToast("请填写正确的手机号")
                return
            }
            if (InfoVerify.isEmpty(data.get()?.rgImageCode)) {
                showToast("请填写验证码")
                return
            }
            if (InfoVerify.isEmpty(data.get()?.rgPaw)) {
                showToast("账号密码为空")
                return
            }
            if (!InfoVerify.isPwd(data.get()?.rgPaw)) {
                showToast("请输入6-20位字母和数字组合，必须同时含有字母和数字")
                return
            }
            if (data.get()!!.rgPaw != data.get()?.rgqrPaw) {
                showToast("密码和确认密码不一致")
                return
            }
            foggerPas()
        } else if (i == R.id.register_get_message || i == R.id.forget_get_message) {
            rgitstView = view
            if (!InfoVerify.isPhone(data.get()!!.rgPhone)) {
                showToast("请填写正确的手机号")
                return
            }
            if (data.get()!!.rgImageCode == null || data.get()?.rgImageCode!!.isEmpty()) {
                showToast("请填写验证码")
                return
            }
            if (token!!.isEmpty()) {
                showToast("验证码失效")
                captcha()
                return
            }
            var type = "regist"
            if (view.id == R.id.forget_get_message) {
                type = "forget"
            }
            sendSms(type)
        } else if (i == R.id.register_mark) {
            val contentView = LayoutInflater.from(view.context)
                .inflate(R.layout.popup_tip, null)
            val params = RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            val location = IntArray(2)
            view.getLocationOnScreen(location)
            params.topMargin = location[1] + view.height / 2
            params.leftMargin = location[0] + view.width / 2 - dip2px(22f)
            contentView.layoutParams = params
            showFullPopupWindow(
                contentView,
                view,
                R.color.color_99ffffff
            )
        } else if (i == R.id.register_account_level) { //账号级别
            initPicker(view.context)
            selectLevel()

        } else if (i == R.id.register_get_image || i == R.id.forget_get_image) {
            captcha()
        } else if (i == R.id.start_register) {
            if (InfoVerify.isEmpty(data.get()!!.rgName)) {
                showToast("用户名为空")
                return
            }
            if (!InfoVerify.isChinese(data.get()!!.rgName)) {
                showToast("姓名只能是中文")
                return
            }
            if (!InfoVerify.isPhone(data.get()!!.rgPhone)) {
                showToast("请填写正确的手机号")
                return
            }
            if (InfoVerify.isEmpty(data.get()!!.rgCode)) {
                showToast("短信验证码为空")
                return
            }
            val rgLevel = data.get()?.rgLevel
            if (InfoVerify.isEmpty(rgLevel) || "账号级别" == rgLevel) {
                showToast("账号级别为空")
                return
            }
            if (InfoVerify.isEmpty(data.get()!!.rgPaw)) {
                showToast("账号密码为空")
                return
            }

            if (data.get()!!.job.isNullOrEmpty()) {
                showToast("单位职务为空")
                return
            }
            if (!InfoVerify.isPwd(data.get()!!.rgPaw)) {
                showToast("请输入6-20位字母和数字组合，必须同时含有字母和数字")
                return
            }
            if (data.get()!!.rgPaw != data.get()!!.rgqrPaw) {
                showToast("密码和确认密码不一致")
                return
            }
            val list = levelAdapter.getList()
            val p = SM3.encrypt(data.get()!!.rgPaw.toString())
            var registUrl =
                API.URL_HOST + "regist.do?username=${data.get()?.rgName}" +
                        "&password=$p&accountLevel=$attr_idx&phone=${data.get()?.rgPhone}&" +
                        "smsCode=${data.get()?.rgCode}&job=${data.get()?.job}"
            list.forEach {
                if (it.value.isNullOrEmpty()) {
                    it.attr_tip?.showToast()
                    return
                }
                registUrl += "&${it.attr}=${it.value}&${it.attr_code}=${it.valueCode}"
            }
            launchUI({
                mModel.regist(registUrl)
                "注册成功，请等待管理员审核！".showToast()
                getmVoidSingleLiveEvent().value = "finsh"
            }
            )


        } else if (i == R.id.login) {
            login()
        }
    }

    private fun selectLevel() {
        launchUI({
            val accountLevel = mModel.getAccountLevel(null)
            levelList.clear()
            accountLevel?.let { it ->
                it.forEach {
                    levelList.add(it.attr_name)
                }
                pickerView?.setPicker(levelList)
                pickerView?.setSelectOptions(data.get()!!.levelIndel)
                pickerView?.show()
                pickerBuilder?.setOnOptionsSelectListener (OnOptionsSelectListener{ options1, _, _, _ ->
                    attr_idx = accountLevel[options1].attr_idx
                    levelListT.clear()
                    for ((index, value) in accountLevel.withIndex()) {
                        if (index <= options1) {
                            levelListT.add(value)
                        }
                    }
                    data.get()?.rgLevel = levelList[options1].toString()
                    data.get()?.writeLevel = true
                    getmVoidSingleLiveEvent().value = "notify"

                })

            }


        })

    }

    /**
     * 忘记密码
     */
    private fun foggerPas() {
        launchUI({
            val updateUserPassword = mModel.updateUserPassword(
                "forget",
                data.get()!!.rgPhone,
                "",
                SM3.encrypt(data.get()!!.rgPaw!!),
                data.get()!!.rgCode
            )
            showToast(updateUserPassword.toString())
            getmVoidSingleLiveEvent().call()
        })
    }

    fun getmVoidSingleLiveEvent(): SingleLiveEvent<String> {
        return createLiveData(mVoidSingleLiveEvent).also {
            mVoidSingleLiveEvent = it
        }
    }

    private fun startTimer() {
        rgitstView!!.isEnabled = false
        timer = Timer()
        timerTask = object : TimerTask() {
            override fun run() {
                handler?.sendEmptyMessage(1)
            }
        }
        timer?.schedule(timerTask, 0, 1000)
    }

    fun captcha() {
        launchUI({
            //数据库使用 用例
//          var  testRoomDbDao =
//                AppDatabase.getDatabase(getApplication())?.testRoomDbDao()!!
//            val testRoomDb = TestRoomDb(2231, "name", 3, "1", "3")
//            val list= arrayListOf<TestRoomDb>()
//            list.add(testRoomDb)
//            testRoomDbDao.insertOrUpdate(list)
//            val users = testRoomDbDao.users()

            val captcha = mModel.captcha()
            captcha?.let {
                data.get()?.rgImageUrl =
                    it.getBitmap()
                token = it.token
            }

        })
    }

    fun getRegion(regionLevel: Int?, position: Int, adapter: LevelAdapter) {
        levelAdapter = adapter
        launchUI({
            val region = mModel.getRegion(regionLevel)
            levelList.clear()
            region?.let { it ->
                it.forEach {
                    levelList.add(it.name)
                }
                pickerView?.setPicker(levelList)
                pickerView?.setSelectOptions(data.get()!!.levelIndel)
                pickerView?.show()
                pickerBuilder?.setOnOptionsSelectListener (OnOptionsSelectListener{ options1, _, _, _ ->
                    for ((index, _) in levelListT.withIndex()) {
                        if (index >= position) {
                            levelListT[index].value = ""
                        }
                    }
                    levelListT[position].value = levelList[options1].toString()
                    levelListT[position].valueCode = region[options1].code
                    getmVoidSingleLiveEvent().value = "notify"
                })
            }
        })

    }


    /**
     * 发送验证吗
     */
    private fun sendSms(type: String) {

        launchUI({
            mModel.sendSms(
                token,
                data.get()!!.rgImageCode,
                data.get()!!.rgPhone,
                type
            )
            startTimer()
            "发送成功".showToast()
        }, object : NetError {
            override fun getError(e: Exception) {
                resume()
            }
        })
    }


    @SuppressLint("HandlerLeak")
    inner class CustomHandle : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            if (msg?.what == 1) {
                time -= 1
                data.get()?.timeMeesage = time.toString() + "秒"
                if (time == 1) {
                    resume()
                }
            }
        }

    }

    private fun resume() {
        if (timer != null) {
            timer?.cancel()
            timerTask?.cancel()
        }
        data.get()?.timeMeesage = "获取"
        data.set(splashViewBean)
        time = 60
        rgitstView?.isEnabled = true
    }

    override fun onDestroy() {
        super.onDestroy()
        if (subscribe != null) {
            subscribe!!.dispose()
        }
        if (handler != null) {
            handler?.removeMessages(1)
            handler = null
        }
        if (timer != null) {
            timer?.cancel()
            timerTask?.cancel()
            timerTask = null
            timer = null
        }
    }

    companion object {
        var TAG = SplashViewModel::class.java.simpleName
    }

    init {
        val phone = SPUtils.get(getApplication(), "phone", "")
        val pwd =
            SPUtils.get(getApplication(), phone.toString(), "")
        name.set(phone.toString())
        paw.set(pwd.toString())
        //        getCaptcha();
        try {
            splashViewBean = SplashViewBean()
            splashViewBean?.let {
                it.timeMeesage = "获取"
                it.rgLevel = "账号级别"
                it.rgProvince = "省"
                it.rgTwon = "县"
                it.rgCity = "市"
                it.rgSchool = "学校"
                it.levelIndel = -1
                it.rgErrorImageUrl = R.drawable.image_code_error
            }
            data.set(splashViewBean)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}