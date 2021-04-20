package com.zky.basics.api.config

object API {


    const val IP = "http://47.104.136.79:2021/"
    const val URL_HOST = IP + "zkydls_shikong/"

    //ali image
    const val ImageFolderPath = "https://zjcp.oss-cn-beijing.aliyuncs.com/"

    //ali upload file
    const val ImageAliFolderPath = "oss-cn-beijing.aliyuncs.com"
    const val URL_APP_UPDATE = URL_HOST+"getAppInfo.do"
    const val PAGE_SIZE = 10
    const val URL_UPLOAD_NEW = URL_HOST + "savaFileInfo.do"
    const val URL_UPLOAD_HEARD = URL_HOST + "uploadUserHeadImg.do"
}