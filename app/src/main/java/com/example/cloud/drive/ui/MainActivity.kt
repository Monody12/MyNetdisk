package com.example.cloud.drive.ui

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.cloud.drive.ui.features.login.LoginScreen
import com.example.cloud.drive.ui.features.player.MediaPreviewScreen
import com.example.cloud.drive.ui.features.player.VideoPlayer
import com.example.cloud.drive.ui.theme.CloudDriveTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CloudDriveTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    LoginScreen(modifier = Modifier.padding(innerPadding))
//                    MediaPreviewScreen(modifier = Modifier.padding(innerPadding))
                    val lifecycle = LocalLifecycleOwner.current.lifecycle
                    VideoPlayer(
                        videoUri = Uri.parse("https://www.dluserver.cn:8080/api/files/download?fileId=677&preview=true"),
                        lifecycle = lifecycle
                    )
                    // 使用Jetpack Compose基于Exoplayer进行的封装可以参考 https://github.com/lanlinju/ComposeVideoPlayer
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CloudDriveTheme {
        Greeting("Android")
    }
}