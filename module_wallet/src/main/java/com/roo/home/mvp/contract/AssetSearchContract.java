package com.roo.home.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.roo.core.model.base.BaseResponse;
import com.core.domain.mine.WalletBean;

import java.util.List;

import io.reactivex.Observable;

public interface AssetSearchContract {
    interface View extends IView {

    }

    interface Model extends IModel {

        Observable<BaseResponse<List<WalletBean>>> getWalletList();

        Observable<List<WalletBean>> getWallet(String content);

        Observable<BaseResponse> onTokenAdd(String chainCode, String address, String contractId);
    }
}