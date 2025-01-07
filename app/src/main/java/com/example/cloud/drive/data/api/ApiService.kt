package com.example.cloud.drive.data.api

import com.example.cloud.drive.data.model.LoginRequest
import com.example.cloud.drive.data.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>
}