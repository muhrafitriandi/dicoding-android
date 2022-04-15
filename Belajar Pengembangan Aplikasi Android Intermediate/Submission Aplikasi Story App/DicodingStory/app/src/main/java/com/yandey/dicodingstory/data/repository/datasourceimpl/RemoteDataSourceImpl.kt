package com.yandey.dicodingstory.data.repository.datasourceimpl

import com.yandey.dicodingstory.data.model.body.LoginBody
import com.yandey.dicodingstory.data.model.body.RegisterBody
import com.yandey.dicodingstory.data.model.response.LoginResponse
import com.yandey.dicodingstory.data.model.response.RegisterResponse
import com.yandey.dicodingstory.data.remote.ApiService
import com.yandey.dicodingstory.data.repository.datasource.RemoteDataSource
import com.yandey.dicodingstory.util.Resource
import retrofit2.Response

class RemoteDataSourceImpl(private val apiService: ApiService): RemoteDataSource {
    override suspend fun registerUser(registerBody: RegisterBody): Response<RegisterResponse> =
        apiService.register(registerBody)

    override suspend fun loginUser(loginBody: LoginBody): Response<LoginResponse> =
        apiService.login(loginBody)
}