package com.yandey.dicodingstory.data.repository.settings

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun saveThemeSetting(isDarkModeActive: Boolean)
    fun getThemeSetting(): Flow<Boolean?>
}