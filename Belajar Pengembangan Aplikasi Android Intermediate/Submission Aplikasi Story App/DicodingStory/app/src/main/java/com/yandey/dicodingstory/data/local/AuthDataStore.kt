package com.yandey.dicodingstory.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.yandey.dicodingstory.util.Constant.AUTH_TOKEN
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthDataStore @Inject constructor(private val dataStore: DataStore<Preferences>) {
    suspend fun saveToken(token: String) {
        dataStore.edit {
            it[stringPreferencesKey(AUTH_TOKEN)] = token
        }
    }

    fun getToken(): Flow<String?> = dataStore.data.map {
        it[stringPreferencesKey(AUTH_TOKEN)]
    }

    suspend fun clearToken() {
        dataStore.edit {
            it.clear()
        }
    }
}