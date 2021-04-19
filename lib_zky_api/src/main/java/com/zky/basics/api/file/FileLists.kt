package com.zky.basics.api.file

import com.zky.basics.api.room.bean.MediaBean

/**
 *create_time : 21-4-16 上午10:20
 *author: lk
 *description： FileLists
 */

data class FileData(
    var title: String,
    var subTile: String,
    var type: String,
    var files: ArrayList<MediaBean>?,
    var showTile:Boolean
)

data class MediaJson(
    var title: String,
    val title_id: String,
    var sortList: ArrayList<SubTitle>
)

data class SubTitle(var subTitle: String, var subTitle_id: String)
