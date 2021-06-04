package com.zky.basics.common.constant

import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider
import com.zky.basics.api.common.entity.task.DepartmentDataBean

/**
 * Created by lk
 * Date 2019-11-13
 * Time 18:00
 * Detail:
 */
object Constants {
    var ossStsTokenCredentialProvider: OSSStsTokenCredentialProvider? = null
    var isNet = true

    const val providePath = "com.zky.sk.data.fileProvider"
    const val bucketName = "zkydls-test"
    const val appCode = "27104114940837888"

    var itemCode = ""
    var taskName: String? = ""
    var wjCode: String? = ""
    var dataAttr2: String? = ""
    var id_card: String? = ""
    var taskCode: String? = ""
    var dxm: String? = ""
    var mediaDataTypePhoto: String? = ""
    var mediaDataTypeVideo: String? = ""
    var mediaDataTypeAudio: String? = ""
    var dataList: ArrayList<DepartmentDataBean>? = arrayListOf()

}