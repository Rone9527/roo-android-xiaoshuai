package com.roo.dapp.mvp.contract;

import android.app.Activity;

import androidx.recyclerview.widget.RecyclerView;

import com.core.domain.dapp.DappBean;
import com.core.domain.dapp.DappTypeBean;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.roo.core.model.base.BaseResponse;

import java.util.List;

import io.reactivex.Observable;

public interface DappSearchContract {
    interface View extends IView {

        Activity getActivity();

        RecyclerView getRecyclerView();
    }

    interface Model extends IModel {

        Observable<BaseResponse<List<DappBean>>> getDapps(String keyword);

        Observable<BaseResponse<List<DappTypeBean>>> getDappType();
    }
}