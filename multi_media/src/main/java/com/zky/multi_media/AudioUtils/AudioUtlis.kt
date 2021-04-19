import android.media.MediaPlayer


/**
 *create_time : 21-3-12 下午2:44
 *author: lk
 *description： AudioUtlis
 */
class AudioUtlis private constructor() {

    companion object {
        private val mapList = hashMapOf<String, MediaPlayer>()
        fun getAudioUtlis(): AudioUtlis {
            return AudioUtlis()
        }
    }


    private var click: AudioClick? = null
    fun startAudio(path: String,dataIndex:Int,fileIndex:Int) {
        try {
            var player = mapList["$dataIndex$fileIndex"]
            if (player == null) {
                player = MediaPlayer()
            }

            if (player.isPlaying) {
                player.pause()
                click?.pauseListener(dataIndex,fileIndex,
                    path,
                    ((player.duration - player.currentPosition) / 1000).toInt()
                )
                return
            }
            mapList.forEach {
//                click?.pauseListener(path,((player.duration- player.currentPosition)/1000).toInt())
                it.value.pause()
            }
            val contains = mapList.contains("$dataIndex$fileIndex")
            if (contains) {
                player.start()
                click?.startListener(dataIndex,fileIndex,
                    path,
                    ((player.duration - player.currentPosition) / 1000).toInt()
                )
                return
            }

            mapList[path] = player
            player.setDataSource(path)
            player.prepare()
            player.start()
            click?.startListener(dataIndex,fileIndex,path, (player.duration / 1000))

            player.setOnCompletionListener {
                click?.completionListener(dataIndex,fileIndex,path)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun setAudioClick(c: AudioClick): AudioUtlis {
        click = c
        return this
    }

    interface AudioClick {
        fun completionListener(dataIndex:Int,fileIndex:Int,path: String)
        fun startListener(dataIndex:Int,fileIndex:Int,path: String, length: Int)
        fun pauseListener(dataIndex:Int,fileIndex:Int,path: String, length: Int)
    }


    fun stop(path: String?) {
        if (path.isNullOrEmpty()) {
            mapList.forEach {
                it.value.pause()
            }
        } else {
            mapList[path]?.stop()
        }

    }


    /**
     * TODO 获取 MediaPlayer 对象
     *
     * @return
     */
    fun getPlayer(path: String): MediaPlayer? {
        return mapList[path]
    }

}