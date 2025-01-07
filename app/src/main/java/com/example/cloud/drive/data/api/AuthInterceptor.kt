package com.example.cloud.drive.data.api

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.cloud.drive.data.repository.PreferencesKeys
import com.google.common.eventbus.EventBus
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val token = runBlocking {
            dataStore.data.first()[PreferencesKeys.TOKEN]
        }

        val request = chain.request().newBuilder().apply {
            token?.let { addHeader("Authorization", "Bearer $it") }
        }.build()

        val response = chain.proceed(request)

        if (response.code == 401) {
            // 需要在应用层面处理导航，这里发送事件
//            EventBus.publish(AuthEvent.Unauthorized)
        }

        return response
    }
}