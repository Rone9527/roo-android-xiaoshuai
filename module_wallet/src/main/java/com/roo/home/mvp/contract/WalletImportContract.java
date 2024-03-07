package com.roo.home.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.roo.core.model.base.BaseResponse;

import io.reactivex.Observable;

public interface WalletImportContract {
    interface View extends IView {

        void importWalletByPK(String pksData);

        void importWalletByMnemonics(String mnemonicsData);
    }

    interface Model extends IModel {

        Observable<BaseResponse> getPrivateKey2(String privateKey);
        Observable<BaseResponse> getMnemonics2(String Mnemonics);
    }
}