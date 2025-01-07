package com.example.cloud.drive.ui.navigation

import android.annotation.SuppressLint
import androidx.navigation.NavController

object NavControllerProvider {
    @SuppressLint("StaticFieldLeak")
    private var navController: NavController? = null

    fun provide(navController: NavController) {
        this.navController = navController
    }

    fun get(): NavController {
        return navController ?: throw IllegalStateException("NavController not initialized")
    }
}