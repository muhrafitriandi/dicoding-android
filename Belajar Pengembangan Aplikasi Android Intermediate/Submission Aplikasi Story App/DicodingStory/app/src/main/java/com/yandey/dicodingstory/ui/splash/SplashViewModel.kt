package com.yandey.dicodingstory.ui.splash

import androidx.lifecycle.ViewModel
import com.yandey.dicodingstory.data.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    fun getToken(): Flow<String?> = repository.getToken()
}