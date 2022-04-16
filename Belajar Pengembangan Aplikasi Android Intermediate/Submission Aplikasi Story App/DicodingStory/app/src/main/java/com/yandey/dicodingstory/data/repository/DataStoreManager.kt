package com.yandey.dicodingstory.data.repository

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import com.yandey.dicodingstory.util.Constant
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(private val context: Context) {
    private val Context.userPreferenceDataStore: DataStore<Preferences> by preferencesDataStore(
        Constant.USER_PREF
    )

    suspend fun saveLoginToken(loginToken: String) {
        val wrappedKey = stringPreferencesKey(Constant.TOKEN_PREF)
        context.userPreferenceDataStore.edit {
            it[wrappedKey] = loginToken
        }
    }

    fun getLoginToken(): Flow<String> =
        context.userPreferenceDataStore.data.map {
            it[stringPreferencesKey(Constant.TOKEN_PREF)] ?: ""
        }

    suspend fun clearLoginToken() {
        context.userPreferenceDataStore.edit {
            it.clear()
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var mInstance: DataStoreManager? = null

        fun getInstance(context: Context): DataStoreManager =
            mInstance ?: synchronized(this) {
                val newInstance = mInstance ?: DataStoreManager(context).also { mInstance = it }
                newInstance
            }
    }
}