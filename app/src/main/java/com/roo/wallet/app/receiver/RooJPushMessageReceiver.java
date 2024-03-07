package com.roo.wallet.app.receiver;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;

import androidx.core.app.NotificationCompat;

import com.alibaba.fastjson.JSONObject;
import com.core.domain.dapp.JpushUpload;
import com.core.domain.mine.MessageSystem;
import com.core.domain.mine.MessageTrade;
import com.google.gson.Gson;
import com.jiameng.mmkv.SharedPref;
import com.roo.core.app.RetryWithDelay;
import com.roo.core.app.api.ApiService;
import com.roo.core.app.constants.Constants;
import com.roo.core.model.base.BaseResponse;
import com.roo.core.ui.notification.NotificationHelper;
import com.roo.core.utils.Kits;
import com.roo.core.utils.LogManage;
import com.roo.core.utils.RetrofitFactory;
import com.roo.core.utils.RxUtils;
import com.roo.core.utils.utils.AddressManager;
import com.roo.core.utils.utils.MessageSystemManager;
import com.roo.core.utils.utils.MessageTradeManager;
import com.roo.wallet.R;
import com.roo.wallet.WalletApplication;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Objects;

import cn.jpush.android.api.CmdMessage;
import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

public class RooJPushMessageReceiver extends JPushMessageReceiver {

    private static final String TAG = RooJPushMessageReceiver.class.getSimpleName();

    @Override
    public void onMessage(Context context, CustomMessage msg) {
        LogManage.i(">>startNotification()>>" + "msg = [" + msg + "]");

        //{
        //     messageId='20266430282633495',
        //     extra='xxxx',
        //     message='您已到账',
        //     contentType='',
        //     title='',
        //     senderId='2e9d8ec86d7e2a2502f98182',
        //     appId='com.roo.wallet',
        //     platform='0'
        //}


        JSONObject jsonObject = JSONObject.parseObject(msg.extra);
        String type = jsonObject.getString("type");
        String message = "";
        String contentText = "";
        String ticker = "";
        String paramId = "";

        if (Constants.TYPE_MSG_TRADE.equals(type)) {
            MessageTrade messageTrade = new Gson().fromJson(msg.extra, MessageTrade.class);

            if (AddressManager.getInstance().isAddressExpired(messageTrade.getToAddr())) {
                //地址已经过期 通知服务器删除该地址

                ArrayList<JpushUpload.SubAddrListBean> options = new ArrayList<>();
                JpushUpload.SubAddrListBean item = new JpushUpload.SubAddrListBean();
                item.setAddress(messageTrade.getToAddr());
                item.setChainCode(messageTrade.getChainCode());
                options.add(item);

                RxUtils.transform(RetrofitFactory.getRetrofit(ApiService.class).unsubJpush(options))
                        .retryWhen(new RetryWithDelay())
                        .subscribe(new ErrorHandleSubscriber<BaseResponse>(WalletApplication.getRxErrorHandler()) {
                            @Override
                            public void onNext(BaseResponse t) {
                            }
                        });
                return;
            }

            messageTrade.setId(messageTrade.getTxId() + messageTrade.getIndex());
            MessageTradeManager.getInstance().addNotice(messageTrade);

            message = MessageFormat.format(context.getString(R.string.tip_receive_cash),
                    messageTrade.getAmount(), messageTrade.getToken());
            contentText = messageTrade.getAlert();
            ticker = messageTrade.getAlert();
            paramId = messageTrade.getId();

        } else if (Constants.TYPE_MSG_SYS.equals(type)) {
            MessageSystem messageSystem = new Gson().fromJson(msg.extra, MessageSystem.class);
            MessageSystemManager.getInstance().addNotice(messageSystem);

            message = msg.message;
            contentText = msg.title;
            ticker = msg.title;
            paramId = messageSystem.getMsgId();

            messageSystem.setMsgTitle(ticker);
            messageSystem.setMsgContent(message);
        }


        if (Kits.Empty.check(message)
                || Kits.Empty.check(contentText)
                || Kits.Empty.check(ticker)
                || Kits.Empty.check(paramId)) {
            return;
        }
        Intent launchIntent = Objects.requireNonNull(context.getPackageManager()
                .getLaunchIntentForPackage(context.getPackageName()));
        launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        launchIntent.putExtra(Constants.PUSH_EXTRA, paramId);
        launchIntent.putExtra(Constants.PUSH_TYPE, type);
        openNotification(context, message, contentText, ticker, launchIntent);
    }

    public static void openNotification(Context context,
                                        String message,
                                        String contentText,
                                        String ticker,
                                        Intent launchIntent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder builder = notificationHelper.getNotificationBuilder(context);


        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setTicker(ticker)
                .setContentText(message)
                .setSubText(contentText)
                .setContentTitle(ticker)
                .setOngoing(false)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        //最后一个参数 launchIntent, PendingIntent.FLAG_UPDATE_CURRENT，不然每次拿到的值都是第一次拿到的，不会更新
        PendingIntent pIntent = PendingIntent.getActivity(context, 1, launchIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pIntent);
        Notification notification = builder.build();
        int notifyId = (int) (System.currentTimeMillis() / 1000);
        notificationHelper.getNotificationManager().notify(notifyId, notification);
    }

    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage message) {
        LogManage.i(TAG, "[onNotifyMessageOpened] " + message);
    }

    @Override
    public void onMultiActionClicked(Context context, Intent intent) {
        LogManage.i(TAG, "[onMultiActionClicked] 用户点击了通知栏按钮");
    }

    @Override
    public void onNotifyMessageArrived(Context context, NotificationMessage message) {
        LogManage.i(TAG, "[onNotifyMessageArrived] " + message);
    }

    @Override
    public void onNotifyMessageDismiss(Context context, NotificationMessage message) {
        LogManage.i(TAG, "[onNotifyMessageDismiss] " + message);
    }

    @Override
    public void onRegister(Context context, String registrationId) {
        LogManage.i(TAG, "[onRegister] " + registrationId);
        SharedPref.putString(Constants.JPUSH_REGISTER_ID, registrationId);
    }

    @Override
    public void onConnected(Context context, boolean isConnected) {
        LogManage.i(TAG, "[onConnected] " + isConnected);
    }

    @Override
    public void onCommandResult(Context context, CmdMessage cmdMessage) {
        LogManage.i(TAG, "[onCommandResult] " + cmdMessage);
    }

    @Override
    public void onTagOperatorResult(Context context, JPushMessage jPushMessage) {
        LogManage.i(TAG, ">>onTagOperatorResult()>>" + "context = [" + context + "], jPushMessage = [" + jPushMessage + "]");
    }

    @Override
    public void onCheckTagOperatorResult(Context context, JPushMessage jPushMessage) {
        LogManage.i(TAG, ">>onCheckTagOperatorResult()>>" + "context = [" + context + "], jPushMessage = [" + jPushMessage + "]");
    }

    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        LogManage.i(TAG, ">>onAliasOperatorResult()>>" + "context = [" + context + "], jPushMessage = [" + jPushMessage + "]");

    }

    @Override
    public void onMobileNumberOperatorResult(Context context, JPushMessage jPushMessage) {
        LogManage.i(TAG, ">>onMobileNumberOperatorResult()>>" + "context = [" + context + "], jPushMessage = [" + jPushMessage + "]");

    }

    @Override
    public void onNotificationSettingsCheck(Context context, boolean isOn, int source) {
        super.onNotificationSettingsCheck(context, isOn, source);
        LogManage.i(TAG, "[onNotificationSettingsCheck] isOn:" + isOn + ",source:" + source);
    }

}
