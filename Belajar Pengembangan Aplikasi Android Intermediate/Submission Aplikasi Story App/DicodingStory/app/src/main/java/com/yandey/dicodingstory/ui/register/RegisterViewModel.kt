package com.yandey.dicodingstory.ui.register

import androidx.lifecycle.ViewModel
import com.yandey.dicodingstory.data.model.RegisterBody
import com.yandey.dicodingstory.data.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    suspend fun register(registerBody: RegisterBody) = authRepository.registerUser(registerBody)
}