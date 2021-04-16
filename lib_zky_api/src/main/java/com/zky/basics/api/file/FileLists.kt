package com.zky.basics.api.file

import com.zky.basics.api.room.bean.MediaBean

/**
 *create_time : 21-4-16 上午10:20
 *author: lk
 *description： FileLists
 */
class FileDataS(dataS: List<FileData>?)
data class FileData(var title: String?, var subTile: String?, var type: String?,var  files: List<MediaBean>?)