package com.example.cloud.drive.ui.features.player

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.cloud.drive.ui.MainActivity
import com.example.composevideoplayer.navigation.Screen
import com.example.composevideoplayer.navigation.VideoNavHost
import com.example.composevideoplayer.ui.theme.ComposeVideoPlayerTheme

@Composable
fun MyVideoPlayScreen() {
    val activity = LocalContext.current as MainActivity
    ComposeVideoPlayerTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()
            VideoNavHost(
                navController = navController,
                onNavigationToVideoPlay = { url, title ->
                    navController.navigate(Screen.VideoPlayScreen.passUrl(url, title))
                },
                onBackClick = { navController.popBackStack() },
                activity = activity
            )
        }
    }
}