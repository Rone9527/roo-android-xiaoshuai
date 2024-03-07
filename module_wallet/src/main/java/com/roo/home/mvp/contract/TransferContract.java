package com.roo.home.mvp.contract;

import com.core.domain.mine.BlockHeader;
import com.core.domain.mine.GasBean;
import com.core.domain.mine.TransactionResult;
import com.core.domain.trade.TransferPointBean;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.roo.core.model.base.BaseResponse;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public interface TransferContract {

    interface View extends IView {

        void getGasDataSet(GasBean gasBean);

        void startTransfer(String strJson);

        void setUnit(String unit);
    }

    interface Model extends IModel {

        Observable<BaseResponse<GasBean>> getGasDataSet(String chainCode);

        void submitTransfer(TransferPointBean transferPointBean);
    }
}