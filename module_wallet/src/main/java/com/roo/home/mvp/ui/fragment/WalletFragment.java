package com.roo.home.mvp.ui.fragment;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aries.ui.view.radius.RadiusTextView;
import com.billy.cc.core.component.CC;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.core.domain.SharePreUtil;
import com.core.domain.link.LinkTokenBean;
import com.core.domain.manager.ChainCode;
import com.core.domain.mine.LegalCurrencyBean;
import com.core.domain.trade.NewAssetsBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.rxbinding2.view.RxView;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DeviceUtils;
import com.lzh.easythread.AsyncCallback;
import com.roo.core.app.annotation.CreateType;
import com.roo.core.app.component.Qrcode;
import com.roo.core.app.constants.Constants;
import com.roo.core.app.constants.EventBusTag;
import com.roo.core.daoManagers.NewAssetsDaoManager;
import com.roo.core.model.UserWallet;
import com.roo.core.utils.ClickUtil;
import com.roo.core.utils.GlobalUtils;
import com.roo.core.utils.Kits;
import com.roo.core.utils.LogManage;
import com.roo.core.utils.SafePassword;
import com.roo.core.utils.ThreadManager;
import com.roo.core.utils.ToastUtils;
import com.roo.core.utils.ViewHelper;
import com.roo.core.utils.handler.HandlerUtil;
import com.roo.core.utils.utils.BalanceManager;
import com.roo.core.utils.utils.EthereumWalletUtils;
import com.roo.core.utils.utils.TickerManager;
import com.roo.home.R;
import com.roo.home.di.component.DaggerWalletComponent;
import com.roo.home.di.module.WalletModule;
import com.roo.home.mvp.contract.WalletContract;
import com.roo.home.mvp.presenter.WalletPresenter;
import com.roo.home.mvp.ui.activity.AssetSelectActivity;
import com.roo.home.mvp.ui.activity.CashierActivity;
import com.roo.home.mvp.ui.activity.SafetyQuestionnaireActivity;
import com.roo.home.mvp.ui.activity.TransferETHActivity;
import com.roo.home.mvp.ui.activity.TransferRecordActivity;
import com.roo.home.mvp.ui.activity.TransferTronActivity;
import com.roo.home.mvp.ui.activity.WalletCreateNormalActivity;
import com.roo.home.mvp.ui.adapter.WalletAdapter;
import com.roo.home.mvp.ui.dialog.AssetOperateDialog;
import com.roo.home.mvp.ui.dialog.ChooseAssetDialog;
import com.roo.home.mvp.ui.dialog.ChooseLinkDialog;
import com.roo.home.mvp.ui.dialog.ChooseWalletDialog;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class WalletFragment extends BaseFragment<WalletPresenter> implements WalletContract.View {
    public static final String DEFAULT = "******";
    public static final String DEFAULT_SHORT = "****";
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    WalletAdapter mAdapter;

    //当前选中的钱包
    private RadiusTextView tvWalletName;
    private View layoutBackupTip;

    private String filterAsset = null;
    private TextView tvTitle;
    private TextView tvBalanceTotal;
    private BigDecimal totalBalance = BigDecimal.ZERO;
    private TextView tvSelectAsset;
    private RecyclerView recyclerView;
    private LinearLayout llRed;
    private List<NewAssetsBean> listMyAssets;

    public static WalletFragment newInstance() {
        WalletFragment fragment = new WalletFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerWalletComponent
                .builder()
                .appComponent(appComponent)
                .walletModule(new WalletModule(this))
                .build()
                .inject(this);

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_wallet_new, container, false);

        int barHeight = DeviceUtils.getStatuBarHeight(requireActivity());
        inflate.findViewById(R.id.titleBar).setPadding(0, barHeight, 0, 0);
        ViewHelper.initRefreshLayout(inflate, refreshLayout -> {
            onLoadedBalance("");
            refreshLayout.finishRefresh(2000);

        });
        tvWalletName = inflate.findViewById(R.id.tvWalletName);
        listMyAssets = new ArrayList<>();
        layoutBackupTip = inflate.findViewById(R.id.layoutBackupTip);
        RxView.clicks(inflate.findViewById(R.id.tvScan)).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {//扫一扫
                    if (null == getUserWallet()) {
                        WalletCreateNormalActivity.start(getActivity());
                    } else {
                        CC.obtainBuilder(Qrcode.NAME)
                                .setContext(requireActivity())
                                .setActionName(Qrcode.Action.QrcodeScanActivity)
                                .build().callAsyncCallbackOnMainThread((cc, result) -> {
                            if (result.isSuccess()) {
                                HandlerUtil.runOnUiThreadDelay(() -> {
                                    String address = result.getDataItem(Qrcode.Result.RESULT);
                                    ChooseAssetDialog.newInstance().setOnClickedListener((item) -> {
                                        startTransferActivity(item, address);

                                    }).show(getChildFragmentManager(), getClass().getSimpleName());
                                }, 200);
                            } else {
                                ToastUtils.show(getString(R.string.toast_not_support_scan_type));
                            }
                        });
                    }
                });
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(requireActivity());
        recyclerView = ViewHelper.initRecyclerView(inflate, mAdapter, mLayoutManager);
        mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

                LinkTokenBean.TokensBean item = Objects.requireNonNull(mAdapter.getItem(position));
                AssetOperateDialog.newInstance()
                        .setOnClickedListener(new AssetOperateDialog.OnClickedListener() {
                            @Override
                            public void onClickTop() {
                                UserWallet userWallet = getUserWallet();
                                int maxSort = 1;
                                for (LinkTokenBean.TokensBean bean : userWallet.getTokenList()) {
                                    maxSort = Math.max(bean.getSort(), maxSort);
                                }
                                for (LinkTokenBean.TokensBean tokensBean : userWallet.getTokenList()) {
                                    if (Objects.equals(tokensBean.getChainCode(), item.getChainCode())
                                            && tokensBean.getSymbol().equalsIgnoreCase(item.getSymbol())) {
                                        tokensBean.setSort(maxSort + 1);
                                        break;
                                    }
                                }
                                EthereumWalletUtils.getInstance().updateWallet(requireActivity(), userWallet, false);
                                onRefresh();
                            }

                            @Override
                            public void onClickHide() {
                                UserWallet userWallet = getUserWallet();
                                if (item.isMain()) {
                                    ToastUtils.show(R.string.toast_main_forbidden_delete);
                                    return;
                                }

                                Iterator<LinkTokenBean.TokensBean> iterator = userWallet.getTokenList().iterator();
                                while (iterator.hasNext()) {
                                    LinkTokenBean.TokensBean next = iterator.next();
                                    if (Objects.equals(next.getChainCode(), item.getChainCode())
                                            && next.getSymbol().equalsIgnoreCase(item.getSymbol())) {
                                        iterator.remove();
                                        break;
                                    }
                                }
                                EthereumWalletUtils.getInstance().updateWallet(requireActivity(), userWallet, false);
                                onRefresh();
                            }

                            @Override
                            public void onDismiss() {

                            }
                        }).show(getChildFragmentManager(), getClass().getSimpleName());

                return true;
            }
        });

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (ClickUtil.clickInFronzen()) {
                return;
            }
            LinkTokenBean.TokensBean item = mAdapter.getItem(position);
            TransferRecordActivity.start(requireActivity(), item);
        });


        String emptyStr = getString(R.string.no_data_asset);
        ViewHelper.initEmptyView(R.layout.layout_empty_view, mAdapter, recyclerView, emptyStr);

        tvBalanceTotal = inflate.findViewById(R.id.tvBalanceTotal);

        ImageView ivSelectAsset = inflate.findViewById(R.id.ivSelectAsset);

        tvSelectAsset = inflate.findViewById(R.id.tvSelectAsset);
        llRed = inflate.findViewById(R.id.llRed);
        ImageView ivVisible = inflate.findViewById(R.id.ivVisible);

        ivVisible.setOnClickListener(v -> {
            if (DEFAULT.equals(Kits.Text.getText(tvBalanceTotal))) {
                mAdapter.setAssetVisible(true);
                tvBalanceTotal.setText(getBalanceDecimal());
                ivVisible.setImageResource(R.drawable.ic_home_asset_visible);
            } else {
                mAdapter.setAssetVisible(false);
                tvBalanceTotal.setText(DEFAULT);
                ivVisible.setImageResource(R.drawable.ic_home_asset_invisible);
            }
        });

        TextView tvTransfer = inflate.findViewById(R.id.tvTransfer);
        TextView tvCashier = inflate.findViewById(R.id.tvCashier);
        TextView tvExchange = inflate.findViewById(R.id.tvExchange);
        tvTitle = inflate.findViewById(R.id.tvTitle);
        RelativeLayout rlAdd = inflate.findViewById(R.id.rlAdd);

        RxView.clicks(tvWalletName).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {//切换钱包
                    if (null == getUserWallet()) {
                        WalletCreateNormalActivity.start(getActivity());
                    } else {
                        ChooseWalletDialog.newInstance().setOnClickedListener((userWallet) -> {
                            EthereumWalletUtils.getInstance().updateWallet(requireActivity(), userWallet, true);

                            filterAsset = null;
                            onAsssetSelected();

                            initDefalutValue();

                            onLoadedBalance("");
                            EventBus.getDefault().post(EventBusTag.NULL_EVENT, EventBusTag.GET_BALANCE);

                        }).show(getChildFragmentManager(), getClass().getSimpleName());
                    }
                });

        RxView.clicks(tvTransfer).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {//转账
                    if (null == getUserWallet()) {
                        WalletCreateNormalActivity.start(getActivity());
                    } else {
                        ChooseAssetDialog.newInstance().setOnClickedListener((item) -> {
                            startTransferActivity(item, "");
                        }).show(getChildFragmentManager(), getClass().getSimpleName());
                    }
                });

        RxView.clicks(tvCashier).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {//收款
                    if (null == getUserWallet()) {
                        WalletCreateNormalActivity.start(getActivity());
                    } else {
                        ChooseAssetDialog.newInstance().setOnClickedListener((item) -> {
                            CashierActivity.start(requireActivity(), item);
                        }).show(getChildFragmentManager(), getClass().getSimpleName());
                    }
                });

        RxView.clicks(tvExchange).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {//闪兑
                    if (null == getUserWallet()) {
                        WalletCreateNormalActivity.start(getActivity());
                    } else {
                        ToastUtils.show(R.string.not_open_yet);
//                        EventBus.getDefault().post(EventBusTag.NULL_EVENT, EventBusTag.GOTO_DEX);
                    }
                });

        RxView.clicks(tvSelectAsset).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {//选择主链
                    onAsssetSelect();
                });
        RxView.clicks(ivSelectAsset).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {//选择主链
                    onAsssetSelect();
                });

        RxView.clicks(rlAdd).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {//添加代币
                    if (null == getUserWallet()) {
                        WalletCreateNormalActivity.start(getActivity());
                    } else {
                        AssetSelectActivity.start(requireActivity(), listMyAssets, true);
                    }
                });


        //滑动事件回调监听（一次滑动的过程一般会连续触发多次）
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int position = mLayoutManager.findFirstVisibleItemPosition();
            }
        });
        initDefalutValue();

        return inflate;
    }

    private String getBalanceDecimal() {
        //return TickerManager.getInstance().getDecimals(totalBalance);
        return totalBalance.setScale(2, RoundingMode.DOWN).toPlainString();
    }

    private void initDefalutValue() {
        mAdapter.setNewData(new ArrayList<>());
        if (!DEFAULT.equals(tvBalanceTotal.getText().toString())) {
            totalBalance = BigDecimal.ZERO;
            tvBalanceTotal.setText(getBalanceDecimal());
        }
    }

    private UserWallet getUserWallet() {
        return EthereumWalletUtils.getInstance().getSelectedWalletOrFirst(getActivity());
    }

    private void onAsssetSelect() {
        if (null == getUserWallet()) {
            WalletCreateNormalActivity.start(getActivity());
        } else {
            ChooseLinkDialog.newInstance(filterAsset).setOnClickListener((item) -> {
                filterAsset = item.getCode();
                onAsssetSelected();
                onRefresh();
            }).show(getChildFragmentManager(), getClass().getSimpleName());
        }
    }

    private void onAsssetSelected() {
        if (filterAsset == null) {
            tvSelectAsset.setText(getString(R.string.all_assets));
            GlobalUtils.setTextViewDrawable(tvSelectAsset, null, Gravity.START);
        } else {
            tvSelectAsset.setText(MessageFormat.format(getString(R.string.format_assets), filterAsset.toUpperCase()));
            Glide.with(this).asDrawable()
                    .apply(RequestOptions.overrideOf(ArmsUtils.dip2px(requireActivity(), 20)))
                    .load(GlobalUtils.getLinkImageUnselect(filterAsset)).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    GlobalUtils.setTextViewDrawable(tvSelectAsset, resource, Gravity.START);
                }
            });
        }
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        UserWallet userWallet = getUserWallet();
        if (null != userWallet) {
            tvWalletName.setText(TextUtils.isEmpty(userWallet.getRemark()) ? getString(R.string.my_wallet) : userWallet.getRemark());
        }
        setLegalUnit();
    }

    @Subscriber(tag = EventBusTag.GET_BALANCE)
    public void getBalance(String event) {
        mPresenter.getBalance();
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void onResume() {
        super.onResume();
        onLoadedBalance("");
        mPresenter.doRefresh(getUserWallet(), filterAsset);
        initBackupTipLayout();
    }

    @Subscriber(tag = EventBusTag.LOADED_BALANCE)
    public void onLoadedBalance(String event) {
        onTotalBlanceChanged();
        onRefresh();
    }

    private void onTotalBlanceChanged() {

        ThreadManager.getCache().async(new Callable<BigDecimal>() {
            @Override
            public BigDecimal call() {
                UserWallet userWallet = getUserWallet();
                if (userWallet == null || Kits.Empty.check(userWallet.getTokenList())) {
                    totalBalance = BigDecimal.ZERO;
                } else {
                    totalBalance = BigDecimal.ZERO;

                    for (LinkTokenBean.TokensBean tokensBean : userWallet.getTokenList()) {
                        BigDecimal balance = BalanceManager.getInstance().get(userWallet, tokensBean);
                        totalBalance = totalBalance.add(TickerManager.getInstance()
                                .getLegalValue(tokensBean.getSymbol(), balance));
                    }
                }
                return totalBalance;
            }
        }, new AsyncCallback<BigDecimal>() {
            @Override
            public void onSuccess(BigDecimal bigDecimal) {
                if (!DEFAULT.equals(tvBalanceTotal.getText().toString())) {
                    tvBalanceTotal.setText(getBalanceDecimal());
                }
            }

            @Override
            public void onFailed(Throwable t) {

            }
        });
    }

    @Subscriber(tag = EventBusTag.LEGAL_CHANGED)
    public void onLegalChanged(String event) {
        setLegalUnit();
        onTotalBlanceChanged();

        mAdapter.notifyDataSetChanged();
    }

    private void setLegalUnit() {
        LegalCurrencyBean selectedLegal = TickerManager.getInstance().getLegal();
        tvTitle.setText(MessageFormat.format(getString(R.string.format_my_assets), selectedLegal.getIcon()));
    }

    private void startTransferActivity(LinkTokenBean.TokensBean tokensBean, String address) {
        switch (tokensBean.getChainCode()) {
            case ChainCode.ETH:
            case ChainCode.BSC:
            case ChainCode.HECO:
            case ChainCode.OEC:
            case ChainCode.POLYGON:
            case ChainCode.FANTOM:
                if (Kits.Empty.check(address)) {
                    TransferETHActivity.start(requireActivity(), tokensBean, TransferETHActivity.FR_WALLET);
                } else {
                    TransferETHActivity.start(requireActivity(), tokensBean, address, TransferETHActivity.FR_WALLET);
                }
                break;
            case ChainCode.BTC:
                LogManage.e(Constants.LOG_STRING, "");
                break;
            case ChainCode.TRON:
                TransferTronActivity.start(requireActivity(), tokensBean, address, TransferTronActivity.FR_WALLET);
                break;

        }
    }


    /**
     * 安全问卷
     */
    private void initBackupTipLayout() {
        boolean finish = SharePreUtil.getBoolean(Objects.requireNonNull(getContext()), Constants.SP_KEY.QUESTION_FINISH, false);
        if (!finish){
            layoutBackupTip.setVisibility(View.VISIBLE);
            RxView.clicks(layoutBackupTip).throttleFirst(1, TimeUnit.SECONDS)
                    .subscribe(o -> {
                        SafetyQuestionnaireActivity.start(requireActivity());
                    });
        }

    }

    private void onRefresh() {
        UserWallet userWallet = getUserWallet();
        if (null == userWallet) {
            mAdapter.setNewData(null);
            tvWalletName.setText(getString(R.string.create_wallet));
        } else {
            tvWalletName.setText(userWallet.getRemark());
            mPresenter.doRefresh(userWallet, filterAsset);
            mPresenter.getAllBalance(requireActivity());
            EventBus.getDefault().post(EventBusTag.NULL_EVENT, EventBusTag.GET_BALANCE);
        }
        initBackupTipLayout();
    }

    @Override
    public void setTokensData(String data) {
        List<NewAssetsBean> listNewAssets = new Gson().fromJson("" + data + "", new TypeToken<List<NewAssetsBean>>() {
        }.getType());
        String name = EthereumWalletUtils.getInstance().getSelectedWalletOrNull(getActivity()).getRemark();

        if (listMyAssets.size() > 0) listMyAssets.clear();
        listMyAssets.addAll(listNewAssets);

        if (Kits.Empty.check(listMyAssets)) {
            llRed.setVisibility(View.GONE);
        } else {
            setRedVisibility(NewAssetsDaoManager.getUnAddedAmount(listNewAssets, name));
        }
    }

    private void setRedVisibility(int unRedAmount) {
        if (unRedAmount > 0) {
            llRed.setVisibility(View.VISIBLE);
        } else {
            llRed.setVisibility(View.GONE);
        }
    }

}
