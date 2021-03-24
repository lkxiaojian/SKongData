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
    fun startAudio(path: String) {
        var di=1
        try {
            var player = mapList[path]
            if (player == null) {
                player = MediaPlayer()
            }

            if (player.isPlaying) {
                player.pause()
                click?.pauseListener(
                    path,
                    ((player.duration - player.currentPosition) / 1000).toInt()
                )
                return
            }
            mapList.forEach {
//                click?.pauseListener(path,((player.duration- player.currentPosition)/1000).toInt())
                it.value.pause()
            }
            val contains = mapList.contains(path)
            if (contains) {
                player.start()
                click?.startListener(
                    path,
                    ((player.duration - player.currentPosition) / 1000).toInt()
                )
                return
            }

            mapList[path] = player
            player.setDataSource(path)
            player.prepare()
            player.start()
            click?.startListener(path, (player.duration / 1000).toInt())

            player.setOnCompletionListener {
                click?.completionListener(path)
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
        fun completionListener(path: String)
        fun startListener(path: String, length: Int)
        fun pauseListener(path: String, length: Int)
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