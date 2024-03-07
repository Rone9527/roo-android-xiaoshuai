package com.roo.wallet.mvp.presenter;



import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.core.domain.link.LinkTokenBean;
import com.core.domain.link.MainToken;
import com.core.domain.manager.ChainCode;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jiameng.mmkv.SharedPref;
import com.roo.core.app.RetryWithDelay;
import com.roo.core.app.api.ApiService;
import com.roo.core.app.constants.Constants;
import com.roo.core.model.base.BaseResponse;
import com.roo.core.utils.Kits;
import com.roo.core.utils.RetrofitFactory;
import com.roo.core.utils.RxUtils;
import com.roo.core.utils.utils.TokenManager;
import com.roo.wallet.WalletApplication;
import com.roo.wallet.mvp.contract.SplashContract;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


@ActivityScope
public class SplashPresenter extends BasePresenter<SplashContract.Model, SplashContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public SplashPresenter(SplashContract.Model model, SplashContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void getLinkListCoin() {
        if (Kits.Empty.check(TokenManager.getInstance().getLink())) {
            RxUtils.transform(RetrofitFactory.getRetrofit(ApiService.class).getLinkListCoin())
                    .retryWhen(new RetryWithDelay())
                    .subscribe(new ErrorHandleSubscriber<BaseResponse<List<LinkTokenBean>>>(WalletApplication.getRxErrorHandler()) {
                        @Override
                        public void onNext(BaseResponse<List<LinkTokenBean>> t) {
                            List<LinkTokenBean> data = t.getData();
                            for (LinkTokenBean tokenBean : data) {
                                List<LinkTokenBean.TokensBean> tokens = tokenBean.getTokens();
                                if (tokenBean.getCode().equalsIgnoreCase(ChainCode.ETH)) {
                                    tokens.add(0, LinkTokenBean.TokensBean.valueOf(MainToken.ETH));
                                } else if (tokenBean.getCode().equalsIgnoreCase(ChainCode.BSC)) {
                                    tokens.add(0, LinkTokenBean.TokensBean.valueOf(MainToken.BSC));
                                } else if (tokenBean.getCode().equalsIgnoreCase(ChainCode.HECO)) {
                                    tokens.add(0, LinkTokenBean.TokensBean.valueOf(MainToken.HECO));
                                } else if (tokenBean.getCode().equalsIgnoreCase(ChainCode.OEC)) {
                                    tokens.add(0, LinkTokenBean.TokensBean.valueOf(MainToken.OEC));
                                } else if (tokenBean.getCode().equalsIgnoreCase(ChainCode.TRON)) {
                                    tokens.add(0, LinkTokenBean.TokensBean.valueOf(MainToken.TRON));
                                } else if (tokenBean.getCode().equalsIgnoreCase(ChainCode.POLYGON)) {
                                    tokens.add(0, LinkTokenBean.TokensBean.valueOf(MainToken.POLYGON));
                                } else if (tokenBean.getCode().equalsIgnoreCase(ChainCode.FANTOM)) {
                                    tokens.add(0, LinkTokenBean.TokensBean.valueOf(MainToken.FANTOM));
                                }
                                SharedPref.putString(Constants.KEY_OBJ_LINK_TOKEN, new Gson().toJson(data));
                            }
                        }
                    });
        }
    }

}