package com.example.cloud.drive.ui.features.player

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AudioPreviewPlayer(uri: Uri) {
    val context = LocalContext.current
    var player by remember { mutableStateOf<ExoPlayer?>(null) }
    var currentPosition by remember { mutableStateOf(0f) }
    var duration by remember { mutableStateOf(0f) }
    var isPlaying by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    DisposableEffect(uri) {
        val exoPlayer = ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(uri))
            prepare()
            addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(state: Int) {
                    super.onPlaybackStateChanged(state)
                    if (state == Player.STATE_READY) {
                        duration = duration.toFloat()
                    }
                }

                override fun onIsPlayingChanged(playing: Boolean) {
                    super.onIsPlayingChanged(playing)
                    isPlaying = playing
                }
            })
        }
        player = exoPlayer

        // 启动协程来更新进度
        scope.launch {
            while (true) {
                delay(100) // 每100ms更新一次
                exoPlayer.currentPosition.let {
                    if (it >= 0) currentPosition = it.toFloat()
                }
            }
        }

        onDispose {
            exoPlayer.release()
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        player?.let { exoPlayer ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                IconButton(
                    onClick = {
                        if (isPlaying) {
                            exoPlayer.pause()
                        } else {
                            exoPlayer.play()
                        }
                    }
                ) {
                    Icon(
                        imageVector = if (isPlaying) {
                            Icons.Default.Pause
                        } else {
                            Icons.Default.PlayArrow
                        },
                        contentDescription = "播放/暂停"
                    )
                }

                // 检查duration是否有效，并确保slider的值域合法
                if (duration > 0) {
                    Slider(
                        value = currentPosition.coerceIn(0f, duration),
                        onValueChange = {
                            currentPosition = it
                            exoPlayer.seekTo(it.toLong())
                        },
                        valueRange = 0f..duration,
                        modifier = Modifier.weight(1f)
                    )
                } else {
                    // 如果duration无效，显示加载进度条
                    LinearProgressIndicator(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp)
                    )
                }
            }
        }
    }
}