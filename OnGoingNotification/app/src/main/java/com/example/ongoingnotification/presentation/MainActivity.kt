/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.example.ongoingnotification.presentation

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import com.example.ongoingnotification.presentation.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val launcher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    mainViewModel.updatePermissionGranted(MainViewModel.PermissionStatus.GRANTED)
                } else {
                    mainViewModel.updatePermissionGranted(MainViewModel.PermissionStatus.NOT_GRANTED)
                }
            }
        mainViewModel.checkNotificationPermission(this, launcher)


        setContent {
            val permissionStatus by mainViewModel.permissionStatus.collectAsState()
            when (permissionStatus) {
                MainViewModel.PermissionStatus.GRANTED -> MainScreen(mainViewModel)
                MainViewModel.PermissionStatus.NOT_GRANTED -> PermissionNotGranted()
                else -> {}
            }
        }
    }
}

@Composable
fun PermissionNotGranted() {
    val context = LocalContext.current as Activity
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {

        Text(
            text = "Please allow the permission to use the application...",
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}


@Composable
fun MainScreen(mainViewModel: MainViewModel) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Post Ongoing Notification",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Noti 1",
            modifier = Modifier
                .fillMaxWidth()
                .clickable { mainViewModel.sendOnGoingNotificationWithUri(context) },
            textAlign = TextAlign.Center,
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Noti 2",
            modifier = Modifier
                .fillMaxWidth()
                .clickable { mainViewModel.sendOnGoingNotificationWithResource(context) },
            textAlign = TextAlign.Center,
            fontSize = 12.sp
        )
    }
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    MainScreen(MainViewModel())
}