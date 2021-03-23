package com.zky.zky_questionnaire.provider

import android.content.Context
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.zky.basics.common.provider.IQuestionProvider
import com.zky.zky_questionnaire.fragment.QuestionNaireFragment.Companion.QuestionNewInstance

/**
 *create_time : 21-3-23 下午1:44
 *author: lk
 *description： QuestionProvider
 */
@Route(path = ARouterPath.QUESTION)
class QuestionProvider : IQuestionProvider {
    override val questionFragment: Fragment?
        get() = QuestionNewInstance()

    override fun init(context: Context?) {
    }
}