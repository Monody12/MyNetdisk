package com.example.cloud.drive.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

object PreferencesKeys {
    val TOKEN = stringPreferencesKey("token")
}

class DataStoreRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.TOKEN] = token
        }
    }

    val token: Flow<String?> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.TOKEN]
    }
}