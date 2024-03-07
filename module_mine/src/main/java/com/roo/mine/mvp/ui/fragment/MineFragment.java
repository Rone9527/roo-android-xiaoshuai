package com.roo.mine.mvp.ui.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aries.ui.view.title.TitleBarView;
import com.billy.cc.core.component.CC;
import com.core.domain.manager.ChainCode;
import com.core.domain.mine.SysConfigBean;
import com.jakewharton.rxbinding2.view.RxView;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.roo.core.app.component.Wallet;
import com.roo.core.app.constants.Constants;
import com.roo.core.app.constants.EventBusTag;
import com.roo.core.app.constants.GlobalConstant;
import com.roo.core.model.UserWallet;
import com.roo.core.ui.dialog.AddLinkHintDialog;
import com.roo.core.utils.ViewHelper;
import com.roo.core.utils.utils.EthereumWalletUtils;
import com.roo.core.utils.utils.MessageTradeManager;
import com.roo.mine.R;
import com.roo.mine.di.component.DaggerMineComponent;
import com.roo.mine.di.module.MineModule;
import com.roo.mine.mvp.contract.MineContract;
import com.roo.mine.mvp.presenter.MinePresenter;
import com.roo.mine.mvp.ui.activity.AboutUsActivity;
import com.roo.mine.mvp.ui.activity.AddressBookActivity;
import com.roo.mine.mvp.ui.activity.InviteActivity;
import com.roo.mine.mvp.ui.activity.NoticeActivity;
import com.roo.mine.mvp.ui.activity.SettingActivity;

