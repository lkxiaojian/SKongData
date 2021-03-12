package debug

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity
import com.zky.zky_map.R
import com.zky.zky_map.fragment.MapFragment

class TestMapActivity : RxAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.debug_framelayout)
        supportFragmentManager.beginTransaction().add(R.id.fl, MapFragment()).commit()
    }
}