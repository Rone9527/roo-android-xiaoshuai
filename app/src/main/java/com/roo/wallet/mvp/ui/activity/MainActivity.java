package com.roo.wallet.mvp.ui.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponentCallback;
import com.core.domain.mine.MessageSystem;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jiameng.mmkv.SharedPref;
import com.roo.core.app.component.Dapp;
import com.roo.core.app.component.Main;
import com.roo.core.app.component.Mine;
import com.roo.core.app.component.Trade;
import com.roo.core.app.component.Upgrade;
import com.roo.core.app.component.Wallet;
import com.roo.core.app.component.WebView;
import com.roo.core.app.constants.Constants;
import com.roo.core.app.constants.EventBusTag;
import com.roo.core.app.constants.GlobalConstant;
import com.roo.core.ui.adapter.FragmentPageAdapter;
import com.roo.core.ui.widget.CustomItemView;
import com.roo.core.ui.widget.DialogLoading;
import com.roo.core.ui.widget.ExViewPager;
import com.roo.core.utils.Kits;
import com.roo.core.utils.LogManage;
import com.roo.core.utils.ResourceUtil;
import com.roo.core.utils.ScreenObserver;
import com.roo.core.utils.ToastUtils;
import com.roo.core.utils.handler.HandlerUtil;
import com.roo.core.utils.utils.MessageSystemManager;
import com.roo.core.utils.utils.TickerManager;
import com.roo.router.Router;
import com.roo.wallet.BuildConfig;
import com.roo.wallet.R;
import com.roo.wallet.app.annotation.AliasType;
import com.roo.wallet.app.receiver.RooJPushMessageReceiver;
import com.roo.wallet.di.component.DaggerMainComponent;
import com.roo.wallet.mvp.contract.MainContract;
import com.roo.wallet.mvp.presenter.MainPresenter;

