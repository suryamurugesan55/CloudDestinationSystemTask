package com.surya.grocerytask.utils

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity

private const val NOTIFICATION_PERMISSION_REQUEST_CODE = 1001

fun AppCompatActivity.requestNotificationPermission() {

    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (!notificationManager.areNotificationsEnabled()) {
        val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
            .putExtra(Settings.EXTRA_APP_PACKAGE, packageName)

        startActivityForResult(intent, NOTIFICATION_PERMISSION_REQUEST_CODE)
    }

}