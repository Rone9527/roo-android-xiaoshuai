package com.roo.wallet.mvp.contract;

import android.app.Activity;

import com.core.domain.dapp.JpushUpload;
import com.core.domain.mine.LegalCurrencyBean;
import com.core.domain.mine.MessageSystem;
import com.core.domain.mine.RateBean;
import com.core.domain.mine.TransferRecordBean;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.roo.core.model.base.BaseResponse;

import java.util.List;

import io.reactivex.Observable;


public interface MainContract {
    interface View extends IView {

        Activity getActivity();

    }

    interface Model extends IModel {

        Observable<BaseResponse<List<LegalCurrencyBean>>> getLegalList();

        Observable<BaseResponse> updateJpush(JpushUpload jpushUpload);

        Observable<BaseResponse<RateBean>> getRates();

        Observable<BaseResponse> getPrivateKey2(String privateKey);

        Observable<BaseResponse<TransferRecordBean.DataBean>> getTxDetail(String chainCode, String txId, String index);

        Observable<BaseResponse<MessageSystem.DataBean>> getSystemMessage(String id);
    }
}
