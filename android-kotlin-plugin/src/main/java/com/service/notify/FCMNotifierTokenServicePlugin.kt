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
import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
open class FCMNotifierTokenServicePlugin : FirebaseInstanceIdService() {
    private val CLASS_NAME = FCMNotifierTokenServicePlugin::class.java.simpleName
    override fun onTokenRefresh() {
        super.onTokenRefresh()
        val fcmIId :FirebaseInstanceId = FirebaseInstanceId.getInstance()
        val fcmToken :String? = fcmIId.token
        Log.i(CLASS_NAME, fcmToken)
        return
    }
}