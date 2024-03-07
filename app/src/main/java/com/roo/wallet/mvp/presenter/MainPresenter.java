package com.roo.wallet.mvp.presenter;


import android.text.TextUtils;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.billy.cc.core.component.CC;
import com.core.domain.dapp.JpushUpload;
import com.core.domain.link.LinkTokenBean;
import com.core.domain.manager.ChainCode;
import com.core.domain.mine.LegalCurrencyBean;
import com.core.domain.mine.MessageSystem;
import com.core.domain.mine.MessageTrade;
import com.core.domain.mine.RateBean;
import com.core.domain.mine.TransferRecordBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jiameng.mmkv.SharedPref;
import com.lzh.easythread.AsyncCallback;
import com.roo.core.app.component.Mine;
import com.roo.core.app.component.Wallet;
import com.roo.core.app.constants.Constants;
import com.roo.core.model.UserWallet;
import com.roo.core.model.base.BaseResponse;
import com.roo.core.utils.LogManage;
import com.roo.core.utils.RxUtils;
import com.roo.core.utils.ThreadManager;
import com.roo.core.utils.utils.AddressManager;
import com.roo.core.utils.utils.EthereumWalletUtils;
import com.roo.core.utils.utils.MessageSystemManager;
import com.roo.core.utils.utils.MessageTradeManager;
import com.roo.wallet.mvp.contract.MainContract;
import com.subgraph.orchid.encoders.Hex;

import org.jetbrains.annotations.NotNull;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.utils.Numeric;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.jpush.android.api.JPushInterface;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.ResponseBody;
import wallet.core.jni.Base58;
import wallet.core.jni.CoinType;
import wallet.core.jni.HDWallet;


@ActivityScope
public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public MainPresenter(MainContract.Model model, MainContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void getLegalList() {
        if (SharedPref.getObject(Constants.KEY_OBJ_LEGAL_CURRENT) == null) {
            mModel.getLegalList().compose(RxUtils.applySchedulersLife(mRootView))
                    .subscribe(new ErrorHandleSubscriber<BaseResponse<List<LegalCurrencyBean>>>(mErrorHandler) {
                        @Override
                        public void onNext(@NotNull BaseResponse<List<LegalCurrencyBean>> t) {
                            for (LegalCurrencyBean item : t.getData()) {
                                if (item.getSymbol().equals(LegalCurrencyBean.USD)) {
                                    SharedPref.putObject(Constants.KEY_OBJ_LEGAL_CURRENT, item);
                                    break;
                                }
                            }
                        }
                    });
        }
    }
    public String getPrivateKeyToAddress(String privateKey) {
        String address = Keys.toChecksumAddress(Keys.getAddress(ECKeyPair.create(Numeric.toBigInt(privateKey))));
        byte[] decode = Hex.decode(address.replace(Constants.PREFIX_16, "41"));
        return Base58.encode(decode);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void updateJpush() {
        String registerId = JPushInterface.getRegistrationID(mRootView.getActivity());
        if (TextUtils.isEmpty(registerId)) {
            registerId = SharedPref.getString(Constants.JPUSH_REGISTER_ID);
        }
        if (TextUtils.isEmpty(registerId)) {
            return;
        }

        JpushUpload jpushUpload = new JpushUpload();
        jpushUpload.setJpushId(registerId);
        jpushUpload.setDeviceType("android");

        ThreadManager.getCache().async(() -> {
            ArrayList<JpushUpload.SubAddrListBean> subAddrList = new ArrayList<>();
            for (UserWallet userWallet : EthereumWalletUtils.getInstance().getWallet(mRootView.getActivity())) {
                for (LinkTokenBean.TokensBean tokensBean : userWallet.getTokenList()) {
                    JpushUpload.SubAddrListBean item = new JpushUpload.SubAddrListBean();
                    item.setChainCode(tokensBean.getChainCode());
                    item.setWalletName(userWallet.getRemark());
                    String address = "";
                    switch (tokensBean.getChainCode()) {
                        case ChainCode.TRON:
                            if (userWallet.getPrivateKey() != null) {
                                address = getPrivateKeyToAddress(userWallet.getPrivateKey());
                            } else {
                                HDWallet hdWallet = new HDWallet(userWallet.getMnemonics(), "");
                                address = hdWallet.getAddressForCoin(CoinType.TRON);
                            }
                            break;
                        default:
                            address = Constants.PREFIX_16 + EthereumWalletUtils.getInstance()
                                    .getWalletByChainCode(userWallet, tokensBean.getChainCode()).getWalletFile().getAddress();
                            break;
                    }
                    item.setAddress(address);

                    subAddrList.add(item);
                }
            }
            jpushUpload.setSubAddrList(subAddrList);

            return jpushUpload;
        }, new AsyncCallback<JpushUpload>() {
            @Override
            public void onSuccess(JpushUpload jpushUpload) {

                AddressManager.getInstance().setSubAddrList(jpushUpload.getSubAddrList());

                mModel.updateJpush(jpushUpload).compose(RxUtils.applySchedulersLife(mRootView))
                        .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                            @Override
                            public void onNext(@NotNull BaseResponse t) {
                            }
                        });
            }

            @Override
            public void onFailed(Throwable t) {

            }
        });

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void getRate() {
        mModel.getRates().compose(RxUtils.applySchedulersLife(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<RateBean>>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull BaseResponse<RateBean> t) {
                        SharedPref.putObject(Constants.KEY_OBJ_RATE, t.getData());
                    }
                });
    }

    //获取私钥2
    public void getPrivateKey2(String privateKey) {
        mModel.getPrivateKey2(privateKey).compose(RxUtils.applySchedulersLife(mRootView)).subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
            @Override
            public void onNext(@NotNull BaseResponse t) {
                SharedPref.putObject(Constants.KEY_PRIVATE2, t.getData());
            }
        });
    }

    public void getTxDetail(String id) {
        MessageTrade item = MessageTradeManager.getInstance().getNoticeById(id);
        if (item == null) {
            return;
        }
        mModel.getTxDetail(item.getChainCode(), item.getTxId(), item.getIndex()).compose(RxUtils.applySchedulersLife(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<TransferRecordBean.DataBean>>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull BaseResponse<TransferRecordBean.DataBean> t) {
                        TransferRecordBean.DataBean data = t.getData();

                        item.setDataBean(data);
                        MessageTradeManager.getInstance().replaceNotice(item);

                        CC.obtainBuilder(Wallet.NAME)
                                .setContext(mRootView.getActivity())
                                .addParam(Wallet.Param.TransferRecordDataBean, data)
                                .addParam(Constants.KEY_CHAIN_CODE, item.getChainCode())
                                .addParam(Constants.KEY_FR, true)
                                .setActionName(Wallet.Action.TransferDetailActivity)
                                .build().call();
                    }
                });
    }

    public void getSysDetail(String id) {
        MessageSystem item = MessageSystemManager.getInstance().getNoticeById(id);
        if (item == null) {
            return;
        }
        mModel.getSystemMessage(id).compose(RxUtils.applySchedulersLife(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<MessageSystem.DataBean>>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull BaseResponse<MessageSystem.DataBean> t) {
                        MessageSystem.DataBean data = t.getData();

                        item.setDataBean(data);
                        CC.obtainBuilder(Mine.NAME)
                                .setContext(mRootView.getActivity())
                                .addParam(Mine.Param.MessageSystemDataBean, data)
                                .setActionName(Mine.Action.NoticeDetailActivity)
                                .build().call();
                    }
                });
    }
}
