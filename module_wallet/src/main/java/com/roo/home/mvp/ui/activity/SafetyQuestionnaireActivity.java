package com.roo.home.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.aries.ui.view.radius.RadiusTextView;
import com.core.domain.SharePreUtil;
import com.core.domain.wallet.QuestionBean;
import com.jakewharton.rxbinding2.view.RxView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.roo.core.app.constants.Constants;
import com.roo.core.app.constants.EventBusTag;
import com.roo.core.ui.dialog.TestSuccessDialog;
import com.roo.core.utils.ToastUtils;
import com.roo.core.utils.ViewHelper;
import com.roo.home.R;
import com.roo.home.di.component.DaggerSafetyQuestionnaireComponent;
import com.roo.home.mvp.contract.SafetyQuestionnaireContract;
import com.roo.home.mvp.presenter.SafetyQuestionnairePresenter;
import com.roo.home.mvp.ui.adapter.QAAdapter;
import com.roo.router.Router;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class SafetyQuestionnaireActivity extends BaseActivity<SafetyQuestionnairePresenter> implements SafetyQuestionnaireContract.View {

    @Inject
    QAAdapter mAdapter;
    private RecyclerView recyclerView;
    private RadiusTextView bottomTextView;
    private LinearLayout llDes;//描述
    private boolean testing;

    public static void start(Context context) {
        Router.newIntent(context)
                .to(SafetyQuestionnaireActivity.class)
                .launch();

    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSafetyQuestionnaireComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_safetyquestionnaire; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ViewHelper.initTitleBar(getString(R.string.safety_questionnaire), this);
        bottomTextView = findViewById(R.id.tv_submit);
        llDes = findViewById(R.id.ll_des);

        recyclerView = ViewHelper.initRecyclerView(mAdapter, SafetyQuestionnaireActivity.this);

        RxView.clicks(bottomTextView).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    if (isAllFinish) {
                        if (isExistErr()) {
                            mAdapter.showAnswer(true);
                            ToastUtils.show(R.string.answer_err_tip);
                        } else {
                            TestSuccessDialog.newInstance().setOnClickListener(this::finish).show(getSupportFragmentManager(), getClass().getSimpleName());
                            SharePreUtil.putBoolean(this,Constants.SP_KEY.QUESTION_FINISH,true);
                        }
                    } else {
                        testing = !testing;
                        switchModel();
                    }

                });


        mPresenter.getQuestionnaire();

    }


    private void switchModel() {
        llDes.setVisibility(testing ? View.GONE : View.VISIBLE);
        recyclerView.setVisibility(testing ? View.VISIBLE : View.GONE);
        bottomTextView.setText(getString(testing ? R.string.submit_answer : R.string.start_testing));
        bottomTextView.setEnabled(testing ? false : true);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void getQuestionnaireSuccess(List<QuestionBean> data) {
        mAdapter.setNewData(data);
    }

    boolean isAllFinish = false;

    private boolean isFinish() {

        List<QuestionBean> data = mAdapter.getData();
        for (int i = 0; i < data.size(); i++) {
            boolean isFinish = data.get(i).isFinish;
            if (!isFinish) {
                isAllFinish = false;
                break;
            } else {
                isAllFinish = true;
            }
        }
        return isAllFinish;
    }

    /**
     * 检测答题错误情况
     *
     * @return
     */
    private boolean isExistErr() {
        boolean isExistErr = false;
        List<QuestionBean> data = mAdapter.getData();
        for (int i = 0; i < data.size(); i++) {
            QuestionBean questionBean = data.get(i);
            List<String> answer = data.get(i).answer;
            //选中的答案
            List answerChose = new ArrayList();
            List<QuestionBean.OptionBean> options = questionBean.options;
            for (int j = 0; j < options.size(); j++) {
                if (options.get(j).checked) {
                    answerChose.add(j + "");
                }
            }
            if (Arrays.equals(answer.toArray(), answerChose.toArray())) {//如果答案一致
                isExistErr = false;
            } else {
                isExistErr = true;
                break;
            }
        }
        return isExistErr;
    }

    @Subscriber(tag = EventBusTag.CHECK_OPTION)
    public void switchModel(HashMap<String, Object> arg) {

        if (isFinish()) {
            bottomTextView.setEnabled(true);
        } else {
        }
    }

}