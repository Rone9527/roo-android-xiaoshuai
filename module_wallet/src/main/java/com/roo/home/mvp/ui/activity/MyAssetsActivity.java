package com.roo.home.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.core.domain.link.LinkTokenBean;
import com.core.domain.manager.ChainCode;
import com.core.domain.trade.MyAssetsBean;
import com.core.domain.trade.NewAssetsBean;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.roo.core.app.constants.Constants;
import com.roo.core.daoManagers.NewAssetsDaoManager;
import com.roo.core.model.UserWallet;
import com.roo.core.ui.widget.DividerItemDecoration;
import com.roo.core.utils.ClickUtil;
import com.roo.core.utils.Kits;
import com.roo.core.utils.LogManage;
import com.roo.core.utils.ToastUtils;
import com.roo.core.utils.ViewHelper;
import com.roo.core.utils.utils.EthereumWalletUtils;
import com.roo.core.utils.utils.TokenManager;
import com.roo.home.R;
import com.roo.home.di.component.DaggerMyAssetsComponent;
import com.roo.home.mvp.contract.MyAssetsContract;
import com.roo.home.mvp.presenter.MyAssetsPresenter;
import com.roo.home.mvp.ui.adapter.MyAssetsAdapter;
import com.roo.router.Router;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 我的资产
 */
public class MyAssetsActivity extends BaseActivity<MyAssetsPresenter> implements MyAssetsContract.View {


