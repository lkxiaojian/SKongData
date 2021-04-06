package com.zky.basics.api.room.bean

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by lk
 * Date 2020/9/24
 * Time 17:10s
 * Detail:
 */
//

data class Areas(var province:List<Area>,var town:List<Area>,var city:List<Area>,var county:List<Area>,var village:List<Area>)
@Entity(tableName = "area")
data class Area(var code: Int?, var name: String,var nextCode:String)