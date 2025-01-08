package com.example.cloud.drive.data.model

data class ApiResponse<T>(
    var data: T? = null,
    var code: Int = 0,
    var msg: String = ""
)
