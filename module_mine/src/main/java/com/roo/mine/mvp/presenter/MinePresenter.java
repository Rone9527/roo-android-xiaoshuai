package com.roo.mine.mvp.presenter;

import android.app.Activity;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponentCallback;
import com.core.domain.mine.SysConfigBean;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.roo.core.app.component.Upgrade;
import com.roo.core.model.base.BaseResponse;
import com.roo.core.utils.Kits;
import com.roo.core.utils.RxUtils;
import com.roo.mine.mvp.contract.MineContract;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


@FragmentScope
public class MinePresenter extends BasePresenter<MineContract.Model, MineContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public MinePresenter(MineContract.Model model, MineContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }

    public void getSysConfig() {
        mModel.getSysConfig().compose(RxUtils.applySchedulersLife(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<SysConfigBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull BaseResponse<List<SysConfigBean>> t) {
                        mRootView.getSysConfig(t.getData());
                    }
                });

        Activity activity = mRootView.getActivity();
        String versionName = Kits.Package.getVersionName(activity);

        CC.obtainBuilder(Upgrade.NAME)
                .setContext(activity)
                .addParam(Upgrade.Param.PACKAGE_NAME, activity.getPackageName())
                .addParam(Upgrade.Param.VERSION_NAME, versionName)
                .addParam(Upgrade.Param.NEVER_IGNORE, true)
                .setActionName(Upgrade.Action.versionNew)
                .build().callAsyncCallbackOnMainThread(new IComponentCallback() {
            @Override
            public void onResult(CC cc, CCResult result) {
                if (result.isSuccess()) {
                    Object paramItem = result.getDataItem(Upgrade.Result.RESULT);
                    if (paramItem instanceof Boolean){
                        mRootView.dotVisibility((Boolean) paramItem);
                    }
                } else {
                    mRootView.dotVisibility(false);
                }
            }
        });
    }

}
