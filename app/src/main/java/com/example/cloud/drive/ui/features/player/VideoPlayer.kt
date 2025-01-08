package com.example.cloud.drive.ui.features.player

import android.net.Uri
import android.view.View
import android.widget.ImageButton
import androidx.activity.compose.BackHandler
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Forward
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.cloud.drive.R

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayer(
    videoUri: Uri,
    lifecycle: Lifecycle
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    var isFullScreen by remember { mutableStateOf(false) }

    // 创建 ExoPlayer 实例
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videoUri))
            prepare()
        }
    }

    // 确保在组合物被销毁时释放资源
    DisposableEffect(
        lifecycle
    ) {
        onDispose {
            exoPlayer.release()
        }
    }

    // 监听生命周期，自动暂停/恢复播放
    DisposableEffect(lifecycle) {
        val observer = LifecycleEventObserver { _, event ->
            when(event) {
                Lifecycle.Event.ON_STOP -> exoPlayer.playWhenReady = false
                Lifecycle.Event.ON_START -> exoPlayer.playWhenReady = true
                else -> {}
            }
        }
        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }

    // 返回键处理
    BackHandler(enabled = isFullScreen) {
        isFullScreen = false
    }

    // 根据是否全屏调整播放器大小
    val modifier = if (isFullScreen) {
        Modifier.fillMaxSize()
    } else {
        Modifier
            .fillMaxWidth()
            .height(200.dp)
    }

    // 使用 AndroidView 来嵌入 PlayerView
    AndroidView(
        factory = {
            PlayerView(context).apply {
                player = exoPlayer
                useController = true
                controllerShowTimeoutMs = 3000
                setShowBuffering(PlayerView.SHOW_BUFFERING_WHEN_PLAYING)
                exoPlayer.playWhenReady = true

                // 全屏按钮点击事件
//                setControllerVisibilityListener { visibility ->
//                    if (visibility == View.VISIBLE) {
//                        findViewById<ImageButton>(R.id.exo_fullscreen_button)?.setOnClickListener {
//                            isFullScreen = !isFullScreen
//                        }
//                    }
//                }
            }
        },
        modifier = modifier
    )

    if (!isFullScreen) {
        // 非全屏状态下的额外控制组件
        Column {
            // 倍速控制
            var playbackSpeed by remember { mutableStateOf(1f) }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "倍速：${playbackSpeed}x")
                Slider(
                    value = playbackSpeed,
                    onValueChange = {
                        playbackSpeed = it
                        exoPlayer.setPlaybackSpeed(it)
                    },
                    valueRange = 0.5f..2.0f,
                    steps = 3, // 0.5,1.0,1.5,2.0
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // 快进和回退按钮
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = {
                    exoPlayer.seekBack()
                }) {
                    Icon(imageVector = Icons.Default.Replay, contentDescription = "回退")
                }
                IconButton(onClick = {
                    exoPlayer.seekForward()
                }) {
                    Icon(imageVector = Icons.Default.Forward, contentDescription = "快进")
                }
            }
        }
    }
}