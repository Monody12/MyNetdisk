package com.example.cloud.drive.ui.features.player

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MediaPreviewScreen(
    modifier: Modifier = Modifier
) {
    val audioUri = Uri.parse("https://www.dluserver.cn:8080/api/files/download?fileId=227&preview=false")
    val videoUri = Uri.parse("https://www.dluserver.cn:8080/api/files/download?fileId=677&preview=true")

    Column(modifier = modifier.fillMaxWidth()) {
        Text("音频预览", style = MaterialTheme.typography.headlineSmall)
        AudioPreviewPlayer(audioUri)

        Spacer(modifier = Modifier.height(16.dp))

        Text("视频预览", style = MaterialTheme.typography.headlineSmall)
        VideoPreviewPlayer(videoUri)
    }
}