package com.roo.upgrade;

import com.azhon.appupdate.config.UpdateConfiguration;
import com.azhon.appupdate.listener.OnButtonClickListener;
import com.azhon.appupdate.manager.DownloadManager;
import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jiameng.mmkv.SharedPref;
import com.roo.core.app.RetryWithDelay;
import com.roo.core.app.api.ApiService;
import com.roo.core.app.component.Upgrade;
import com.roo.core.app.constants.GlobalConstant;
import com.roo.core.model.base.BaseResponse;
import com.roo.core.utils.GlobalUtils;
import com.roo.core.utils.LogManage;
import com.roo.core.utils.RxUtils;
import com.roo.upgrade.app.utils.SimpleOnDownloadListener;
import com.core.domain.mine.AppUpdateInfo;

import org.jetbrains.annotations.NotNull;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


public class UpgradeManger {

    public static final String UPDATE = "8001";
    public static final String NO_NEW_VERSION = "0";
    public static final String CANCEL = "-8001";
    public static final String ERROR_NET = "-9002";

    private final CC cc;
    private final String packageName;

    private final RxErrorHandler mErrorHandler;
    private final AppComponent appComponent;

    public UpgradeManger(CC cc) {
        appComponent = ArmsUtils.obtainAppComponentFromContext(cc.getContext());
        mErrorHandler = appComponent.rxErrorHandler();
        this.cc = cc;
        this.packageName = cc.getParamItem(Upgrade.Param.PACKAGE_NAME);
    }

    public static UpgradeManger newInstance(CC cc) {
        return new UpgradeManger(cc);
    }

    /**
     * 升级接口
     *
     * @param action 检查更新还是获取是否有新版本(小红点检测)
     */
    public void requestCheckUpdate(String action) {
        appComponent.repositoryManager().obtainRetrofitService(ApiService.class)
                .getAppNewVersion()
                .retryWhen(new RetryWithDelay()).compose(RxUtils.applySchedulers())
                .subscribe(new ErrorHandleSubscriber<BaseResponse<AppUpdateInfo>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<AppUpdateInfo> iModel) {
                        responseCheckUpdate(iModel.getData(), action);
                    }

                    @Override
                    public void onError(@NotNull Throwable t) {
                        if (action.equals(Upgrade.Action.versionNew)) {
                            CC.sendCCResult(cc.getCallId(), CCResult.success(Upgrade.Result.RESULT, false));
                        } else {
                            CC.sendCCResult(cc.getCallId(), CCResult.success(Upgrade.Result.RESULT, ERROR_NET));
                        }
                    }
                });
    }

    private void responseCheckUpdate(AppUpdateInfo updateBean, String action) {
        String currentVersion = cc.getParamItem(Upgrade.Param.VERSION_NAME);
        if (isIgnored(updateBean, cc.getParamItem(Upgrade.Param.NEVER_IGNORE, false))
                && GlobalUtils.compareVersion(currentVersion, updateBean.getVersion()) < 0) {

            if (action.equals(Upgrade.Action.versionNew)) {
                CC.sendCCResult(cc.getCallId(), CCResult.success(Upgrade.Result.RESULT, true));
                return;
            }

            //处于WiFi状态下则自动下载
            //NetworkUtils.getNetType() == NetType.WIFI

            UpdateConfiguration conf = new UpdateConfiguration();
            String downloadUrl = updateBean.getDownloadUrl();
            if (cc.getParamItem(Upgrade.Param.DOWNLOAD_DIRECTLY, false)) {
                conf.setOnDownloadListener(new SimpleOnDownloadListener() {
                    @Override
                    public void start() {
                        CC.sendCCResult(cc.getCallId(), CCResult.success(Upgrade.Result.RESULT, UPDATE));
                    }
                });
                DownloadManager.getInstance(cc.getContext()).setApkName(packageName.concat(".apk"))
                        .setApkUrl(downloadUrl).setSmallIcon(R.mipmap.ic_launcher)
                        .setConfiguration(conf).download();
            } else {
                conf.setDialogImage(R.drawable.ic_dialog_default)
                        .setJumpInstallPage(true)
                        .setEnableLog(GlobalConstant.DEBUG_MODEL)
                        .setForcedUpgrade(isConstraints(updateBean, currentVersion))
                        .setButtonClickListener(id -> {
                            LogManage.e(String.valueOf(id));
                            switch (id) {
                                case OnButtonClickListener.UPDATE:
                                    CC.sendCCResult(cc.getCallId(), CCResult.success(Upgrade.Result.RESULT, UPDATE));
                                    break;
                                case OnButtonClickListener.CANCEL:
                                    SharedPref.putString(Upgrade.Param.NEVER_IGNORE, updateBean.getVersion());
                                    CC.sendCCResult(cc.getCallId(), CCResult.success(Upgrade.Result.RESULT, CANCEL));
                                    break;
                                default:
                                    break;
                            }
                        });
                DownloadManager.getInstance(cc.getContext())
                        .setApkName(System.currentTimeMillis() + packageName.concat(".apk"))
                        .setApkUrl(downloadUrl)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setConfiguration(conf)
                        .setApkVersionCode(Integer.MAX_VALUE)
                        .setApkVersionName(updateBean.getVersion())
                        .setApkDescription(updateBean.getRemark().replace("&", "\n\n"))
                        .download();
            }
        } else {
            if (action.equals(Upgrade.Action.versionNew)) {
                CC.sendCCResult(cc.getCallId(), CCResult.success(Upgrade.Result.RESULT, false));
            } else {
                CC.sendCCResult(cc.getCallId(), CCResult.success(Upgrade.Result.RESULT, NO_NEW_VERSION));
            }
        }
    }

    public boolean isConstraints(AppUpdateInfo updateBean, String currentVersion) {
        return updateBean.getType() == 1 || GlobalUtils.compareVersion(
                currentVersion, updateBean.getLowVersion()) < 0;
    }

    public boolean isIgnored(AppUpdateInfo updateBean, Boolean neverIgnore) {
        String ignoreVersion = SharedPref.getString(Upgrade.Param.NEVER_IGNORE);
        return neverIgnore || !updateBean.getVersion().equals(ignoreVersion);
    }

    public void downloadDirectly(String downloadUrl) {
        String apkName = packageName.concat(".apk");
        DownloadManager.getInstance(cc.getContext())
                .setApkName(apkName)
                .setApkUrl(downloadUrl)
                .setApkVersionCode(Integer.MAX_VALUE)
                .setSmallIcon(R.mipmap.ic_launcher).setConfiguration(new UpdateConfiguration()
                .setOnDownloadListener(new SimpleOnDownloadListener() {
                    @Override
                    public void start() {
                        CC.sendCCResult(cc.getCallId(), CCResult.success(Upgrade.Result.RESULT, UPDATE));
                    }
                })).download();
    }
}
