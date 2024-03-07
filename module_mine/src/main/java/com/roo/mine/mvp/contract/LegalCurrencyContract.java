package com.roo.mine.mvp.contract;

import com.core.domain.mine.LegalCurrencyBean;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.roo.core.model.base.BaseResponse;

import java.util.List;

import io.reactivex.Observable;

public interface LegalCurrencyContract {
    interface View extends IView {

    }

    interface Model extends IModel {

        Observable<BaseResponse<List<LegalCurrencyBean>>> getLegalList();
    }
}