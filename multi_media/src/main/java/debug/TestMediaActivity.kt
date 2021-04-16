package debug

import android.content.Intent
import android.os.Bundle
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity
import com.zky.multi_media.R
import com.zky.multi_media.fragment.MediaImageFragment
import com.zky.multi_media.fragment.MediaVideoFragment
import com.zky.multi_media.fragment.MediaVoiceFragment

class TestMediaActivity : RxAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_media)
//        MediaImageFragment()  MediaVoiceFragment()
        val mediaVideoFragment = MediaVideoFragment()
        supportFragmentManager.beginTransaction().add(R.id.fl,  MediaVideoFragment() ).commit()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (supportFragmentManager.fragments.size > 0) {
            val fragments = supportFragmentManager.fragments
            fragments.forEach {
                it.onActivityResult(requestCode, resultCode, data)
            }
        }

    }
}