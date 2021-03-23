package debug

import android.os.Bundle
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity
import com.zky.basics.api.RetrofitManager
import com.zky.zky_questionnaire.R
import com.zky.zky_questionnaire.fragment.QuestionNaireFragment


class TestqnActivity : RxAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.debug_framelayout)

        RetrofitManager.TOKEN="1616485755841"
        supportFragmentManager.beginTransaction().add(R.id.fl, QuestionNaireFragment()).commit()

    }
}