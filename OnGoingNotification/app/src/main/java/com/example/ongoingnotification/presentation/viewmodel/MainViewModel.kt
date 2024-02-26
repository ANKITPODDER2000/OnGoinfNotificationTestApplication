package com.example.ongoingnotification.presentation.viewmodel

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import com.example.ongoingnotification.presentation.notification.BasicNotificationService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {
    enum class PermissionStatus {
        CHECKING,
        GRANTED,
        NOT_GRANTED
    }

    companion object {
        private const val TAG = "MainViewModel"
    }

    private val basicNotificationService = BasicNotificationService()
    private val _permissionStatus = MutableStateFlow(PermissionStatus.CHECKING)
    val permissionStatus = _permissionStatus.asStateFlow()

    fun updatePermissionGranted(permissionStatus: PermissionStatus) {
        _permissionStatus.value = permissionStatus
    }

    fun checkNotificationPermission(context: Activity, launcher: ActivityResultLauncher<String>) {
        if (ActivityCompat.checkSelfPermission(
                context,
                "android.permission.POST_NOTIFICATIONS"
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d(TAG, "checkNotificationPermission: asking for permission...")
            launcher.launch("android.permission.POST_NOTIFICATIONS")
        } else {
            Log.d(TAG, "checkNotificationPermission: permission granted...")
            _permissionStatus.value = PermissionStatus.GRANTED
        }
    }


    fun sendOnGoingNotificationWithUri(context: Context) {
        basicNotificationService.showBasicNotification(
            "Test Notification",
            "Content of test notification...",
            context
        )
    }

    fun sendOnGoingNotificationWithResource(context: Context) {
        basicNotificationService.showBasicNotification(
            "Test Notification",
            "Content of test notification...",
            context,
            false
        )
    }
}