package com.yandey.dicodingstory.data.remote

import com.yandey.dicodingstory.data.model.body.LoginBody
import com.yandey.dicodingstory.data.model.body.RegisterBody
import com.yandey.dicodingstory.data.model.response.LoginResponse
import com.yandey.dicodingstory.data.model.response.RegisterResponse
import com.yandey.dicodingstory.util.Resource
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("register")
    suspend fun register(@Body registerBody: RegisterBody): Response<RegisterResponse>

    @POST("login")
    suspend fun login(@Body loginBody: LoginBody): Response<LoginResponse>
}