package com.yandey.dicodingstory.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yandey.dicodingstory.data.repository.auth.AuthRepository
import com.yandey.dicodingstory.data.repository.settings.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    fun logout() {
        viewModelScope.launch {
            authRepository.logoutUser()
        }
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            settingsRepository.saveThemeSetting(isDarkModeActive)
        }
    }

    fun getThemeSetting(): Flow<Boolean?> = settingsRepository.getThemeSetting()
}