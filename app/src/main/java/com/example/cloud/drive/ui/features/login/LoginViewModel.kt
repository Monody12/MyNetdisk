package com.example.cloud.drive.ui.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cloud.drive.data.api.ApiService
import com.example.cloud.drive.data.model.LoginRequest
import com.example.cloud.drive.data.model.LoginState
import com.example.cloud.drive.data.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val apiService: ApiService,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    fun login(username: String, password: String) = viewModelScope.launch {
        _loginState.value = LoginState.Loading

        try {
            val response = apiService.login(LoginRequest(username, password))
            if (response.isSuccessful) {
                response.body()?.let { loginResponse ->
                    // 保存token
                    dataStoreRepository.saveToken(loginResponse.token)
                    _loginState.value = LoginState.Success(loginResponse)
                } ?: run {
                    _loginState.value = LoginState.Error("Empty response")
                }
            } else {
                _loginState.value = LoginState.Error("Login failed: ${response.code()}")
            }
        } catch (e: Exception) {
            _loginState.value = LoginState.Error(e.message ?: "Unknown error")
        }
    }
}