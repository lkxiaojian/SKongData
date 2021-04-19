package com.zky.basics.common.constant

import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider

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
    var itemCode = "27093798379061255"
    var dataAttr2: String? = ""
    var taskCode: String? = "27102455388962819"
    var dxm: String? = ""
    var mediaDataTypePhoto:String? = ""


    var mediaDataTypeVideo:String?=""

    var mediaDataTypeAudio:String?="[\n" +
            "    {\n" +
            "        \"title\": \"房子\",\n" +
            "        \"title_id\": \"ndu2h50\",\n" +
            "        \"sortType\": \"video\",\n" +
            "        \"sortList\": [\n" +
            "            {\n" +
            "                \"subTitle\": \"前面\",\n" +
            "                \"subTitle_id\": \"fecn2b02\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"subTitle\": \"后面\",\n" +
            "                \"subTitle_id\": \"onak7201\"\n" +
            "            }\n" +
            "        ]\n" +
            "    },\n" +
            "    {\n" +
            "        \"title\": \"房子2\",\n" +
            "        \"title_id\": \"ndu2h50\",\n" +
            "        \"sortType\": \"video\"\n" +
            "    }\n" +
            "]"

}