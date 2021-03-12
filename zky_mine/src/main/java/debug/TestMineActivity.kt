package debug

import android.os.Bundle
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity
import com.zky.zky_mine.R
import com.zky.zky_mine.fragment.MainMineFragment

class TestMineActivity : RxAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_mine)
        supportFragmentManager.beginTransaction().add(R.id.fl, MainMineFragment()).commit()

    }
}