package com.example.ongoingnotification.presentation.broadcast

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.util.Log
import com.example.ongoingnotification.presentation.MainActivity
import com.example.ongoingnotification.presentation.notification.BasicNotificationService

class MainBroadcastReceiver : BroadcastReceiver() {
    companion object {
        private const val TAG = "IncrementBroadcastReceiver"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "onReceive: is called....")
        val mainIntent = Intent(context, MainActivity::class.java)
        mainIntent.flags = FLAG_ACTIVITY_NEW_TASK
        context?.startActivity(mainIntent)
        val notificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(BasicNotificationService.notificationId)
    }
}