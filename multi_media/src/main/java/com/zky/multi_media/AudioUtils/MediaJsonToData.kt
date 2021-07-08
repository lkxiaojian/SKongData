import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zky.basics.api.file.FileData
import com.zky.basics.api.file.MediaJson
import com.zky.basics.common.constant.Constants
import java.lang.Exception

/**
 *create_time : 21-4-19 下午2:35
 *author: lk
 *description： MediaJson
 */
object MediaJsonToData {
    fun getMediaList(type: String): ArrayList<FileData> {
        try {


            val classType = object : TypeToken<ArrayList<MediaJson>>() {}.type
            val list = when (type) {
                "image" -> Gson().fromJson<ArrayList<MediaJson>>(
                    Constants.mediaDataTypePhoto,
                    classType
                )
                "video" -> Gson().fromJson<ArrayList<MediaJson>>(
                    Constants.mediaDataTypeVideo,
                    classType
                )
                else ->
                    Gson().fromJson<ArrayList<MediaJson>>(Constants.mediaDataTypeAudio, classType)

            }

            var listAd = arrayListOf<FileData>()
            list?.forEach {
                if(it.sortList.isNullOrEmpty()){
                    listAd.add(FileData(it.title, it.title,"", "", arrayListOf(), true,false))
                }else{
                    for ((index, value) in it.sortList.withIndex()) {
                        val fileData = if (index == 0) {
                            FileData(it.title, it.title,value.subTitle, value.subTitle_id, arrayListOf(), true,true)
                        } else {
                            FileData("", it.title,value.subTitle, value.subTitle_id, arrayListOf(), false,true)
                        }
                        listAd.add(fileData)
                    }
                }

            }
            return listAd
        } catch (e: Exception) {
            return arrayListOf()

        }

    }
}