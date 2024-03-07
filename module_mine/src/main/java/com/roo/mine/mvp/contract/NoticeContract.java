package com.roo.mine.mvp.contract;

import android.app.Activity;

import com.core.domain.mine.MessageSystem;
import com.core.domain.mine.MessageTrade;
import com.core.domain.mine.TransferRecordBean;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.roo.core.model.base.BaseResponse;

import io.reactivex.Observable;

public interface NoticeContract {
    interface View extends IView {

        void loadData();

        Activity getActivity();

        void gotoTransferDetail(MessageTrade item);

        void gotoMessageSystemDetail(MessageSystem item);
    }

    interface Model extends IModel {

        Observable<BaseResponse<TransferRecordBean.DataBean>> getTxDetail(String chainCode, String toAddr, String index);

        Observable<BaseResponse<MessageSystem.DataBean>> getSystemMessage(String id);
    }
}