import org.simple.eventbus.EventBus;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class MineFragment extends BaseFragment<MinePresenter> implements MineContract.View {

    private View inflate;
    private TitleBarView mTitleBarView;
    private ImageView ivRoundOrange;
    private boolean dotVisibility;

    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMineComponent
                .builder()
                .appComponent(appComponent)
                .mineModule(new MineModule(this))
                .build()
                .inject(this);

    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_mine, container, false);
        mTitleBarView = ViewHelper.initTitleBar(getString(R.string.my), inflate, this);
        ivRoundOrange = inflate.findViewById(R.id.ivRoundOrange);
        RxView.clicks(inflate.findViewById(R.id.layoutWallet))
                .throttleFirst(1, TimeUnit.SECONDS).subscribe(o -> {
            CC.obtainBuilder(Wallet.NAME)
                    .setContext(requireActivity())
                    .setActionName(Wallet.Action.WalletListActivity)
                    .build().call();
        });
        RxView.clicks(inflate.findViewById(R.id.layoutContact))
                .throttleFirst(1, TimeUnit.SECONDS).subscribe(o -> {
            AddressBookActivity.start(requireActivity());
        });

        RxView.clicks(inflate.findViewById(R.id.layoutSetting))
                .throttleFirst(1, TimeUnit.SECONDS).subscribe(o -> {
            SettingActivity.start(requireActivity());
        });
        RxView.clicks(inflate.findViewById(R.id.layoutInvite))
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> InviteActivity.start(requireActivity()));

        TextView tvDebugInfo = inflate.findViewById(R.id.tvDebugInfo);
        tvDebugInfo.setVisibility(GlobalConstant.DEBUG_MODEL ? View.VISIBLE : View.GONE);

        String rpcType = GlobalConstant.RPC_TYPE_MAIN ? "主网RPC" : "测试RPC";
        String serverType = GlobalConstant.SERVER_TYPE ? "生产服公网" : "公司局域网";
        tvDebugInfo.setText(MessageFormat.format("RPC地址：{0}\n服务器类型：{1}", rpcType, serverType));

        mPresenter.getSysConfig();

        return inflate;
    }

    @Override
    public void onResume() {
        super.onResume();

        TitleBarView.ImageAction action;
        if (MessageTradeManager.getInstance().hasNoticeNotRead()) {
            action = mTitleBarView.new ImageAction(R.drawable.ic_mine_notice_hint,
                    v -> NoticeActivity.start(requireActivity()));
        } else {
            action = mTitleBarView.new ImageAction(R.drawable.ic_mine_notice,
                    v -> NoticeActivity.start(requireActivity()));
        }
        mTitleBarView.getLinearLayout(Gravity.END).removeAllViews();
        mTitleBarView.addRightAction(action);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    public static final String HELP_CENTER_URL = "HELP_CENTER_URl";
    public static final String ACTIVITY_CENTER = "ACTIVITY_CENTER";
    public static final String ABOUT_US_URL = "ABOUT_US_URL";
    public static final String USE_THE_TUTORIAL = "USE_THE_TUTORIAL";

    @Override
    public void getSysConfig(List<SysConfigBean> data) {

        for (SysConfigBean item : data) {
            switch (item.getCode()) {
                case ACTIVITY_CENTER: {
                    RxView.clicks(inflate.findViewById(R.id.layoutInviteAward))
                            .throttleFirst(1, TimeUnit.SECONDS).subscribe(o -> {

                        UserWallet userWallet = EthereumWalletUtils.getInstance()
                                .getSelectedWalletOrNull(requireActivity());
                        String chainCode = ChainCode.BSC;
                        if (!userWallet.getListMainChainCode().contains(chainCode)) {
                            AddLinkHintDialog.newInstance(chainCode).setOnClickListener(() -> {
                                CC.obtainBuilder(Wallet.NAME)
                                        .setContext(requireActivity())
                                        .setActionName(Wallet.Action.AssetSelectActivity)
                                        .build().call();
                            }).show(getChildFragmentManager(), getClass().getSimpleName());
                            return;
                        }
                        UserWallet.ChainWallet chainWallet = EthereumWalletUtils.getInstance()
                                .getWalletByChainCode(userWallet, chainCode);
                        String address = Constants.PREFIX_16 + chainWallet.getWalletFile().getAddress();


                        SysConfigBean.ValueBean valueBean = item.getValue().get(0);
                        HashMap<String, Object> param = new HashMap<>();
                        //eg: https://wap.roo.top/invation-active/{chainCode}/{地址}?platform=android
                        String url = MessageFormat.format("{0}{1}/{2}?platform={3}",
                                valueBean.getValue(),
                                chainCode, address, "android");
                        param.put(Constants.KEY_URL, url);
                        EventBus.getDefault().post(param, EventBusTag.GOTO_WEBVIEW);
                    });
                }
                break;
                case HELP_CENTER_URL: {

                    RxView.clicks(inflate.findViewById(R.id.layoutHelper))
                            .throttleFirst(1, TimeUnit.SECONDS).subscribe(o -> {
                        SysConfigBean.ValueBean valueBean = item.getValue().get(0);
                        HashMap<String, Object> param = new HashMap<>();
                        param.put(Constants.KEY_URL, valueBean.getValue());
                        param.put(Constants.KEY_TITLE, valueBean.getName());
                        EventBus.getDefault().post(param, EventBusTag.GOTO_WEBVIEW);
                    });

                }
                break;
                case ABOUT_US_URL: {
                    RxView.clicks(inflate.findViewById(R.id.layoutAbout))
                            .throttleFirst(1, TimeUnit.SECONDS).subscribe(o -> {
                        AboutUsActivity.start(requireActivity(), item, dotVisibility);
                    });
                }
                break;
                case USE_THE_TUTORIAL: {

                    RxView.clicks(inflate.findViewById(R.id.layoutCourse))
                            .throttleFirst(1, TimeUnit.SECONDS).subscribe(o -> {
                        HashMap<String, Object> param = new HashMap<>();
                        SysConfigBean.ValueBean valueBean = item.getValue().get(0);
                        param.put(Constants.KEY_URL, valueBean.getValue());
                        param.put(Constants.KEY_TITLE, valueBean.getName());
                        EventBus.getDefault().post(param, EventBusTag.GOTO_WEBVIEW);
                    });
                }
                break;
            }
        }
    }

    @Override
    public void dotVisibility(boolean dotVisibility) {
        this.dotVisibility = dotVisibility;
        ivRoundOrange.setVisibility(dotVisibility ? View.VISIBLE : View.GONE);
    }
}