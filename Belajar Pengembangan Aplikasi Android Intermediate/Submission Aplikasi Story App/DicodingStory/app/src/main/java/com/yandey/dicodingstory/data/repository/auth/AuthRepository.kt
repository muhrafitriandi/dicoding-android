package com.yandey.dicodingstory.data.repository.auth

import com.yandey.dicodingstory.data.model.LoginBody
import com.yandey.dicodingstory.data.model.LoginResponse
import com.yandey.dicodingstory.data.model.RegisterBody
import com.yandey.dicodingstory.data.model.RegisterResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun loginUser(loginBody: LoginBody): Flow<Result<LoginResponse>>
    suspend fun registerUser(registerBody: RegisterBody): Flow<Result<RegisterResponse>>
    suspend fun logoutUser()
    suspend fun saveToken(token: String)
    fun getToken(): Flow<String?>
}