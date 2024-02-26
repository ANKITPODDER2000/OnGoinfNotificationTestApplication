package com.example.ongoingnotification.presentation

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.example.ongoingnotification.presentation.notification.BasicNotificationService

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val notificationChannel = NotificationChannel(
            BasicNotificationService.channelId,
            BasicNotificationService.name,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationChannel.description = "Basic test notification for testing"
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
    }
}