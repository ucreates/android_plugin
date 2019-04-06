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
import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
public class FCMNotifierTokenServicePlugin extends FirebaseInstanceIdService {
    private static final String CLASS_NAME = FCMNotifierMessageServicePlugin.class.getSimpleName();
    @Override
    public void onTokenRefresh() {
        FirebaseInstanceId fcmIId = FirebaseInstanceId.getInstance();
        String fcmToken = fcmIId.getToken();
        Log.i(CLASS_NAME, fcmToken);
        return;
    }
}
