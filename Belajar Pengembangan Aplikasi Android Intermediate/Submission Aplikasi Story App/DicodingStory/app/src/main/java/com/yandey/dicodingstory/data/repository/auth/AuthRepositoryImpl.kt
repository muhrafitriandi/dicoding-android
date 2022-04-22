package com.yandey.dicodingstory.data.repository.auth

import com.yandey.dicodingstory.data.local.AuthDataStore
import com.yandey.dicodingstory.data.model.LoginBody
import com.yandey.dicodingstory.data.model.LoginResponse
import com.yandey.dicodingstory.data.model.RegisterBody
import com.yandey.dicodingstory.data.model.RegisterResponse
import com.yandey.dicodingstory.data.remote.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val authDataStore: AuthDataStore
) : AuthRepository {
    override suspend fun loginUser(loginBody: LoginBody): Flow<Result<LoginResponse>> = flow {
        try {
            val response = apiService.login(loginBody)
            emit(Result.success(response))
        } catch (exception: Exception) {
            exception.printStackTrace()
            emit(Result.failure(exception))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun registerUser(registerBody: RegisterBody): Flow<Result<RegisterResponse>> =
        flow {
            try {
                val response = apiService.register(registerBody)
                emit(Result.success(response))
            } catch (exception: Exception) {
                exception.printStackTrace()
                emit(Result.failure(exception))
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun logoutUser() = authDataStore.clearToken()

    override suspend fun saveToken(token: String) = authDataStore.saveToken(token)

    override fun getToken(): Flow<String?> = authDataStore.getToken()
}