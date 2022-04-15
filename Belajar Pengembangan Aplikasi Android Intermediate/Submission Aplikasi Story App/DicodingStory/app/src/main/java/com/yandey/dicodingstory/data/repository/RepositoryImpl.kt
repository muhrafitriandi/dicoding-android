package com.yandey.dicodingstory.data.repository

import com.yandey.dicodingstory.data.model.body.LoginBody
import com.yandey.dicodingstory.data.model.body.RegisterBody
import com.yandey.dicodingstory.data.model.response.LoginResponse
import com.yandey.dicodingstory.data.model.response.RegisterResponse
import com.yandey.dicodingstory.data.repository.datasource.RemoteDataSource
import com.yandey.dicodingstory.domain.repository.Repository
import com.yandey.dicodingstory.util.Resource
import retrofit2.Response

class RepositoryImpl(private val remoteDataSource: RemoteDataSource): Repository {
    override suspend fun getRegisterUser(registerBody: RegisterBody): Resource<RegisterResponse> =
        responseToResourceRegister(remoteDataSource.registerUser(registerBody))

    override suspend fun getLoginUser(loginBody: LoginBody): Resource<LoginResponse> =
        responseToResourceLogin(remoteDataSource.loginUser(loginBody))

    private fun responseToResourceLogin(
        response: Response<LoginResponse>
    ): Resource<LoginResponse> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    private fun responseToResourceRegister(
        response: Response<RegisterResponse>
    ): Resource<RegisterResponse> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }
}