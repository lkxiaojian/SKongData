package debug

import android.os.Bundle
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity
import com.zky.zky_questionnaire.R
import com.zky.zky_questionnaire.fragment.QuestionNaireFragment


class TestMineActivity : RxAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.debug_framelayout)
        supportFragmentManager.beginTransaction().add(R.id.fl, QuestionNaireFragment()).commit()

    }
}