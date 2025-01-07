package com.example.cloud.drive.data.repository
//
//import android.content.Context
//import androidx.datastore.core.DataStore
//import androidx.datastore.preferences.core.booleanPreferencesKey
//import androidx.datastore.preferences.core.stringPreferencesKey
//import androidx.datastore.preferences.preferencesDataStore
//import com.example.cloud.drive.data.model.UserInfo
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.map
//import java.util.prefs.Preferences
//
//class UserPreferencesRepository(private val context: Context) {
//    companion object {
//        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")
//
//        // 定义键值
//        private val NICKNAME_KEY = stringPreferencesKey("nickname")
//        private val AVATAR_KEY = stringPreferencesKey("avatar")
//        private val TOKEN_KEY = stringPreferencesKey("token")
//        private val IS_LOGGED_IN_KEY = booleanPreferencesKey("is_logged_in")
//    }
//
//    // 获取用户信息Flow
//    val userInfoFlow: Flow<UserInfo> = context.dataStore.data.map { preferences ->
//        UserInfo(
//            nickname = preferences[NICKNAME_KEY] ?: "",
//            avatar = preferences[AVATAR_KEY] ?: "",
//            token = preferences[TOKEN_KEY] ?: "",
//            isLoggedIn = preferences[IS_LOGGED_IN_KEY] ?: false
//        )
//    }
//
//    // 保存用户信息
//    suspend fun saveUserInfo(userInfo: UserInfo) {
//        context.dataStore.edit { preferences ->
//            preferences[NICKNAME_KEY] = userInfo.nickname
//            preferences[AVATAR_KEY] = userInfo.avatar
//            preferences[TOKEN_KEY] = userInfo.token
//            preferences[IS_LOGGED_IN_KEY] = userInfo.isLoggedIn
//        }
//    }
//
//    // 清除用户信息
//    suspend fun clearUserInfo() {
//        context.dataStore.edit { preferences ->
//            preferences.clear()
//        }
//    }
//}