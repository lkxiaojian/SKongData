package com.zky.zky_questionnaire.activity

import ARouterPath.GROUP_QUESTION
import ARouterPath.QUESTION_ACTIVITY
import com.alibaba.android.arouter.facade.annotation.Route
import com.zky.basics.common.constant.Constants.taskName
import com.zky.basics.common.mvvm.BaseActivity
import com.zky.zky_questionnaire.R
import com.zky.zky_questionnaire.fragment.QuestionNaireFragment

@Route(path = QUESTION_ACTIVITY, group = GROUP_QUESTION)
class QuestionNaireActivity : BaseActivity() {

    override fun onBindLayout() = R.layout.activity_question_naire

    override fun initView() {
        supportFragmentManager.beginTransaction().add(R.id.fl, QuestionNaireFragment()).commit()
    }

    override fun initData() {

    }

    override val tootBarTitle="$taskName"

}