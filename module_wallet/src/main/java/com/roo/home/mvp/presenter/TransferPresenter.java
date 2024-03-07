package com.roo.home.mvp.presenter;

import com.alibaba.fastjson.JSON;
import com.core.domain.link.LinkTokenBean;
import com.core.domain.mine.BlockHeader;
import com.core.domain.mine.GasBean;
import com.core.domain.mine.TransactionResult;
import com.core.domain.trade.TransferPointBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.roo.core.app.RetryWithDelay;
import com.roo.core.app.api.ApiService;
import com.roo.core.model.base.BaseResponse;
import com.roo.core.utils.LogManage;
import com.roo.core.utils.RetrofitFactory;
import com.roo.core.utils.RxUtils;
import com.roo.core.utils.utils.Web3jUtil;
import com.roo.home.mvp.contract.TransferContract;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Callable;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

@ActivityScope
public class TransferPresenter extends BasePresenter<TransferContract.Model, TransferContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public TransferPresenter(TransferContract.Model model, TransferContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }
    //获取私钥2
    public void getPrivateKey2(String privateKey) {
        RxUtils.transform(RetrofitFactory.getRetrofit(ApiService.class).getPrivateKey2(privateKey))
                .retryWhen(new RetryWithDelay())
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse t) {
                        LogManage.e(JSON.toJSONString(t));
                        String jsonStr = t.getData().toString();
                        mRootView.startTransfer(jsonStr);
                    }

                    @Override
                    public void onError(@NotNull Throwable t) {
                        super.onError(t);
                        LogManage.e(JSON.toJSONString(t.getMessage()));
                    }
                });
    }

    public void getGasDataSet(String chainCode) {
        mModel.getGasDataSet(chainCode).compose(RxUtils.applySchedulersLife(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<GasBean>>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull BaseResponse<GasBean> t) {
                        mRootView.getGasDataSet(t.getData());
                    }
                });
    }

    public void getUnit(String address, LinkTokenBean.TokensBean tokensBean) {
        RxUtils.makeObservable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return Web3jUtil.getInstance().getDecimal(tokensBean.isMain() ? "" : address, tokensBean.getChainCode(), tokensBean.getContractId());
            }
        }).compose(RxUtils.applySchedulersLife(mRootView))
                .subscribe(new ErrorHandleSubscriber<String>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull String unit) {
                        mRootView.setUnit(unit);
                    }
                });
    }

    public void submitTransfer(TransferPointBean transferPointBean) {
        RxUtils.transform(RetrofitFactory.getRetrofit(ApiService.class).submitTransfer(transferPointBean))
                .retryWhen(new RetryWithDelay())
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse t) {
                        LogManage.e(JSON.toJSONString(t));
                    }

                    @Override
                    public void onError(@NotNull Throwable t) {
                        super.onError(t);
                        LogManage.e(JSON.toJSONString(t.getMessage()));
                    }
                });

    }

}