    private MyAssetsAdapter myAssetsAdapterNew;
    private MyAssetsAdapter myAssetsAdapterList;
    private List<NewAssetsBean> NewAssetsBeanList;
    private List<NewAssetsBean> listTop;//没添加的
    private List<NewAssetsBean> listBottom;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyAssetsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    public static void start(Context context, List<NewAssetsBean> NewAssetsBeanList) {
        Router.newIntent(context)
                .to(MyAssetsActivity.class)
                .putSerializable(Constants.KEY_DEFAULT, (Serializable) NewAssetsBeanList)
                .launch();
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_myassets; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ViewHelper.initTitleBar(getString(R.string.my_assets), this);
        myAssetsAdapterNew = new MyAssetsAdapter();
        myAssetsAdapterList = new MyAssetsAdapter();
        listTop = new ArrayList<>();//没添加的
        listBottom = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.rylNewAssets);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this));
        recyclerView.setAdapter(myAssetsAdapterNew);

        RecyclerView recyclerViewList = findViewById(R.id.rylList);
        recyclerViewList.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewList.addItemDecoration(new DividerItemDecoration(this));
        recyclerViewList.setAdapter(myAssetsAdapterList);


        setAdapterItemClick(myAssetsAdapterNew);
        setAdapterItemClick(myAssetsAdapterList);

        NewAssetsBeanList = (List<NewAssetsBean>) getIntent().getSerializableExtra(Constants.KEY_DEFAULT);

        String walletName = EthereumWalletUtils.getInstance().getSelectedWalletOrNull(this).getRemark();

        List listOld =new ArrayList();
        for (NewAssetsBean newAssetsBean : NewAssetsBeanList) {
            if (NewAssetsDaoManager.getTokenIsExist(newAssetsBean, walletName) != null) {//数据库已经存在该资产
                NewAssetsBean tokenIsExist = NewAssetsDaoManager.getTokenIsExist(newAssetsBean, walletName);
                tokenIsExist.setAvailableBalance(newAssetsBean.getAvailableBalance());
                NewAssetsBean.TokenChildBean tokenVO = tokenIsExist.getTokenVO();
                if (tokenVO != null) {
                    tokenVO.setMarket(newAssetsBean.getTokenVO().isMarket());
                }
                listOld.add(tokenIsExist);
            } else {//不存在 ，添加
                newAssetsBean.setAdded(true);
                if (Kits.Empty.check(newAssetsBean.getContractId())) {
                    listBottom.add(newAssetsBean);
                } else {
                    listTop.add(newAssetsBean);
                }
            }
        }

        listBottom.addAll(listOld);
        myAssetsAdapterNew.setNewData(listTop);
        myAssetsAdapterList.setNewData(listBottom);
        if (Kits.Empty.check(listTop)) {
            findViewById(R.id.llNewAssets).setVisibility(View.GONE);
        } else {
            findViewById(R.id.llNewAssets).setVisibility(View.VISIBLE);
        }
        MyAssetsBean myAssetsBean = NewAssetsDaoManager.select(walletName);
        if (Kits.Empty.check(myAssetsBean)) {
            myAssetsBean = new MyAssetsBean();
            myAssetsBean.setWalletRemark(EthereumWalletUtils.getInstance().getSelectedWalletOrFirst(this).getRemark());
            NewAssetsDaoManager.insert(myAssetsBean);
        }
        List<NewAssetsBean> listNew = new ArrayList<>();
        listNew.addAll(listBottom);
        listNew.addAll(listTop);
        myAssetsBean.setAllAssets(JSONObject.toJSONString(listNew));
        NewAssetsDaoManager.update(myAssetsBean);
    }

    private void setAdapterItemClick(MyAssetsAdapter myAdapter) {
        myAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (ClickUtil.clickInFronzen()) {
                return;
            }
            if (view.getId() == R.id.ivAssetAdd) {
                NewAssetsBean NewAssetsBean = myAdapter.getItem(position);

                LinkTokenBean.TokensBean tokensBean = new LinkTokenBean.TokensBean();

                tokensBean.setChainCode(NewAssetsBean.getChainCode());
                if (Kits.Empty.check(NewAssetsBean.getContractId())) {
                    tokensBean.setName(NewAssetsBean.getChainCode());
                    tokensBean.setSymbol(NewAssetsBean.getChainCode());
                    tokensBean.setNameEn(NewAssetsBean.getChainCode());
                    tokensBean.setMarket(NewAssetsBean.getTokenVO().isMarket());
                    tokensBean.setDecimals(NewAssetsBean.getChainCode().equals(ChainCode.TRON) ? 6 : 18);
                } else {
                    tokensBean.setName(NewAssetsBean.getTokenVO().getName());
                    tokensBean.setSymbol(NewAssetsBean.getTokenVO().getSymbol());
                    tokensBean.setNameEn(NewAssetsBean.getTokenVO().getSymbol());
                    tokensBean.setContractId(NewAssetsBean.getTokenVO().getContractId());
                    tokensBean.setMarket(NewAssetsBean.getTokenVO().isMarket());
                    tokensBean.setDecimals(NewAssetsBean.getTokenVO().getDecimals());
                }
                addOrDeleteAsset(tokensBean);
                myAssetsAdapterNew.notifyDataSetChanged();
                myAssetsAdapterList.notifyDataSetChanged();
                if (Kits.Empty.check(listTop)) {
                    findViewById(R.id.llNewAssets).setVisibility(View.GONE);
                } else {
                    findViewById(R.id.llNewAssets).setVisibility(View.VISIBLE);
                }
                if (Kits.Empty.check(listBottom)) {
                    findViewById(R.id.llBottom).setVisibility(View.GONE);
                } else {
                    findViewById(R.id.llBottom).setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void addOrDeleteAsset(LinkTokenBean.TokensBean tokensBean) {
        UserWallet userWallet = EthereumWalletUtils.getInstance().getSelectedWalletOrNull(this);

        ArrayList<LinkTokenBean.TokensBean> tokenList = userWallet.getTokenList();
        if (TokenManager.getInstance().isTokenHasSelected(tokensBean)) {
            if (tokensBean.isMain()) {
                ToastUtils.show(R.string.toast_main_forbidden_delete);
                return;
            }
            Iterator<LinkTokenBean.TokensBean> iterator = tokenList.iterator();
            while (iterator.hasNext()) {
                LinkTokenBean.TokensBean next = iterator.next();
                if (Objects.equals(next.getChainCode(), tokensBean.getChainCode())
                        && next.getSymbol().equalsIgnoreCase(tokensBean.getSymbol())) {
                    iterator.remove();
                    break;
                }
            }
        } else {
            tokenList.add(tokensBean);

            ArrayList<LinkTokenBean.NodesBean> chainNode = TokenManager.getInstance().getChainNode();
            if (!Kits.Empty.check(chainNode)) {
                userWallet.setNodes(chainNode);
            }

        }
        userWallet.setTokenList(tokenList);
        EthereumWalletUtils.getInstance().updateWallet(this, userWallet, false);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

//    public void setDataForUI(List<NewAssetsBean> list) {
//        listTop.clear();
//        listBottom.clear();
//        listTop.addAll(NewAssetsManager.getInstance().getUnAddedAssetsList(list));
//        listBottom.addAll(NewAssetsManager.getInstance().getAssetsList());
//
//        myAssetsAdapterNew.setNewData(listTop);
//        myAssetsAdapterList.setNewData(listBottom);
//        if (Kits.Empty.check(listTop)) {
//            findViewById(R.id.llNewAssets).setVisibility(View.GONE);
//        } else {
//            NewAssetsManager.getInstance().resetMyAssetsList(listTop);
//        }
//        if (Kits.Empty.check(listBottom)) {
//            findViewById(R.id.llBottom).setVisibility(View.GONE);
//        }
//    }
}