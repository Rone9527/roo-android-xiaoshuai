package com.roo.core.ui.notification;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;


import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.roo.core.R;

import org.jetbrains.annotations.NotNull;


/**
 * https://github.com/ZLOVE320483/Notification
 * private void receivingNotification(Context context, Bundle bundle) {
 * Simple simple = new Simple.Builder().contentText("contentText").subText("subText").build();
 * mNotificationHelper.simpleNotify(context, SplashActivity.class,
 * new NotificationOptions.Builder()
 * .smallIcon(R.mipmap.ic_launcher)
 * .autoCancel(true)
 * .contentInfo("contentInfo")
 * .contentTitle("contentTitle")
 * .number(2)
 * .ticker("ticker")
 * .largeIcon(R.mipmap.ic_launcher)
 * .simple(simple).build());
 * }
 */
public class NotificationHelper extends ContextWrapper {
    private NotificationManager mNotificationManager;
    private String channelId;
    private boolean quiet;

    public NotificationHelper(Context context) {
        this(context, false);
    }

    public NotificationHelper(Context context, boolean quiet) {
        super(context);
        this.quiet = quiet;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channelId = context.getPackageName() + "channel_id";
            String channelName = context.getPackageName() + "channel_name";
            String channelDesc = "this is default channel";

            NotificationChannel channel = getNotificationChannel(
                    quiet,
                    channelName,
                    channelDesc,
                    channelId);
            getNotificationManager().createNotificationChannel(channel);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NotNull
    public static NotificationChannel getNotificationChannel(boolean quiet, String channelName,
                                                             String channelDesc, String channelId) {
        NotificationChannel mNotificationChannel;
        if (quiet) {//静默
            mNotificationChannel = new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW);

        } else {//随系统使用声音或振动
            mNotificationChannel = new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_HIGH);
            mNotificationChannel.enableLights(true);
        }
        mNotificationChannel.setDescription(channelDesc);
        return mNotificationChannel;
    }

    public NotificationManager getNotificationManager() {
        if (mNotificationManager == null) {
            mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mNotificationManager;
    }

    @SuppressLint("WrongConstant")
    public NotificationCompat.Builder getNotificationBuilder(Context context) {
        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new NotificationCompat.Builder(this, channelId);
        } else {
            builder = new NotificationCompat.Builder(this);
        }
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setShowWhen(true);
        builder.setVisibility(Notification.VISIBILITY_PUBLIC);
        if (quiet) {
            builder.setVibrate(new long[]{0L});//不震动
            builder.setSound(Uri.parse("android.resource://com.roo.core/" + R.raw.no_sound));
            builder.setLights(ContextCompat.getColor(context, R.color.blue_color),
                    1000, 5000);
        }
        return builder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void openChannelSetting(String channelId) {
        Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
        intent.putExtra(Settings.EXTRA_CHANNEL_ID, channelId);
        if (getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
            startActivity(intent);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void openNotificationSetting() {
        Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
        if (getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
            startActivity(intent);
        }
    }
}