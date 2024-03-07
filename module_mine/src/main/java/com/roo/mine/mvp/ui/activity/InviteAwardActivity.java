package com.roo.mine.mvp.ui.activity;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aries.ui.view.title.TitleBarView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.roo.core.utils.ViewHelper;
import com.roo.mine.R;
import com.roo.mine.di.component.DaggerInviteAwardComponent;
import com.roo.mine.mvp.contract.InviteAwardContract;
import com.roo.mine.mvp.presenter.InviteAwardPresenter;
import com.roo.router.Router;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 *
 */
public class InviteAwardActivity extends BaseActivity<InviteAwardPresenter> implements InviteAwardContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerInviteAwardComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    public static void start(Context context) {
        Router.newIntent(context)
                .to(InviteAwardActivity.class)
                .launch();
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_inviteaward;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        TitleBarView mTitleBarView = ViewHelper.initTitleBar(getString(R.string.activity_center), this);
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

}