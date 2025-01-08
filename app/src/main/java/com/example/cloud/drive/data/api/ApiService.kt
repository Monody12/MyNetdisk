package com.example.cloud.drive.data.api

import com.example.cloud.drive.data.model.ApiResponse
import com.example.cloud.drive.data.model.LoginRequest
import com.example.cloud.drive.data.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("user/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): ApiResponse<LoginResponse>
}