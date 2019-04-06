// ======================================================================
// Project Name    : android_plugin
//
// Copyright © 2019 U-CREATES. All rights reserved.
//
// This source code is the property of U-CREATES.
// If such findings are accepted at any time.
// We hope the tips and helpful in developing.
// ======================================================================
package com.service.notify;
import com.frontend.notify.FCMNotifierPlugin;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
public class FCMNotifierMessageServicePlugin extends FirebaseMessagingService {
    private static final String CLASS_NAME = FCMNotifierMessageServicePlugin.class.getSimpleName();
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        String title = notification.getTitle();
        String body = notification.getBody();
        FCMNotifierPlugin plugin = FCMNotifierPlugin.getInstance();
        plugin.notify(title, body, 0);
        return;
    }
}
