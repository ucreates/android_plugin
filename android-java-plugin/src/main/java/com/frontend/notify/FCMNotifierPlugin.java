// ======================================================================
// Project Name    : android_plugin
//
// Copyright © 2019 U-CREATES. All rights reserved.
//
// This source code is the property of U-CREATES.
// If such findings are accepted at any time.
// We hope the tips and helpful in developing.
// ======================================================================
package com.frontend.notify;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;
public class FCMNotifierPlugin {
    private static final String CLASS_NAME = FCMNotifierPlugin.class.getSimpleName();
    private static final int HEADS_UP_REQUESTED = 2;
    private static FCMNotifierPlugin instance = null;
    private Context context;
    private FCMNotifierPlugin() { }
    public static FCMNotifierPlugin getInstance() {
        if (null == FCMNotifierPlugin.instance) {
            FCMNotifierPlugin.instance = new FCMNotifierPlugin();
        }
        return FCMNotifierPlugin.instance;
    }
    public void register(final Activity activity) {
        FirebaseInstanceId fcmIId = FirebaseInstanceId.getInstance();
        String fcmToken = fcmIId.getToken();
        if (false != fcmToken.isEmpty()) {
            Log.i(CLASS_NAME, "fcm token is empty. register faild");
        } else {
            Log.i(CLASS_NAME, String.format("fcm token is valid. %s", fcmToken));
        }
        this.context = activity;
        return;
    }
    public void notify(final String title, final String body, final long timeInterval) {
        Intent nextIntent = new Intent(context, context.getClass());
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 777, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        this.notify(title, body, timeInterval, pendingIntent, context);
        return;
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void notify(String title, String body, long timeInterval, PendingIntent intent, final Context context) {
        String packageName = context.getPackageName();
        PackageManager packageManager = context.getPackageManager();
        ApplicationInfo appInfo = null;
        try {
            appInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return;
        }
        int iconId = appInfo.icon;
        Resources resources = context.getResources();
        Bitmap icon = BitmapFactory.decodeResource(resources, iconId);
        long when = System.currentTimeMillis();
        long timeIntervalSecond = timeInterval * 1000;
        Notification.Builder builder = new Notification.Builder(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Bundle bundle = new Bundle();
            bundle.putInt("headsup", FCMNotifierPlugin.HEADS_UP_REQUESTED);
            try {
                Class<?> clazz = builder.getClass();
                Method method = clazz.getMethod("setExtras", Bundle.class);
                method.invoke(builder, bundle);
            } catch (NoSuchMethodException e) {
                return;
            } catch (IllegalArgumentException e) {
                return;
            } catch (IllegalAccessException e) {
                return;
            } catch (InvocationTargetException e) {
                return;
            }
            builder.setAutoCancel(true)
            .setContentTitle(title)
            .setContentText(body)
            .setTicker(title)
            .setWhen(when)
            .setSmallIcon(iconId)
            .setLargeIcon(icon)
            .setContentIntent(intent)
            .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
            .setPriority(Notification.PRIORITY_HIGH)
            .setFullScreenIntent(intent, true)
            .setOngoing(true);
        } else {
            builder.setAutoCancel(true)
            .setContentTitle(title)
            .setContentText(body)
            .setTicker(title)
            .setWhen(when)
            .setSmallIcon(iconId)
            .setLargeIcon(icon)
            .setContentIntent(intent)
            .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
        }
        final Notification notification = builder.build();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                NotificationManagerCompat manager = NotificationManagerCompat.from(context);
                manager.notify(0, notification);
                return;
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, timeIntervalSecond);
        return;
    }
}
