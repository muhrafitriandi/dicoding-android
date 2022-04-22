package com.yandey.dicodingstory.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yandey.dicodingstory.data.model.LoginBody
import com.yandey.dicodingstory.data.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    suspend fun login(loginBody: LoginBody) =
        authRepository.loginUser(loginBody)

    fun saveToken(token: String) {
        viewModelScope.launch {
            authRepository.saveToken(token)
        }
    }
}