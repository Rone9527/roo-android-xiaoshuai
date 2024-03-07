package com.roo.mine.mvp.ui.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.aries.ui.view.title.TitleBarView;
import com.billy.cc.core.component.CC;
import com.core.domain.mine.AppUpdateInfo;
import com.huantansheng.easyphotos.utils.bitmap.BitmapUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.roo.core.app.RetryWithDelay;
import com.roo.core.app.api.ApiService;
import com.roo.core.app.component.Qrcode;
import com.roo.core.model.base.BaseResponse;
import com.roo.core.ui.dialog.ShareDialog;
import com.roo.core.utils.RxUtils;
import com.roo.core.utils.ToastUtils;
import com.roo.core.utils.ViewHelper;
import com.roo.mine.R;
import com.roo.mine.di.component.DaggerInviteComponent;
import com.roo.mine.mvp.contract.InviteContract;
import com.roo.mine.mvp.presenter.InvitePresenter;
import com.roo.router.Router;

import org.jetbrains.annotations.NotNull;

import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 邀请好友
 */

public class InviteActivity extends BaseActivity<InvitePresenter> implements InviteContract.View {

    private boolean onShare;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerInviteComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    public static void start(Context context) {
        Router.newIntent(context)
                .to(InviteActivity.class)
                .launch();
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_invite;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        TitleBarView mTitleBarView = ViewHelper.initTitleBar(getString(R.string.invite_friend), this);
        mTitleBarView.addRightAction(mTitleBarView.new ImageAction(getDrawable(R.drawable.ic_invite_share),
                v -> {
                    View view = findViewById(R.id.layoutContent);
                    Bitmap bitmap = BitmapUtils.createBitmapFromView(view);
                    ShareDialog.newInstance(bitmap).setOnClickListener(() -> {
                        onShare = true;
                    }).show(getSupportFragmentManager(), getClass().getSimpleName());
                }));
        RelativeLayout layoutQrcode = findViewById(R.id.layoutQrcode);
        ImageView ivBitmap = findViewById(R.id.ivBitmap);

        AppComponent appComponent = ArmsUtils.obtainAppComponentFromContext(this);

        appComponent.repositoryManager().obtainRetrofitService(ApiService.class)
                .getAppNewVersion()
                .retryWhen(new RetryWithDelay()).compose(RxUtils.applySchedulers())
                .subscribe(new ErrorHandleSubscriber<BaseResponse<AppUpdateInfo>>(appComponent.rxErrorHandler()) {
                    @Override
                    public void onNext(@NotNull BaseResponse<AppUpdateInfo> iModel) {
                        CC.obtainBuilder(Qrcode.NAME)
                                .setContext(InviteActivity.this)
                                .addParam(Qrcode.Param.Text, iModel.getData().getLinkUrl())
                                .setActionName(Qrcode.Action.ContentEncode)
                                .build().callAsyncCallbackOnMainThread((cc, result) -> {
                            if (result.isSuccess()) {
                                Bitmap bitmap = result.getDataItem(Qrcode.Result.RESULT);
                                ivBitmap.setImageBitmap(bitmap);
                                layoutQrcode.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (onShare) {
            ToastUtils.show(getString(R.string.share_success));
            onShare = false;
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }
}