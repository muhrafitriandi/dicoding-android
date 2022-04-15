package com.yandey.dicodingstory.domain.repository

import com.yandey.dicodingstory.data.model.body.LoginBody
import com.yandey.dicodingstory.data.model.body.RegisterBody
import com.yandey.dicodingstory.data.model.response.LoginResponse
import com.yandey.dicodingstory.data.model.response.RegisterResponse
import com.yandey.dicodingstory.util.Resource

interface Repository {
    suspend fun getRegisterUser(registerBody: RegisterBody): Resource<RegisterResponse>
    suspend fun getLoginUser(loginBody: LoginBody): Resource<LoginResponse>
}