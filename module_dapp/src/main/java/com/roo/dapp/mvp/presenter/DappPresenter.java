package com.roo.dapp.mvp.presenter;

import com.core.domain.dapp.DappBannerBean;
import com.core.domain.dapp.DappBean;
import com.core.domain.dapp.DappTypeBean;
import com.google.gson.Gson;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jiameng.mmkv.SharedPref;
import com.roo.core.app.constants.Constants;
import com.roo.core.daoManagers.DappDaoManager;
import com.roo.core.model.base.BaseResponse;
import com.roo.core.utils.LogManage;
import com.roo.core.utils.RxUtils;
import com.roo.dapp.mvp.contract.DappContract;
import com.roo.dapp.mvp.ui.adapter.DappAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


@FragmentScope
public class DappPresenter extends BasePresenter<DappContract.Model, DappContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    DappAdapter mAdapter;

    public static final int PER_LINE_COUNT = 5;

    private List<DappTypeBean> data;

    @Inject
    public DappPresenter(DappContract.Model model, DappContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
    }

    public void getDappTop() {
        RxUtils.makeObservable(new Callable<List<DappBean>>() {
            @Override
            public List<DappBean> call() throws Exception {
                List<DappBean> list = DappDaoManager.select(mRootView.getSelectTopIndex());
                Collections.sort(list, new Comparator<DappBean>() {
                    @Override
                    public int compare(DappBean o1, DappBean o2) {
                        return (int) (o2.getDataBaseCreateTime() - o1.getDataBaseCreateTime());
                    }
                });

                if (list.size() > PER_LINE_COUNT) {
                    list.subList(PER_LINE_COUNT, list.size()).clear();
                }
                return list;
            }
        }).compose(RxUtils.applySchedulersLife(mRootView))
                .subscribe(new ErrorHandleSubscriber<List<DappBean>>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull List<DappBean> t) {
                        mRootView.getAdapterFavRecent().setNewData(t);
                    }
                });
    }


    public void getDappsHot() {
        mModel.getDappsHot().compose(RxUtils.applySchedulersLife(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<DappBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull BaseResponse<List<DappBean>> t) {
                        mRootView.getAdapterHot().setNewData(t.getData());
                    }
                });
    }

    public void getDappType() {
        mModel.getDappType().compose(RxUtils.applySchedulersLife(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<DappTypeBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull BaseResponse<List<DappTypeBean>> t) {
                        backupAllDapp(t);
                        data = t.getData();
                        DappTypeBean dappTypeBean = data.get(0);
                        dappTypeBean.setSelect(true);

                        List<DappTypeBean.ListBean> dappTypeList = dappTypeBean.getList();
                        DappTypeBean.ListBean listBean = dappTypeList.get(0);
                        listBean.setSeletct(true);
                        mRootView.getDappTypeSuccess(t);

                        mAdapter.setNewData(listBean.getList());


//                        dappTypeListBean = new Gson().fromJson(response, DappTypeListBean.class);
//                        Log.e(TAG, dappTypeListBean.data.size() + "");
//                        DappTypeBean.ListBean dataBean = new DappTypeBean.ListBean();
//
//                        List<DappTypeBean.ListBean> list = new ArrayList<>();
//                        DappTypeListBean.DataBean.ListBeanX beanX = new DappTypeListBean.DataBean.ListBeanX();
//                        beanX.name = "借贷";
//                        list.add(beanX);
//                        dataBean.name = "TRON";
//                        dataBean.list = list;
//                        List<DappTypeListBean.DataBean.ListBeanX.ListBean> beanList = new ArrayList<>();
//                        DappTypeListBean.DataBean.ListBeanX.ListBean bean = new DappTypeListBean.DataBean.ListBeanX.ListBean();
//                        bean.name = "just";
//                        bean.icon = "https://just.tronscan.org/favicon.ico";
//                        bean.type = "借贷";
//                        bean.chain="TRON";
//                        bean.discription="JUST 旨在建立一个公平、去中心化的金融系统，为全球用户提供稳定币借贷和治理机制。JUST 是一个双代币系统。第一个代币 USDJ 是一种以 1:1 的比率与美元挂钩的稳定币，它是通过 JUST 的 CDP 门户通过抵押 TRX 生成的。第二代币JST可用于支付利息、平台维护、投票参与治理等JUST平台上的活动。";
//                        bean.links = "https://just.tronscan.org/#/home";
//                        beanList.add(bean);
//                        beanX.list = beanList;
//
//                        dappTypeListBean.data.add(dataBean);
                    }
                });
    }

    private void backupAllDapp(@NotNull BaseResponse<List<DappTypeBean>> t) {
        ArrayList<DappBean> dataSet = new ArrayList<>();
        for (DappTypeBean datum : t.getData()) {
            for (DappTypeBean.ListBean listBean : datum.getList()) {
                dataSet.addAll(listBean.getList());
            }
        }
        SharedPref.putString(Constants.KEY_OBJ_DAPP_LIST, new Gson().toJson(dataSet));
    }

    public void getDappBottom(int type) {
        for (DappTypeBean dappTypeBean : data) {
            if (dappTypeBean.isSelect()) {
                mAdapter.setNewData(dappTypeBean.getList().get(type).getList());
                break;
            }
        }
    }

    public DappBean updateDapp(DappBean dappBean) {
        for (DappBean bean : mAdapter.getData()) {
            if (bean.getName().equals(dappBean.getName())) {
                DappDaoManager.syn(bean);
                return bean;
            }
        }
        return dappBean;
    }

    public void getDappBanner() {
        mModel.getDappBanner(1).compose(RxUtils.applySchedulersLife(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<DappBannerBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(@NotNull BaseResponse<List<DappBannerBean>> t) {
                        mRootView.getBannerData(t.getData());
                    }

                    @Override
                    public void onError(@NotNull Throwable t) {
                        super.onError(t);
                        LogManage.e(Constants.LOG_STRING + "-ffjdsljfojfpsdjfj1" + t.getMessage());
                    }
                });
    }
}
