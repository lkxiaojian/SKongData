package debug

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity
import com.zky.basics.api.RetrofitManager
import com.zky.basics.common.constant.Constants.itemCode
import com.zky.zky_map.R
import com.zky.zky_map.fragment.MapFragment

class TestMapActivity : RxAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.debug_framelayout)
        RetrofitManager.TOKEN="1616142538367"
        itemCode="27093798379061255"

        supportFragmentManager.beginTransaction().add(R.id.fl, MapFragment()).commit()
    }
}