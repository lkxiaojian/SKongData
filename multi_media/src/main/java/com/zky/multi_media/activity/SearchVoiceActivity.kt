package com.zky.multi_media.activity


import android.content.Intent
import android.os.Parcelable
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.zky.basics.api.room.bean.MediaBean
import com.zky.basics.common.adapter.BaseBindAdapter
import com.zky.basics.common.mvvm.BaseMvvmRefreshActivity
import com.zky.basics.common.util.ObservableListUtil
import com.zky.multi_media.BR
import com.zky.multi_media.R
import com.zky.multi_media.adapter.VoiceSearchListAdapter
import com.zky.multi_media.databinding.ActivitySearchVoiceBinding
import com.zky.multi_media.mvvm.factory.MediaViewModelFactory
import com.zky.multi_media.mvvm.viewmodle.VoiceListViewModle
@Route(path = ARouterPath.MEDIA_SELECT_VOICE)
class SearchVoiceActivity :
    BaseMvvmRefreshActivity<ActivitySearchVoiceBinding, VoiceListViewModle>(),
    BaseBindAdapter.OnItemClickListener<Any> {

    @Autowired
    @JvmField
    var mediaType2: String? = ""
    @Autowired
    @JvmField
    var mediaType3: String? = ""

    private lateinit var voiceSearchListAdapter: VoiceSearchListAdapter

    override fun refreshLayout() = mBinding?.drlVoice
    override fun onBindViewModel() = VoiceListViewModle::class.java
    override fun onBindViewModelFactory() = MediaViewModelFactory.getInstance(application)
    override fun initData() {
        super.initData()
        mViewModel?.mediaType2?.set(mediaType2)
        mViewModel?.mediaType3?.set(mediaType3)
        mViewModel?.search()
    }
    override fun initViewObservable() {
        showInitLoadView(true)

        voiceSearchListAdapter = VoiceSearchListAdapter(this, mViewModel?.mList)
        mViewModel?.mList?.addOnListChangedCallback(
            ObservableListUtil.getListChangedCallback(
                voiceSearchListAdapter
            )
        )
        voiceSearchListAdapter.setItemClickListener(this)
        mBinding?.recview?.adapter = voiceSearchListAdapter
        mViewModel?.activity = this


        mViewModel?.getmVoidSingleLiveEvent()?.observe(this,
            Observer { it ->
                if (it == "sure") {
                    val list = mViewModel?.mList?.filter { it.check }
                    val intent = Intent()
                    intent.putParcelableArrayListExtra("data", list as ArrayList<out Parcelable>)
                    setResult(0, intent)
                    finish()
                }else if(it=="dismiss"){
                    showInitLoadView(false)
                }
            }
        )

    }


    override fun onBindVariableId() = BR.voiceListViewModel

    override fun onBindLayout() = R.layout.activity_search_voice

    override val tootBarTitle = "选择音频"
    override fun onBindToolbarLayout() = R.layout.white_common_toolbar
    override fun onItemClick(e: Any, position: Int) {
        val mediaBean = e as MediaBean
        mViewModel?.mList?.get(position)?.check = !mediaBean.check
    }


}