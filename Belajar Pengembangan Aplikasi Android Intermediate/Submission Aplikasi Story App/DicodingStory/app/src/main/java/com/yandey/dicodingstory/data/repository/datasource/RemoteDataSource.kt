package com.yandey.dicodingstory.data.repository.datasource

import com.yandey.dicodingstory.data.model.body.LoginBody
import com.yandey.dicodingstory.data.model.body.RegisterBody
import com.yandey.dicodingstory.data.model.response.LoginResponse
import com.yandey.dicodingstory.data.model.response.RegisterResponse
import retrofit2.Response

interface RemoteDataSource {
    suspend fun registerUser(registerBody: RegisterBody): Response<RegisterResponse>
    suspend fun loginUser(loginBody: LoginBody): Response<LoginResponse>
}