package com.roo.dapp.mvp.presenter;

import com.core.domain.dapp.DappBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.roo.core.daoManagers.DappDaoManager;
import com.roo.dapp.mvp.contract.DappFavoriteContract;
import com.roo.dapp.mvp.ui.adapter.DappFavoriteAdapter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

@ActivityScope
public class DappFavoritePresenter extends BasePresenter<DappFavoriteContract.Model, DappFavoriteContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    DappFavoriteAdapter mAdapter;

    @Inject
    public DappFavoritePresenter(DappFavoriteContract.Model model, DappFavoriteContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }

    public void getDappTop(int type) {
        List<DappBean> list = DappDaoManager.select(type);
        Collections.sort(list, new Comparator<DappBean>() {
            @Override
            public int compare(DappBean o1, DappBean o2) {
                return (int) (o2.getDataBaseCreateTime() - o1.getDataBaseCreateTime());
            }
        });
        mAdapter.setNewData(list);
    }
}