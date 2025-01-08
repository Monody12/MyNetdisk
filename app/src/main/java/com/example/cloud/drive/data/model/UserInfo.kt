package com.example.cloud.drive.data.model

data class UserInfo(
    val id: Int,
    val username: String,
    val password: String,
    val email: String,
    val createAt: String,
    val updateAt: String,
    val deleteFlag: Boolean
)