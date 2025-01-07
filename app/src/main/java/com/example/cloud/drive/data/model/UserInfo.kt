package com.example.cloud.drive.data.model

data class UserInfo(
    val nickname: String = "",
    val avatar: String = "",
    val token: String = "",
    val isLoggedIn: Boolean = false
)