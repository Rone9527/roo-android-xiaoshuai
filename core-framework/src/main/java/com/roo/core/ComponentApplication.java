package com.roo.core;

import androidx.annotation.CallSuper;

import com.core.domain.dapp.DaoMaster;
import com.core.domain.dapp.DaoSession;
import com.finddreams.languagelib.MultiLanguageUtil;
import com.jess.arms.base.BaseApplication;
import com.jess.arms.utils.ArmsUtils;
import com.roo.core.app.GlobalContext;
import com.roo.core.app.constants.Constants;
import com.roo.core.utils.ProcessUtils;
import com.roo.core.utils.handler.HandlerUtil;
import com.roo.core.websocket.ClientProxy;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.sunchen.netbus.NetStatusBus;

import io.reactivex.plugins.RxJavaPlugins;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/**
 * <pre>
 *     project name: xuetu_android_new
 *     author      : 李琼
 *     create time : 2019-05-29 23:20
 *     desc        : 描述--//ComponentApplication
 * </pre>
 */

public class ComponentApplication extends BaseApplication {
    public static DaoSession daoSession;
    private static RxErrorHandler mRxErrorHandler;

    static {
        SmartRefreshLayout.setDefaultRefreshInitializer((ctx, layout) -> {
            layout.setDisableContentWhenRefresh(true);
        });
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((ctx, layout) -> {
            ClassicsHeader header = new ClassicsHeader(ctx);
            header.setAccentColorId(R.color.white_color_assist_6);
            return header;
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreator((ctx, layout) -> {
            ClassicsFooter footer = new ClassicsFooter(ctx).setDrawableSize(20);
            footer.setAccentColorId(R.color.white_color_assist_6);
            return footer;
        });
    }

    public static RxErrorHandler getRxErrorHandler() {
        return mRxErrorHandler;
    }

    @Override
    @CallSuper
    public void onCreate() {
        GlobalContext.setContext(this);
        super.onCreate();

        if (ProcessUtils.isMainProcess(this)) {
            ClassicsFooter.REFRESH_FOOTER_LOADING = getString(R.string.footer_refreshing);

            initZendesk();

            RxJavaPlugins.setErrorHandler(Throwable::printStackTrace);

            NetStatusBus.getInstance().init(this);

            MultiLanguageUtil.init(this);

            mRxErrorHandler = ArmsUtils.obtainAppComponentFromContext(this).rxErrorHandler();
            initGreenDaoDataBase();
        }
    }

    private void initZendesk() {
//        String appId = "197915512951037953";
//        Zendesk.INSTANCE.init(this, "https://ccmyl.zendesk.com",
//                appId, "mobile_sdk_client_6ec861d275e103ad1996");
//        Chat.INSTANCE.init(this, "zS9YMDAHm0cUfqt3mVtNMuk3LMfKDadA", appId);
    }

    /**
     * 在程序终止时会被调用
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        if (ProcessUtils.isMainProcess(this)) {
            HandlerUtil.removeCallbacksAndMessages();
            ClientProxy.getInstance().close();
        }
    }

    private void initGreenDaoDataBase() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, Constants.DAO_ROO, null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        //获取数据库对象
        daoSession = daoMaster.newSession();
    }
}
