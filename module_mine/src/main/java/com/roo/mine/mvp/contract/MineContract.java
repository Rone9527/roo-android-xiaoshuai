package com.roo.mine.mvp.contract;

import android.app.Activity;

import com.core.domain.mine.SysConfigBean;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.roo.core.model.base.BaseResponse;

import java.util.List;

import io.reactivex.Observable;


public interface MineContract {
    interface View extends IView {

        void getSysConfig(List<SysConfigBean> data);

        Activity getActivity();

        void dotVisibility(boolean dotVisibility);
    }

    interface Model extends IModel {

        Observable<BaseResponse<List<SysConfigBean>>> getSysConfig();
    }
}
