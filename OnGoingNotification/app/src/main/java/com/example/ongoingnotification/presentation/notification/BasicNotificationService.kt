package com.example.ongoingnotification.presentation.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.wear.ongoing.OngoingActivity
import com.example.ongoingnotification.R
import androidx.wear.ongoing.Status
import com.example.ongoingnotification.presentation.broadcast.MainBroadcastReceiver

class BasicNotificationService {
    companion object {
        const val notificationId = 1
        const val channelId = "BasicTestNotification"
        const val name = "Basic notification"
        private const val TAG = "BasicNotificationService"
    }

    fun showBasicNotification(
        title: String,
        content: String,
        context: Context,
        sendUri: Boolean = true
    ) {
        Log.d(TAG, "showBasicNotification: is called..")
        val notification = getBasicNotificationBuilder(title, content, context, sendUri).build()
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, notification)
    }

    private fun getBasicNotificationBuilder(
        title: String,
        content: String,
        context: Context,
        sendUri: Boolean
    ): NotificationCompat.Builder {

        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.baseline_qr_code_24)
            .setContentTitle(title)
            .setContentText(content)
            .setAutoCancel(true)
            .setColor(154589)
            .setOngoing(true)
            .setCategory(NotificationCompat.CATEGORY_WORKOUT)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

        val ongoingActivityStatus = Status.Builder()
            .addTemplate(content)
            .build()

        val icon = Icon.createWithContentUri("content://com.example.ongoingnotification/walk")
        /*val drawable = icon.loadDrawable(context)
        Log.d(TAG, "getBasicNotificationBuilder: drawable : $drawable")*/
        val ongoingActivityBuilder =
            OngoingActivity.Builder(context, notificationId, notificationBuilder)
                .setStatus(ongoingActivityStatus)
                .setTouchIntent(
                    PendingIntent.getBroadcast(
                        context, 5, Intent(context, MainBroadcastReceiver::class.java),
                        PendingIntent.FLAG_IMMUTABLE
                    )
                )
        if(sendUri) ongoingActivityBuilder.setStaticIcon(icon)
        else ongoingActivityBuilder.setStaticIcon(R.drawable.walk)
        ongoingActivityBuilder.build().apply(context)

        return notificationBuilder
    }
}