// ======================================================================
// Project Name    : android_plugin
//
// Copyright © 2019 U-CREATES. All rights reserved.
//
// This source code is the property of U-CREATES.
// If such findings are accepted at any time.
// We hope the tips and helpful in developing.
// ======================================================================
package com.service.notify
import com.frontend.notify.FCMNotifierPlugin
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
open class FCMNotifierMessageServicePlugin : FirebaseMessagingService() {
    override fun onMessageReceived(p0: RemoteMessage?) {
        super.onMessageReceived(p0)
        val notification :RemoteMessage.Notification? = p0?.notification
        val title :String? = notification?.title
        val body :String? = notification?.body
        FCMNotifierPlugin.notify(title, body, 0)
        return
    }
}