package com.yandey.dicodingstory.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yandey.dicodingstory.domain.usecase.LoginUseCase
import com.yandey.dicodingstory.domain.usecase.RegisterUseCase

@Suppress("UNCHECKED_CAST")
class UserViewModelFactory(
    private val app: Application,
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(
            app,
            loginUseCase,
            registerUseCase
        ) as T
    }
}