package com.zky.basics.api.common.entity.task

import com.zky.basics.annotation.AllowNoArg

/**
 *create_time : 21-4-23 上午10:27
 *author: lk
 *description： TaskChartAdBean
 */
@AllowNoArg
data class TaskChartAdBean (var mDatas:ArrayList<Double>,var mDesciption:ArrayList<String>,var avgData:Double,var isAnimation:Boolean,var title:String)