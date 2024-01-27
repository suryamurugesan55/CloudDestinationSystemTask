package com.surya.grocerytask.worker

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.room.Room
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.surya.grocerytask.R
import com.surya.grocerytask.db.ShoppingListDB
import com.surya.grocerytask.model.ShoppingList
import com.surya.grocerytask.ui.MainActivity
import com.surya.grocerytask.ui.todo.add_shopping.UpdateShoppingActivity

class ReminderWorker(
    private val context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {
        val shoppingListId = inputData.getLong("shoppingListId", -1)

        if (shoppingListId != -1L) {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            createNotification(context, "Shopping List Reminder", "Don't forget to complete your shopping list!")
            val intent = Intent("REMINDER_TRIGGERED")
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
        }

        return Result.success()
    }

    private fun createNotification(
        context: Context,
        title: String,
        message: String,
    ) {
        val channelId = "ReminderChannel"
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_shopping_bag)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Reminder Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            notify(1, notificationBuilder.build())
        }
    }
}

