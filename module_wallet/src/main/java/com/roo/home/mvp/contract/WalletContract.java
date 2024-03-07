package com.roo.home.mvp.contract;

import androidx.fragment.app.FragmentActivity;

import com.core.domain.trade.AllBalanceBean;
import com.core.domain.trade.BalanceBean;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.roo.core.model.base.BaseResponse;
import io.reactivex.Observable;

import java.util.ArrayList;
import java.util.List;



public interface WalletContract {
    interface View extends IView {

        FragmentActivity getActivity();

        void setTokensData(String dataS);
    }

    interface Model extends IModel {
        Observable<BaseResponse<List<BalanceBean>>> getBalance(String chainCode, String address, ArrayList<String> contractId);

        Observable<BaseResponse> getAllBalance(ArrayList<AllBalanceBean> dtos);

    }
}
