import android.media.MediaPlayer


/**
 *create_time : 21-3-12 下午2:44
 *author: lk
 *description： AudioUtlis
 */
class AudioUtlis {

    private val mapList = hashMapOf<String, MediaPlayer>()
    private var click:AudioClick?=null
    fun startAudio(path: String) {
        try {
            var player = mapList[path]
            if (player == null) {
                player = MediaPlayer()
            }
            if (player.isPlaying) {
                player.pause()
                click?.pauseListener(path,((player.currentPosition- player.currentPosition)/1000).toInt())
                return
            }
            val contains = mapList.contains(path)
            if (contains) {
                player.start()
                click?.startListener(path,((player.currentPosition- player.currentPosition)/1000).toInt())
                return
            }
            mapList.forEach {
                click?.pauseListener(path,((player.currentPosition- player.currentPosition)/1000).toInt())
                it.value.pause()
            }
            player.setDataSource(path)
            player.prepare()
            player.start()
            click?.startListener(path,(player.currentPosition/1000).toInt())
            mapList[path] = player
            player.setOnCompletionListener {
                click?.completionListener(path)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun setAudioClick(c:AudioClick):AudioUtlis{
        click=c
        return this
    }

    interface AudioClick{
        fun completionListener(path: String)
        fun startListener(path:String,length:Int)
        fun pauseListener(path:String,length:Int)
    }


    fun stop(path: String) {
        mapList[path]?.stop()
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