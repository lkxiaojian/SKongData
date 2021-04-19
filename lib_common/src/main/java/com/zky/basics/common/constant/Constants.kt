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
    var mediaDataTypePhoto:String? = "[\n" +
            "    {\n" +
            "        \"title\": \"家庭照片\",\n" +
            "        \"title_id\": \"111\",\n" +
            "        \"classifyList\": [\n" +
            "            {\n" +
            "                \"subtitle\": \"户主头像\",\n" +
            "                \"type_id\": \"1\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"subtitle\": \"户主身份证\",\n" +
            "                \"type_id\": \"2\"\n" +
            "            }\n" +
            "        ]\n" +
            "    },\n" +
            "    {\n" +
            "        \"title\": \"家庭照片2\",\n" +
            "        \"title_id\": \"222\",\n" +
            "        \"classifyList\": [\n" +
            "            {\n" +
            "                \"subtitle\": \"户主头像2\",\n" +
            "                \"type_id\": \"12\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"subtitle\": \"户主身份证2\",\n" +
            "                \"type_id\": \"22\"\n" +
            "            }\n" +
            "        ]\n" +
            "    }\n" +
            "]"


    var mediaDataTypeVideo:String?="[\n" +
            "    {\n" +
            "        \"title\": \"视频1\",\n" +
            "        \"title_id\": \"111\",\n" +
            "        \"classifyList\": [\n" +
            "            {\n" +
            "                \"subtitle\": \"户主头像\",\n" +
            "                \"type_id\": \"1\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"subtitle\": \"户主身份证\",\n" +
            "                \"type_id\": \"2\"\n" +
            "            }\n" +
            "        ]\n" +
            "    },\n" +
            "    {\n" +
            "        \"title\": \"视频2\",\n" +
            "        \"title_id\": \"222\",\n" +
            "        \"classifyList\": [\n" +
            "            {\n" +
            "                \"subtitle\": \"户主头像2\",\n" +
            "                \"type_id\": \"12\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"subtitle\": \"户主身份证2\",\n" +
            "                \"type_id\": \"22\"\n" +
            "            }\n" +
            "        ]\n" +
            "    }\n" +
            "]"

    var mediaDataTypeAudio:String?="[\n" +
            "    {\n" +
            "        \"title\": \"音频1\",\n" +
            "        \"title_id\": \"111\",\n" +
            "        \"classifyList\": [\n" +
            "            {\n" +
            "                \"subtitle\": \"户主头像\",\n" +
            "                \"type_id\": \"1\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"subtitle\": \"户主身份证\",\n" +
            "                \"type_id\": \"2\"\n" +
            "            }\n" +
            "        ]\n" +
            "    },\n" +
            "    {\n" +
            "        \"title\": \"音频2\",\n" +
            "        \"title_id\": \"222\",\n" +
            "        \"classifyList\": [\n" +
            "            {\n" +
            "                \"subtitle\": \"户主头像2\",\n" +
            "                \"type_id\": \"12\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"subtitle\": \"户主身份证2\",\n" +
            "                \"type_id\": \"22\"\n" +
            "            }\n" +
            "        ]\n" +
            "    }\n" +
            "]"

}