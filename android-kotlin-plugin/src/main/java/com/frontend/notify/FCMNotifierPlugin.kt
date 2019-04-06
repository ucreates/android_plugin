// ======================================================================
// Project Name    : android_plugin
//
// Copyright © 2019 U-CREATES. All rights reserved.
//
// This source code is the property of U-CREATES.
// If such findings are accepted at any time.
// We hope the tips and helpful in developing.
// ======================================================================
package com.frontend.notify
import android.app.Activity
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.support.v4.app.NotificationManagerCompat
import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.*
open class FCMNotifierPlugin {
    companion object {
        private val CLASS_NAME = FCMNotifierPlugin::class.java.simpleName
        private val HEADS_UP_REQUESTED : Int = 2
        private val instance : FCMNotifierPlugin = FCMNotifierPlugin()
        private lateinit var context : Context
        init {
        }
        fun getInstance() :FCMNotifierPlugin {
            return FCMNotifierPlugin.instance;
        }
        fun register(activity:Activity) {
            val fcmIId:FirebaseInstanceId = FirebaseInstanceId.getInstance()
            val fcmToken :String? = fcmIId.token
            if (false != fcmToken.isNullOrEmpty()) {
                Log.i(CLASS_NAME, "fcm token is empty. register faild")
            } else {
                Log.i(CLASS_NAME, String.format("fcm token is valid. %s", fcmToken))
            }
            this.context = activity
            return
        }
        fun notify(title :String?, body:String?, timeInterval:Long) {
            val nextIntent : Intent = Intent(this.context, this.context.javaClass)
            val pendingIntent : PendingIntent = PendingIntent.getActivity(context, 777, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            this.notify(title, body, timeInterval, pendingIntent, context)
            return
        }
        private fun notify(title :String?, body:String?, timeInterval:Long,intent:PendingIntent,context:Context) {
            val packageName :String = context.packageName
            val packageManager :PackageManager = context.packageManager
            val appInfo:ApplicationInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
            val iconId :Int = appInfo.icon
            val resources :Resources = context.resources
            val icon : Bitmap = BitmapFactory.decodeResource(resources, iconId)
            val whenTime :Long = System.currentTimeMillis()
            val timeIntervalSecond : Long = timeInterval * 1000
            val builder : Notification.Builder = Notification.Builder(context)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                try {
                    val bundle : Bundle = Bundle()
                    bundle.putInt("headsup", FCMNotifierPlugin.HEADS_UP_REQUESTED)
                    val clazz : Class<Notification.Builder> = builder.javaClass
                    val method : Method = clazz.getMethod("setExtras", Bundle::class.java)
                    method.invoke(builder, bundle)
                } catch (e:NoSuchMethodException) {
                } catch (e:IllegalArgumentException) {
                } catch (e:IllegalAccessException) {
                } catch (e: InvocationTargetException) {
                }
                builder.setAutoCancel(true)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setTicker(title)
                    .setWhen(whenTime)
                    .setSmallIcon(iconId)
                    .setLargeIcon(icon)
                    .setContentIntent(intent)
                    .setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setFullScreenIntent(intent, true)
                    .setOngoing(true)
            } else {
                builder.setAutoCancel(true)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setTicker(title)
                    .setWhen(whenTime)
                    .setSmallIcon(iconId)
                    .setLargeIcon(icon)
                    .setContentIntent(intent)
                    .setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE)
            }
            val notification :Notification = builder.build()
            val task = object : TimerTask() {
                override fun run() {
                    val manager = NotificationManagerCompat.from(context)
                    manager.notify(0, notification)
                    return
                }
            }
            val timer :Timer = Timer()
            timer.schedule(task, timeIntervalSecond)
            return
        }
    }
}