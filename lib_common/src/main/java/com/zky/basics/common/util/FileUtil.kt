package com.zky.basics.common.util

import android.text.TextUtils
import com.zky.basics.common.util.InfoVerify.isEmpty
import java.io.*
import java.util.regex.Pattern

object FileUtil {
    /**
     * TODO 判断是否 图片
     *
     * @param url
     * @return
     */
    fun isImageFile(url: String): Boolean {
        if (TextUtils.isEmpty(url)) {
            return false
        }
        val reg = ".+(\\.jpeg|\\.jpg|\\.gif|\\.bmp|\\.png).*"
        val pattern = Pattern.compile(reg)
        val matcher = pattern.matcher(url.toLowerCase())
        return matcher.find()
    }

    /**
     * TODO 判断是否是视频
     *
     * @param url
     * @return
     */
    fun isVideoFile(url: String): Boolean {
        if (TextUtils.isEmpty(url)) {
            return false
        }
        val reg =
            ".+(\\.avi|\\.wmv|\\.mpeg|\\.mp4|\\.mov|\\.mkv|\\.flv|\\.f4v|\\.m4v|\\.rmvb|\\.rm|\\.rmvb|\\.3gp|\\.dat|\\.ts|\\.mts|\\.vob).*"
        val pattern = Pattern.compile(reg)
        val matcher = pattern.matcher(url.toLowerCase())
        return matcher.find()
    }

    /**
     * TODO 判断是否是音频
     *
     * @param url
     * @return
     */
    fun isVoiceFile(url: String): Boolean {
        if (TextUtils.isEmpty(url)) {
            return false
        }
        val reg =
            ".+(\\.mp3|\\.m4a|\\.arm).*"
        val pattern = Pattern.compile(reg)
        val matcher = pattern.matcher(url.toLowerCase())
        return matcher.find()
    }




    fun isUrl(url: String): Boolean {
        if (TextUtils.isEmpty(url)) {
            return false
        }
        val reg = "(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]"
        return url.matches(reg.toRegex())
    }

    fun getFileByte(filename: String?): ByteArray? {
        val f = File(filename)
        if (!f.exists()) {
            return null
        }
        val bos = ByteArrayOutputStream(
            f.length().toInt()
        )
        var inbs: BufferedInputStream?
        try {
            inbs = BufferedInputStream(FileInputStream(f))
            val buf_size = 1024
            val buffer = ByteArray(buf_size)
            var len: Int
            while (-1 != inbs.read(buffer, 0, buf_size).also { len = it }) {
                bos.write(buffer, 0, len)
            }
            inbs.close()
            return bos.toByteArray()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                bos.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return null
    }

    /**
     * 根据文件路径获取文件的名称
     *
     * @param path
     * @return
     */
    fun getNameByPath(path: String): String {
        if (isEmpty(path)) {
            return ""
        }
        val list = path.split("/").toTypedArray()
        return list[list.size - 1]
    }


    /**
     * TODO 根据路径 查找所有文件
     *
     * @param path 路径
     * @param type 后缀
     * @return
     */
    private val list = arrayListOf<File>()
    fun getFiles(path: String, type: String = ""): ArrayList<File> {
        list.clear()
        val file = File(path)
        if (!file.exists()) {
            return arrayListOf()
        }
      return  getFile(file,type)
    }
    private fun getFile(file: File, type: String=""): ArrayList<File> {
        if (file.isFile) {
            if (type.isNotEmpty()) {
                if (getFileType(type,file.name )) {
                    list.add(file)
                }
            } else {
                list.add(file)
            }
        } else if (file.isAbsolute) {
            val listFiles = file.listFiles()
            listFiles.forEach {
                getFile(it,type)
            }
        }
        return list
    }

    private fun getFileType(type:String,name:String):Boolean{
        var isFlage=false
        when(type){
            "image"->{
                isFlage = isImageFile(name)
            }
            "voice"->{
                isFlage= isVoiceFile(name)
            }
            "video"->{
                isFlage= isVideoFile(name)
            }
        }
        return isFlage

    }


}