import org.simple.eventbus.Subscriber;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 首页 页面布局（viewpage+bottom）
 * {@link ExViewPager}
 * {@link PageNavigationView}
 *
 * @see <a href="https://github.com/tyzlmjj/PagerBottomTabStrip"></a>
 */

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {


    private ExViewPager exViewPager;
    private ArrayList<BaseFragment> mFragmentFactory = new ArrayList<>();
    private NavigationController mNavigationController;
    private ScreenObserver mScreenObserver;

    public static void start(Context context, boolean exit) {
        Router.newIntent(context)
                .putBoolean(Main.Param.EXIT, exit)
                .to(MainActivity.class)
                .launch();
    }

    public static void start(Context context, String url) {
        Router.newIntent(context)
                .to(MainActivity.class)
                .putString(Constants.KEY_URL, url)
                .launch();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        boolean exit = intent.getBooleanExtra(Main.Param.EXIT, false);
        String paramId = getParamId();
        if (exit) {
            finish();
        } else if (!TextUtils.isEmpty(paramId)) {
            String type = getMessageType();
            if (Constants.TYPE_MSG_TRADE.equals(type)) {
                mPresenter.getTxDetail(paramId);
            } else if (Constants.TYPE_MSG_SYS.equals(type)) {
                mPresenter.getSysDetail(paramId);
            }
        } else if (!Kits.Empty.check(mFragmentFactory)) {
            exViewPager.setCurrentItem(0);
        }
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ImmersionBar.with(this).hideBar(BarHide.FLAG_HIDE_STATUS_BAR).init();

        exViewPager = findViewById(R.id.view_pager);

        mFragmentFactory.add(CC.obtainBuilder(Wallet.NAME)
                .setActionName(Wallet.Action.WalletFragment)
                .build().call().getDataItemWithNoKey());

        mFragmentFactory.add(CC.obtainBuilder(Trade.NAME)
                .setActionName(Trade.Action.MarketFragment)
                .build().call().getDataItemWithNoKey());

        mFragmentFactory.add(CC.obtainBuilder(Dapp.NAME)
                .setActionName(Dapp.Action.DappFragment)
                .build().call().getDataItemWithNoKey());

        mFragmentFactory.add(CC.obtainBuilder(Mine.NAME)
                .setActionName(Mine.Action.MineFragment)
                .build().call().getDataItemWithNoKey());

        ArrayList<CustomItemView> itemView = new ArrayList<>();

        CustomItemView wallet = getNavBottom(
                R.drawable.ic_home_wallect,
                R.drawable.ic_home_mine_sel,
                getString(R.string.t_wallet),
                "ic_home_wallet_json.json");

        CustomItemView trade = getNavBottom(
                R.drawable.ic_home_trade,
                R.drawable.ic_home_trade_sel,
                getString(R.string.market),
                "ic_home_trade_json.json");

        CustomItemView dapp = getNavBottom(
                R.drawable.ic_home_dapp,
                R.drawable.ic_home_dapp_sel,
                getString(R.string.dapp),
                "ic_home_dapp_json.json");

        CustomItemView mine = getNavBottom(
                R.drawable.ic_home_mine,
                R.drawable.ic_home_wallect_sel,
                getString(R.string.my),
                "ic_home_mine_json.json");

        itemView.add(wallet);
        itemView.add(trade);
        itemView.add(dapp);
        itemView.add(mine);

        PageNavigationView pageBottomTabLayout = findViewById(R.id.page_navigation_view);
        mNavigationController = pageBottomTabLayout.custom()
                .addItem(wallet).addItem(trade).addItem(dapp).addItem(mine)
                .enableAnimateLayoutChanges().build();
        mNavigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                exViewPager.setCurrentItem(index);
                mNavigationController.setSelect(index, false);
            }

            @Override
            public void onRepeat(int index) {

            }
        });

        View tvTest = findViewById(R.id.tvTest);
        tvTest.setVisibility(GlobalConstant.DEBUG_MODEL ? View.VISIBLE : View.INVISIBLE);
        tvTest.setOnClickListener(v -> {
            MessageSystem messageSystem = new MessageSystem();

            String message = "测试消息11111";
            String contentText = "测试消息22222";
            String ticker = contentText;
            String paramId = "1";

            messageSystem.setActivity("1002");
            messageSystem.setMsgId(paramId);
            messageSystem.setPublishTime(System.currentTimeMillis());
            messageSystem.setType(Constants.TYPE_MSG_SYS);
            messageSystem.setReadStatus(false);

            messageSystem.setMsgTitle(contentText);
            messageSystem.setMsgContent(message);

            MessageSystemManager.getInstance().addNotice(messageSystem);

            Intent launchIntent = Objects.requireNonNull(getPackageManager()
                    .getLaunchIntentForPackage(getPackageName()));
            launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            launchIntent.putExtra(Constants.PUSH_EXTRA, paramId);
            launchIntent.putExtra(Constants.PUSH_TYPE, Constants.TYPE_MSG_SYS);
            RooJPushMessageReceiver.openNotification(this, message, contentText, ticker, launchIntent);
        });

        CC.obtainBuilder(Upgrade.NAME)
                .setContext(this)
                .addParam(Upgrade.Param.PACKAGE_NAME, getPackageName())
                .addParam(Upgrade.Param.VERSION_NAME, BuildConfig.VERSION_NAME)
                .addParam(Upgrade.Param.NEVER_IGNORE, true)
                .setActionName(Upgrade.Action.check)
                .build().callAsyncCallbackOnMainThread(new IComponentCallback() {
            @Override
            public void onResult(CC cc, CCResult result) {
            }
        });

        exViewPager.setNoScroll(true);
        exViewPager.setAdapter(new FragmentPageAdapter<>(getSupportFragmentManager(), mFragmentFactory));
        exViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mNavigationController.setSelect(position, false);
            }
        });

        exViewPager.setOffscreenPageLimit(mFragmentFactory.size());

        mScreenObserver = new ScreenObserver(this);
        mScreenObserver.requestScreenStateUpdate(new ScreenObserver.ScreenStateListener() {
            @Override
            public void onScreenOn() {
                if (!ScreenObserver.isApplicationBroughtToBackground(MainActivity.this)) {
                    cancelAlarmManager();
                }
            }

            @Override
            public void onScreenOff() {
                if (!ScreenObserver.isApplicationBroughtToBackground(MainActivity.this)) {
                    cancelAlarmManager();
                    setAlarmManager(GlobalConstant.DEBUG_MODEL ? 5 : 180);
                }
            }
        });

        String paramId = getParamId();
        String type = getMessageType();
        if (!TextUtils.isEmpty(paramId)) {
            if (Constants.TYPE_MSG_TRADE.equals(type)) {
                mPresenter.getTxDetail(paramId);
            } else if (Constants.TYPE_MSG_SYS.equals(type)) {
                mPresenter.getSysDetail(paramId);
            }
        }
    }


    private String getParamId() {
        String id = SharedPref.getString(Constants.PUSH_EXTRA);
        if (TextUtils.isEmpty(id)) {
            id = getIntent().getStringExtra(Constants.PUSH_EXTRA);
        } else {
            SharedPref.remove(Constants.PUSH_EXTRA);
        }
        return id;
    }

    private String getMessageType() {
        String type = SharedPref.getString(Constants.PUSH_TYPE);
        if (TextUtils.isEmpty(type)) {
            type = getIntent().getStringExtra(Constants.PUSH_TYPE);
        } else {
            SharedPref.remove(Constants.PUSH_TYPE);
        }
        return type;
    }

    @Subscriber(tag = EventBusTag.GOTO_WEBVIEW)
    public void onAppAdEvent(HashMap<String, Object> arg) {
        CC.obtainBuilder(WebView.NAME)
                .setActionName(WebView.Action.WebActivity)
                .addParams(arg).build().call();
    }

    @Subscriber(tag = EventBusTag.GOTO_DEX)
    public void onDexAdEvent(String event) {
        exViewPager.setCurrentItem(1);
    }

    @Subscriber(tag = EventBusTag.EVENT_WEBVIEW)
    public void onWebEvent(String event) {
    }

    @Subscriber(tag = EventBusTag.GOTO_BACK)
    public void onGotoBack(String event) {
        exViewPager.setCurrentItem(0);
    }

    @Subscriber(tag = EventBusTag.UPLOAD_JPUSH)
    public void onUploadJpush(String event) {
        mPresenter.updateJpush();
    }

    @Subscriber(tag = EventBusTag.LEGAL_CHANGED)
    public void onLegalChanged(String event) {
        TickerManager.getInstance().getLegal(true);
    }

    private void selectLaunchIcon() {
        if (false) {
            if (AliasType.TYPE_YEAR.equals(SharedPref.getString(AliasType.KEY_ALIAS_TYPE))) {
                return;
            }
            setAliasYear();
        } else {
            if (AliasType.TYPE_DEFAULT.equals(SharedPref.getString(AliasType.KEY_ALIAS_TYPE))) {
                return;
            }
            setDefaultAlias();
        }
    }

    @Override
    public void showLoading() {
        DialogLoading.getInstance().showDialog(this);
    }

    @Override
    public void hideLoading() {
        DialogLoading.getInstance().closeDialog();
    }

    /**
     * 设置默认的别名为启动入口
     */
    public void setDefaultAlias() {
        SharedPref.putString(AliasType.KEY_ALIAS_TYPE, AliasType.TYPE_DEFAULT);
        PackageManager packageManager = getPackageManager();
        packageManager.setComponentEnabledSetting(new ComponentName(this, getPackageName() +
                ".DefaultAlias"), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
        packageManager.setComponentEnabledSetting(new ComponentName(this, getPackageName() +
                ".aliasYear"), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        packageManager.setComponentEnabledSetting(new ComponentName(this, getPackageName() +
                ".NewActivity2"), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

    }

    /**
     * 设置周年庆为启动入口
     */
    public void setAliasYear() {
        SharedPref.putString(AliasType.KEY_ALIAS_TYPE, AliasType.TYPE_YEAR);

        PackageManager packageManager = getPackageManager();
        packageManager.setComponentEnabledSetting(new ComponentName(this,
                        getPackageName() + ".DefaultAlias"),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        packageManager.setComponentEnabledSetting(new ComponentName(this,
                        getPackageName() + ".aliasYear"),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
        packageManager.setComponentEnabledSetting(new ComponentName(this,
                        getPackageName() + ".NewActivity2"),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

    /**
     * 设置预留为启动入口
     */
    public void setAlias(View view) {
        //SharedPref.putString(AliasType.KEY_ALIAS_TYPE,AliasType.TYPE_);

        PackageManager packageManager = getPackageManager();
        packageManager.setComponentEnabledSetting(new ComponentName(this,
                        getPackageName() + ".DefaultAlias"),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        packageManager.setComponentEnabledSetting(new ComponentName(this,
                        getPackageName() + ".aliasYear"),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        packageManager.setComponentEnabledSetting(new ComponentName(this,
                        getPackageName() + ".NewActivity2"),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }

    private CustomItemView getNavBottom(int bottomiconHome, int bottomiconHomeSel, String title, String json) {
        CustomItemView customItemView = new CustomItemView(this);
        customItemView.initialize(bottomiconHome, bottomiconHomeSel, title, json);
        customItemView.setTextCheckedColor(ResourceUtil.getColor(R.color.blue_color));
        customItemView.setTextDefaultColor(ResourceUtil.getColor(R.color.white_color_assist_1));
        return customItemView;
    }

    private Long lastClickTime = -1L;

    @Override
    public void onBackPressed() {
        if (SystemClock.currentThreadTimeMillis() - lastClickTime > 1000) {
            lastClickTime = SystemClock.currentThreadTimeMillis();
            ToastUtils.show(getString(R.string.next_click_exit));
        } else {
            doExit();
        }
    }

    private void doExit() {
        HandlerUtil.runOnUiThreadDelay(this::finish, 200);
    }


    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    public static final String STRATEGY_REBOT = "STRATEGY_REBOT";

    /**
     * 设置定时器管理器
     *
     * @param second 几秒后触发
     */
    private void setAlarmManager(int second) {
        //1秒转换为毫秒数
        long numTimeout = TimeUnit.SECONDS.toMillis(second);
        long triggerAtTime = (System.currentTimeMillis() + numTimeout);
        //保存触发时间
        SharedPref.putLong(MainActivity.STRATEGY_REBOT, triggerAtTime);
        if (triggerAtTime != 0) {
            LogManage.e(MessageFormat.format("----->设置定时器: 触发时间 ： {0}, 当前时间 ： {1}",
                    Kits.Date.getYmdhms(triggerAtTime),
                    Kits.Date.getYmdhms(System.currentTimeMillis())));
        }
    }

    /**
     * 取消定时管理器
     */
    private void cancelAlarmManager() {
        long triggerAtTime = SharedPref.getLong(STRATEGY_REBOT, 0);
        SharedPref.remove(STRATEGY_REBOT);
        if (triggerAtTime != 0 && System.currentTimeMillis() > triggerAtTime) {
            ToastUtils.showDebug("SplashActivity >");
        }
        if (triggerAtTime != 0) {
            LogManage.e(MessageFormat.format("----->取消定时器: TriggerAtTime = {0}, CurrentTimeMillis = {1}, isTrigger = {2}",
                    Kits.Date.getYmdhms(triggerAtTime),
                    Kits.Date.getYmdhms(System.currentTimeMillis()),
                    System.currentTimeMillis() > triggerAtTime));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        cancelAlarmManager();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (ScreenObserver.isApplicationBroughtToBackground(this)) {
            cancelAlarmManager();
            setAlarmManager(GlobalConstant.DEBUG_MODEL ? 6 : 60);
        }
    }

    @Override
    protected void onDestroy() {
        mScreenObserver.stopScreenStateUpdate();
        super.onDestroy();
    }
}