package com.yandey.dicodingstory.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.yandey.dicodingstory.util.Constant.THEME_PREF
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsDataStore @Inject constructor(private val dataStore: DataStore<Preferences>) {
    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit {
            it[booleanPreferencesKey(THEME_PREF)] = isDarkModeActive
        }
    }

    fun getThemeSetting(): Flow<Boolean?> = dataStore.data.map {
        it[booleanPreferencesKey(THEME_PREF)]
    }
}